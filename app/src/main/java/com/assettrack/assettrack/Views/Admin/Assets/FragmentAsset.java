package com.assettrack.assettrack.Views.Admin.Assets;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.assettrack.assettrack.Adapters.V1.AssetAdapter;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Constatnts.GLConstants;
import com.assettrack.assettrack.Data.Parsers.AssetParser;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.R;
import com.assettrack.assettrack.Utils.NetworkUtils;
import com.assettrack.assettrack.Views.Engineer.NewAsset.Installation;
import com.assettrack.assettrack.Views.Shared.Asset.AssetActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static com.assettrack.assettrack.Constatnts.GLConstants.Companion;

public class FragmentAsset extends Fragment {
    AssetAdapter listAdapter;
    ArrayList<AssetModel> assetModels;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private LinearLayout linearLayoutEmpty;


    String searchtext = "";
    private View view;
    private ProgressDialog progressDialog;
    private PrefManager prefManager;
    private EditText edtSearch;
    private SearchView search;
    private ActionMode mActionMode;
    private int STATUS_ID;

    private ArrayList<AssetModel> assetModelsSerch;
    int count = 0;
    int lastPage = 1;
    int currentPage = 1;

    private SwipeRefreshLayout swipe_refresh_layout;


    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.menu_asset_options_multi, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:

                    mode.finish();
                    break;
                case R.id.update_status:

                    mode.finish();

                    break;
                case R.id.share:


                    mode.finish();

