package com.assettrack.assettrack.Views.Admin.Assets;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.assettrack.assettrack.Adapters.AssetListAdapter;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Constatnts.GLConstants;
import com.assettrack.assettrack.Data.Parsers.AssetParser;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.R;
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
    AssetListAdapter listAdapter;
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

        prefManager = new PrefManager(Objects.requireNonNull(getActivity()));
        search = view.findViewById(R.id.search_bar);
        edtSearch = view.findViewById(R.id.edt_search);
        STATUS_ID = Companion.getWORKING();
        if (getArguments() != null) {
            STATUS_ID = getArguments().getInt("STATUS_ID");
        }


        getData();
        initUI(STATUS_ID, new ArrayList<>());
        initSearchView();


    }

    private ArrayList<AssetModel> getData() {
        assetModels = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("working....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = APiConstants.Companion.getAllAssets();
        if (STATUS_ID == GLConstants.Companion.getWORKING()) {
            url = APiConstants.Companion.getWorkingAssets();
        } else if (STATUS_ID == GLConstants.Companion.getFAULTY()) {
            url = APiConstants.Companion.getFaultyAssets();
        } else if (STATUS_ID == GLConstants.Companion.getWRITTEN_OFF()) {
            url = APiConstants.Companion.getGroundedAssets();
        }

        Log.d("getData", url + "\n" + params.toString() + "\n" + prefManager.getToken());
        Request.Companion.getRequest(url, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.setMessage(error.getMessage());
                    progressDialog.dismiss();
                }
                Log.d("getData", error.getErrorBody());
            }

            @Override
            public void onError(@NotNull String error) {

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.setMessage(error);
                    progressDialog.dismiss();
                }
                Log.d("getData", error);

            }

            @Override
            public void onSuccess(@NotNull String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.setMessage(response);
                    progressDialog.dismiss();
                }
                Log.d("getData", response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    //if(!jsonObject.getBoolean("error")){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    assetModels = AssetParser.parse(jsonArray);
                    //}
                } catch (Exception nm) {

                    Log.d("getData", nm.toString());
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

                        try {
                            new Thread(() -> {


                            }).start();


                        } catch (Exception nm) {

                        }
                        searchtext = newText;

                    } else {

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


                        searchtext = newText;

                    } else {

                        searchtext = "";

                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
    }

    private void initUI(int status, ArrayList<AssetModel> assetModels) {


        recyclerView = view.findViewById(R.id.recyclerView);


        if (assetModels != null && assetModels.size() > 0) {
            mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mStaggeredLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());


            listAdapter = new AssetListAdapter(getContext(), assetModels, 1, new OnclickRecyclerListener() {
                @Override
                public void onClickListener(int position) {

                }

                @Override
                public void onLongClickListener(int position) {

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

            setEmptyState(listAdapter.getItemCount() < 1);
            ((ActivityManageAssets) Objects.requireNonNull(getActivity())).setCount(listAdapter.getItemCount(), STATUS_ID);
            //  (activity as Ac).methodName()
            //  (ActivityManageAssets).

        } else {
            Log.d("Loohj", "assetmodels is null");

        }

    }

    private void popupMenu(int pos, AssetModel assetModel, View view) {
        PopupMenu popupMenu = new PopupMenu(Objects.requireNonNull(getContext()), view);
        popupMenu.inflate(R.menu.menu_asset_options);
        if (STATUS_ID == Companion.getWORKING()) {
            popupMenu.getMenu().getItem(3).setVisible(false);
        }
        if (STATUS_ID == Companion.getFAULTY()) {
            // popupMenu.getMenu().getItem(3).setVisible(false);
        }
        if (STATUS_ID == Companion.getWRITTEN_OFF()) {
            popupMenu.getMenu().getItem(1).setVisible(false);
            popupMenu.getMenu().getItem(3).setVisible(false);
            popupMenu.getMenu().getItem(5).setVisible(false);
        }
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
            }
            return false;
        });
        popupMenu.show();
    }

    private void startDeleteDialog(AssetModel assetModel) {
        final DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:

                    listAdapter.notifyDataSetChanged();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();

                    break;
            }
        };


        if (getContext() != null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setMessage("You are about to delete this item ").setPositiveButton("Delete", dialogClickListener)
                    .setNegativeButton("Dismiss", dialogClickListener)
                    .show();
        }

    }

    private void setEmptyState(Boolean b) {
        LinearLayout linearLayoutEmpty2 = view.findViewById(R.id.empty_layout);
        TextView txt_empty = view.findViewById(R.id.empty_view);
        if (b) {
            linearLayoutEmpty2.setVisibility(View.VISIBLE);
            //         linearLayoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            txt_empty.setText("No assets found");
            // swipe_refresh_layout.setRefreshing(true);


            //   txt_empty.setText("Couldn't load beneficiaries");

            Log.d("revisi", "recyclerinvisible");
        } else {
            txt_empty.setText("No assets found");

//            linearLayoutEmpty.setVisibility(View.GONE);
            // swipe_refresh_layout.setRefreshing(false);
            linearLayoutEmpty2.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);


            Log.d("revisi", "recyclervisible");
        }
    }
}