                    break;
                default:
                    return false;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            mActionMode = null;
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_assets, container, false);
        //  Log.d("Loohj","in onCreateview");
        //  return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        assetModels = new ArrayList<>();
        prefManager = new PrefManager(Objects.requireNonNull(getActivity()));
        search = view.findViewById(R.id.search_bar);
        edtSearch = view.findViewById(R.id.edt_search);

        swipe_refresh_layout = view.findViewById(R.id.swipeRefreshView);

        STATUS_ID = Companion.getWORKING();
        if (getArguments() != null) {
            STATUS_ID = getArguments().getInt("STATUS_ID");
        }


        if (NetworkUtils.Companion.isConnectionFast(getActivity())) {

            swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
            swipe_refresh_layout.setBackgroundResource(android.R.color.white);
            swipe_refresh_layout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);
            swipe_refresh_layout.setRefreshing(true);


            getData();
        } else {
            snack("Please check your internet connection");
        }
        swipe_refresh_layout.setOnRefreshListener(() -> {

            swipe_refresh_layout.setRefreshing(true);
            if (NetworkUtils.Companion.isConnectionFast(getActivity())) {
                getData();
            } else {
                swipe_refresh_layout.setRefreshing(false);

                snack("Check your internet connection");
            }


        });


        //initUI(STATUS_ID, new ArrayList<>());
        initSearchView();


    }

    void setEmptyState(boolean state) {
        TextView txtEmpty;
        txtEmpty = view.findViewById(R.id.empty_view);
        txtEmpty.setText("Refreshing");

        LinearLayout linearLayoutEmpty = view.findViewById(R.id.empty_layout);
        if (state) {
            txtEmpty.setText("No data to display");

            linearLayoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        } else {
            txtEmpty.setText("No data display");
            linearLayoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
    private ArrayList<AssetModel> getData() {

        HashMap<String, String> params = new HashMap<>();


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("working....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        if (currentPage == 1) {
            progressDialog.show();
        }
        String url = APiConstants.Companion.getAllAssets();
        if (STATUS_ID == GLConstants.Companion.getWORKING()) {
            url = APiConstants.Companion.getWorkingAssets();
        } else if (STATUS_ID == GLConstants.Companion.getFAULTY()) {
            url = APiConstants.Companion.getFaultyAssets();
        } else if (STATUS_ID == GLConstants.Companion.getWRITTEN_OFF()) {
            url = APiConstants.Companion.getGroundedAssets();
        }

        Log.d("getData", url + "\n" + params.toString() + "\n" + prefManager.getToken());
        Request.Companion.getRequest(url + "?page=" + currentPage++, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                initUI(STATUS_ID, new ArrayList<>());

                Log.d("getData", error.getErrorBody());
            }

            @Override
            public void onError(@NotNull String error) {

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                initUI(STATUS_ID, new ArrayList<>());
                Log.d("getData", error);

            }

            @Override
            public void onSuccess(@NotNull String response) {

                Log.d("getData", response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    //if(!jsonObject.getBoolean("error")){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    lastPage = jsonObject.getInt("last_page");
                    count = jsonObject.getInt("total");


                    assetModels.addAll(AssetParser.parse(jsonArray));

                    //initUI(STATUS_ID,assetModels);


                    //}
                } catch (Exception nm) {

                    Log.d("getData", nm.toString());
                }
                if (progressDialog != null && progressDialog.isShowing()) {
                    //progressDialog.setMessage(response);
                    progressDialog.dismiss();
                }
                initUI(STATUS_ID, assetModels);
            }
        });


        return assetModels;
    }
    private void initSearchView() {

        try {
            SearchManager manager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);


            search.setOnClickListener(v -> search.setIconified(false));
            search.setQueryHint("Search asset list");

            if (manager != null) {
                search.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
            }

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


                @Override
                public boolean onQueryTextSubmit(String query) {


                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {


                    if (!newText.isEmpty()) {


                        searchtext = newText.toLowerCase();

                        assetModelsSerch = new ArrayList<>();
                        if (assetModels != null) {
                            for (AssetModel assetModel : assetModels) {
                                if (assetModel.getAsset_name().toLowerCase().contains(searchtext)
                                        || assetModel.getCustomerModel().getName().toLowerCase().contains(searchtext)
                                        || assetModel.getAsset_code().toLowerCase().contains(searchtext)) {

                                    assetModelsSerch.add(assetModel);
                                }
                            }
                        }
                        if (listAdapter != null && assetModelsSerch != null) {
                            listAdapter.updateList(assetModelsSerch);
                            listAdapter.notifyDataSetChanged();
                        }


                    } else {
                        if (listAdapter != null && assetModels != null) {
                            listAdapter.updateList(assetModels);
                        }
                        searchtext = "";

                    }


                    return false;
                }
            });

        } catch (Exception nm) {

            edtSearch.setVisibility(View.VISIBLE);
            edtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String newText = edtSearch.getText().toString();
                    if (!newText.isEmpty()) {


                        searchtext = newText.toLowerCase();
                        assetModelsSerch = new ArrayList<>();
                        if (assetModels != null) {
                            for (AssetModel assetModel : assetModels) {
                                if (assetModel.getAsset_name().toLowerCase().contains(searchtext)
                                        || assetModel.getCustomerModel().getName().toLowerCase().contains(searchtext)
                                        || assetModel.getAsset_code().toLowerCase().contains(searchtext)) {

                                    assetModelsSerch.add(assetModel);
                                }
                            }
                        }
                        if (listAdapter != null && assetModelsSerch != null) {
                            listAdapter.updateList(assetModelsSerch);
                            listAdapter.notifyDataSetChanged();
                        }

                    } else {

                        searchtext = "";
                        if (listAdapter != null && assetModels != null) {
                            listAdapter.updateList(assetModels);
                        }

                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
    }
    private void initUI(int status, ArrayList<AssetModel> assetModels) {

        try {
            ((ActivityManageAssets) Objects.requireNonNull(getActivity())).setFab(R.drawable.ic_add_black_24dp, false);

        } catch (Exception nm) {
            nm.printStackTrace();
        }
        recyclerView = view.findViewById(R.id.recyclerView);


        if (assetModels != null) {
            mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mStaggeredLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());


            listAdapter = new AssetAdapter(getContext(), assetModels , new OnclickRecyclerListener() {
                @Override
                public void onClickListener(int position) {
                    Companion.setId(String.valueOf(assetModels.get(position).getId()));
                    Companion.setAssetModel(assetModels.get(position));
                    Intent intent = new Intent(getActivity(), AssetActivity.class);
                    intent.putExtra("data", assetModels.get(position));
                    intent.putExtra("state", false);
                    startActivity(intent);


                }

                @Override
                public void onLongClickListener(int position) {
                    //  popupMenu(position, assetModels.get(position), view);



                }

                @Override
                public void onCheckedClickListener(int position) {
                    int count = 0;
                    if (assetModels.get(position).isChecked()) {
                        assetModels.get(position).setChecked(false);

                        // count--;

                    } else {
                        assetModels.get(position).setChecked(true);

                    }


                    listAdapter.notifyItemChanged(position, assetModels.get(position));


                    for (AssetModel assetModel : assetModels) {
                        if (assetModel.isChecked()) {
                            count++;

                        }
                    }

                    if (count > 0) {
                        mActionMode = Objects.requireNonNull(getActivity()).startActionMode(callback);
                    } else {
                        if (mActionMode != null) {
                            mActionMode.finish();
                        }
                    }

                }

                @Override
                public void onMoreClickListener(int position) {


                }

                @Override
                public void onClickListener(int adapterPosition, View view) {

                    popupMenu(adapterPosition, assetModels.get(adapterPosition), view);
                }
            });
            // listAdapter.notifyDataSetChanged();

            recyclerView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();

            setEmptyState(listAdapter.getItemCount() > 0);
            if (swipe_refresh_layout != null && swipe_refresh_layout.isRefreshing()) {
                swipe_refresh_layout.setRefreshing(false);
            }
            try {
                ((ActivityManageAssets) Objects.requireNonNull(getActivity())).setCount(count, STATUS_ID);


            } catch (Exception nm) {

            }
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    // boolean   isBottomReached = !recyclerView!!.canScrollVertically(1)
                    boolean isBottomReached = !recyclerView.canScrollVertically(1);
                    if (isBottomReached && lastPage > currentPage++) {
                        getData();
                        // Toast.makeText(getContext(),"Bottom reached",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //  (activity as Ac).methodName()
            //  (ActivityManageAssets).


        } else {
            Log.d("Loohj", "assetmodels is null");
            if (swipe_refresh_layout != null && swipe_refresh_layout.isRefreshing()) {
                swipe_refresh_layout.setRefreshing(false);
            }
            setEmptyState(true);

        }

    }


    private void popupMenu(int pos, AssetModel assetModel, View view) {
        PopupMenu popupMenu = new PopupMenu(Objects.requireNonNull(getContext()), view);
        popupMenu.inflate(R.menu.menu_asset_options);

//        if (STATUS_ID == Companion.getWORKING()) {
//            popupMenu.getMenu().getItem(3).setVisible(false);
//        }
//        if (STATUS_ID == Companion.getFAULTY()) {
//            // popupMenu.getMenu().getItem(3).setVisible(false);
//        }
//        if (STATUS_ID == Companion.getWRITTEN_OFF()) {
//            popupMenu.getMenu().getItem(1).setVisible(false);
//            popupMenu.getMenu().getItem(3).setVisible(false);
//            popupMenu.getMenu().getItem(5).setVisible(false);
//        }
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.view:
                    Companion.setId(String.valueOf(assetModel.getId()));
                    Companion.setAssetModel(assetModel);
                    Intent intent = new Intent(getActivity(), AssetActivity.class);
                    intent.putExtra("data", assetModels.get(pos));
                    intent.putExtra("state", false);
                    startActivity(intent);


                    break;
                case R.id.edit:
                    Companion.setAssetModel(assetModel);

                    Intent intent1 = new Intent(getActivity(), Installation.class);
                    intent1.putExtra("state", 1);
                    startActivity(intent1);


                    break;
                case R.id.delete:

                    startDeleteDialog(assetModel);


                    break;

                case R.id.assign:


                    break;
                case R.id.share:

                    break;
                case R.id.update_status:

                    break;

                default:
            }
            return false;
        });
        popupMenu.show();
    }
    private void startDeleteDialog(AssetModel assetModel) {
        final DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:

                    deleteItem(assetModel);
                    listAdapter.notifyDataSetChanged();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();

                    break;

                default:
            }
        };


        if (getContext() != null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setMessage("You are about to delete this item ").setPositiveButton("Delete", dialogClickListener)
                    .setNegativeButton("Dismiss", dialogClickListener)
                    .show();
        }

    }

    private void deleteItem(AssetModel assetModel) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Deleting....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();


        Request.Companion.deleteRequest(APiConstants.Companion.getDeleteAsset() + "" + assetModel.getId(), prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                snack(error.getMessage());
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onError(@NotNull String error) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                snack(error);
            }

            @Override
            public void onSuccess(@NotNull String response) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                snack(response);
                if (listAdapter != null) {
                    listAdapter.notifyDataSetChanged();
                    getData();
                }

            }
        });

    }

    private void setEmptyState(Boolean b) {
//        LinearLayout linearLayoutEmpty2 = view.findViewById(R.id.empty_layout);
//        TextView txt_empty = view.findViewById(R.id.empty_view);
//        if (b) {
//            linearLayoutEmpty2.setVisibility(View.VISIBLE);
//            //         linearLayoutEmpty.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//            txt_empty.setText("No assets found");
//            // swipe_refresh_layout.setRefreshing(true);
//
//
//            //   txt_empty.setText("Couldn't load beneficiaries");
//
//            Log.d("revisi", "recyclerinvisible");
//        } else {
//            txt_empty.setText("No assets found");
//
////            linearLayoutEmpty.setVisibility(View.GONE);
//            // swipe_refresh_layout.setRefreshing(false);
//            linearLayoutEmpty2.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//
//
//            Log.d("revisi", "recyclervisible");
//        }
    }

    private void snack(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

}
