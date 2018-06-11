package com.assettrack.assettrack.Views.Admin.Categories;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.assettrack.assettrack.Adapters.V1.CategoryAdapter;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Data.Parsers.CategoryParser;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.CategoriesModel;
import com.assettrack.assettrack.R;
import com.assettrack.assettrack.Utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentCategoriesList extends Fragment {

    CategoryAdapter listAdapter;
    String searchtext = "";
    private ArrayList<CategoriesModel> categoriesModels;
    private ArrayList<CategoriesModel> categoriesModelsSerch;
    private View view;
    private ProgressDialog progressDialog;
    private PrefManager prefManager;

    private EditText edtSearch;
    private LinearLayout linearLayoutEmpty;
    private SearchView search;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private ActionMode mActionMode;
    private SwipeRefreshLayout swipe_refresh_layout;

    private int STATUS_ID;


    private Fragment fragment;
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

    void setUpView() {
        if (fragment != null) {
            FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment)
                    .addToBackStack(null).commit();
        }

    }

    void popOutFragments() {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
    }

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
        try {
            ((ActivityManageCategories) Objects.requireNonNull(getActivity())).setFab(R.drawable.ic_add_black_24dp, true);

        } catch (Exception nm) {
            nm.printStackTrace();
        }


        search = view.findViewById(R.id.search_bar);
        edtSearch = view.findViewById(R.id.edt_search);
        prefManager = new PrefManager(getActivity());
        swipe_refresh_layout = view.findViewById(R.id.swipeRefreshView);


        if (NetworkUtils.Companion.isConnectionFast(getActivity())) {

            swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
            swipe_refresh_layout.setBackgroundResource(android.R.color.white);
            swipe_refresh_layout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);
            swipe_refresh_layout.setRefreshing(true);


            initData();
        } else {
            snack("Please check your internet connection");
            swipe_refresh_layout.setRefreshing(false);

        }
        swipe_refresh_layout.setOnRefreshListener(() -> {

            swipe_refresh_layout.setRefreshing(true);
            if (NetworkUtils.Companion.isConnectionFast(getActivity())) {
                initData();
            } else {
                swipe_refresh_layout.setRefreshing(false);

                snack("Check your internet connection");
            }


        });

        initUI(new ArrayList<>());
        initSearchView();
        // initData();


    }

    private ArrayList<CategoriesModel> initData() {
        categoriesModels = new ArrayList<>();


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("working....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = APiConstants.Companion.getCategorieslist();


        // Log.d("getData",url+"\n"+params.toString()+"\n"+prefManager.getToken());
        Request.Companion.getRequest(url, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    // progressDialog.setMessage(error.getMessage());
                    progressDialog.dismiss();
                }
                Log.d("getData", error.getErrorBody());
            }

            @Override
            public void onError(@NotNull String error) {

                if (progressDialog != null && progressDialog.isShowing()) {
                    // progressDialog.setMessage(error);
                    progressDialog.dismiss();
                }
                Log.d("getData", error);

            }

            @Override
            public void onSuccess(@NotNull String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    //progressDialog.setMessage(response);
                    progressDialog.dismiss();
                }
                Log.d("getData", response);

                try {

                    JSONObject jsonObject = new JSONObject(response).getJSONObject("data");
                    //if(!jsonObject.getBoolean("error")){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    categoriesModels = CategoryParser.parse(jsonArray);
                    //}
                } catch (Exception nm) {

                    Log.d("getData", nm.toString());
                }

                initUI(categoriesModels);
            }
        });


        return categoriesModels;
    }

    private void initSearchView() {

        try {
            SearchManager manager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);


            search.setOnClickListener(v -> search.setIconified(false));
            search.setQueryHint("Search categories list");

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

                        categoriesModelsSerch = new ArrayList<>();
                        if (categoriesModels != null) {
                            for (CategoriesModel categoriesModel : categoriesModels) {
                                if (categoriesModel.getName().toLowerCase().contains(searchtext)
                                        || String.valueOf(categoriesModel.getId()).contains(searchtext)) {

                                    categoriesModelsSerch.add(categoriesModel);
                                }
                            }
                        }
                        if (listAdapter != null && categoriesModelsSerch != null) {
                            listAdapter.updateList(categoriesModelsSerch);
                            listAdapter.notifyDataSetChanged();
                        }


                    } else {

                        if (listAdapter != null && categoriesModels != null) {
                            listAdapter.updateList(categoriesModels);
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

                        categoriesModelsSerch = new ArrayList<>();
                        if (categoriesModels != null) {
                            for (CategoriesModel categoriesModel : categoriesModels) {
                                if (categoriesModel.getName().toLowerCase().contains(searchtext)
                                        || String.valueOf(categoriesModel.getId()).contains(searchtext)) {

                                    categoriesModelsSerch.add(categoriesModel);
                                }
                            }
                        }
                        if (listAdapter != null && categoriesModelsSerch != null) {
                            listAdapter.updateList(categoriesModelsSerch);
                            listAdapter.notifyDataSetChanged();
                        }


                    } else {

                        if (listAdapter != null && categoriesModels != null) {
                            listAdapter.updateList(categoriesModels);
                        }

                        searchtext = "";

                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
    }

    private void initUI(ArrayList<CategoriesModel> categoriesModels) {


        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        if (categoriesModels != null && categoriesModels.size() > 0) {
            mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mStaggeredLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());


            listAdapter = new CategoryAdapter(getContext(), categoriesModels, new OnclickRecyclerListener() {
                @Override
                public void onClickListener(int position) {
                    // startViewDialog(categoriesModels.get(position));
                    // popupMenu(adapterPosition, view, customerModels.get(adapterPosition));

                    //  popupMenu(position, view, categoriesModels.get(position));
//                    fragment=new FragmentView();
//                    Bundle args=new Bundle();
//                    args.putSerializable("data",categoriesModels.get(position));
//                    fragment.setArguments(args);
//                    popOutFragments();
//                    setUpView();
                }

                @Override
                public void onLongClickListener(int position) {

                }

                @Override
                public void onCheckedClickListener(int position) {
//                    int count = 0;
//                    if (categoriesModels.get(position).isChecked()) {
//                        categoriesModels.get(position).setChecked(false);
//
//                        // count--;
//
//                    } else {
//                        categoriesModels.get(position).setChecked(true);
//
//                    }
//
//
//                    listAdapter.notifyItemChanged(position, categoriesModels.get(position));
//
//
//                    for (CategoriesModel categoriesModel : categoriesModels) {
//                        if (categoriesModel.isChecked()) {
//                            count++;
//
//                        }
//                    }
//
//                    if (count > 0) {
//                        mActionMode = Objects.requireNonNull(getActivity()).startActionMode(callback);
//                    } else {
//                        if (mActionMode != null) {
//                            mActionMode.finish();
//                        }
//                    }

                }

                @Override
                public void onMoreClickListener(int position) {


                }

                @Override
                public void onClickListener(int adapterPosition, View view) {

                    popupMenu(adapterPosition, view, categoriesModels.get(adapterPosition));

                }
            });
            // listAdapter.notifyDataSetChanged();

            recyclerView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();

            setEmptyState(listAdapter.getItemCount() < 1);
            if (swipe_refresh_layout != null && swipe_refresh_layout.isRefreshing()) {
                swipe_refresh_layout.setRefreshing(false);
            }

        } else {
            if (swipe_refresh_layout != null && swipe_refresh_layout.isRefreshing()) {
                swipe_refresh_layout.setRefreshing(false);
            }
            setEmptyState(true);

            Log.d("Loohj", "assetmodels is null");

        }

    }

    private void popupMenu(int pos, View view, CategoriesModel categoriesModel) {
        PopupMenu popupMenu = new PopupMenu(Objects.requireNonNull(getContext()), view);
        popupMenu.inflate(R.menu.menu_asset_options);

//        popupMenu.getMenu().getItem(3).setVisible(false);
//        popupMenu.getMenu().getItem(5).setVisible(false);
        // popupMenu.getMenu().getItem(4).setVisible(false);

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.view:

                    startViewDialog(categoriesModel);
                    break;
                case R.id.edit:

                    startEditDialog(categoriesModel);
                    break;
                case R.id.delete:

                    startDeleteDialog(categoriesModel);
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

    private void startDeleteDialog(CategoriesModel categoriesModel) {
        final DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:

                    deleteItem(categoriesModel);
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


    private void startEditDialog(CategoriesModel categoriesModel) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_category_details_edit, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        alertDialogBuilderUserInput.setView(mView);
        alertDialogBuilderUserInput.setTitle("Category Details");

        EditText name, idd;
        name = mView.findViewById(R.id.txt_customer_name);
        // idd = mView.findViewById(R.id.txt_customer_id);
        // name=mView.findViewById(R.id.txt_customer_name);


        name.setText(categoriesModel.getName());
        // idd.setText(categoriesModel.getId());
        //name.setText(categoriesModel.getName());


        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Save", (dialogBox, id) -> {
                    // ToDo get user input here

                    // dialogBox.dismiss();

                })

                .setNegativeButton("Dismiss",
                        (dialogBox, id) -> dialogBox.cancel());

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

        Button theButton = alertDialogAndroid.getButton(DialogInterface.BUTTON_POSITIVE);
        theButton.setOnClickListener(new CustomListener(alertDialogAndroid, categoriesModel.getId()));

    }

    private void startViewDialog(CategoriesModel categoriesModel) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_category_details_edit, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        alertDialogBuilderUserInput.setView(mView);
        alertDialogBuilderUserInput.setTitle("Customer Details");

        TextView name, email, phone, address, idd;
        name = mView.findViewById(R.id.txt_customer_name);

        name.setText(categoriesModel.getName());


        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Dismiss", (dialogBox, id) -> {
                    // ToDo get user input here

                    // dialogBox.dismiss();

                });

//                .setNegativeButton("Dismiss",
//                        (dialogBox, id) -> dialogBox.cancel());

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();


    }

    public void refresh() {
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
            // initUI();
        }
    }

    private void setEmptyState(Boolean b) {
        LinearLayout linearLayoutEmpty2 = view.findViewById(R.id.empty_layout);
        TextView txt_empty = view.findViewById(R.id.empty_view);
        if (b) {
            linearLayoutEmpty2.setVisibility(View.VISIBLE);
            //         linearLayoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            txt_empty.setText("No data found");
            // swipe_refresh_layout.setRefreshing(true);


            //   txt_empty.setText("Couldn't load beneficiaries");

            Log.d("revisi", "recyclerinvisible");
        } else {
            txt_empty.setText("No data found");

//            linearLayoutEmpty.setVisibility(View.GONE);
            // swipe_refresh_layout.setRefreshing(false);
            linearLayoutEmpty2.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            Log.d("revisi", "recyclervisible");
        }
    }

    private void updateCustomer(CategoriesModel categoriesModel) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", categoriesModel.getId());
            jsonObject.put("Name", categoriesModel.getName());

        } catch (Exception nm) {
            nm.printStackTrace();
        }

        Request.Companion.putRequest(APiConstants.Companion.getUpdatecategory() + "" + categoriesModel.getId() + "/update", jsonObject, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("updateCustomer", error.toString());
                snack(error.getMessage());

            }

            @Override
            public void onError(@NotNull String error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                snack(error);
                Log.d("updateCustomer", error);

            }

            @Override
            public void onSuccess(@NotNull String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("updateCustomer", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.optBoolean("error")) {
                        snack("Updated Successfully");
                        //finish();
                    } else {
                        snack("Error updating asset");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    snack("Error updating asset");
                }

            }
        });
    }

    private void snack(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    private void deleteItem(CategoriesModel categoriesModel) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Deleting....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();


        Request.Companion.deleteRequest(APiConstants.Companion.getDeletecategory() + "/" + categoriesModel.getId(), prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                snack(error.getMessage());
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                Log.d("delete", error.getMessage());
            }

            @Override
            public void onError(@NotNull String error) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                Log.d("delete", error);
                snack(error);
            }

            @Override
            public void onSuccess(@NotNull String response) {
                Log.d("delete", response);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                snack(response);
                if (listAdapter != null) {
                    listAdapter.notifyDataSetChanged();
                    initData();
                }

            }
        });

    }

    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;
        private final int keyid;
        // private final CategoriesModel categoriesModel;


        public CustomListener(Dialog dialog, int keyid) {
            this.dialog = dialog;
            this.keyid = keyid;

        }

        @Override
        public void onClick(View v) {
            EditText name, email, phone, address, idd;
            name = dialog.findViewById(R.id.txt_customer_name);
            // name=mView.findViewById(R.id.txt_customer_name);


            if (name.getText().toString().isEmpty()) {
                name.setError("Required");
                name.requestFocus();
                return;
            }


            CategoriesModel categoriesModel = new CategoriesModel();
            categoriesModel.setName(name.getText().toString());
            categoriesModel.setId(keyid);

            dialog.dismiss();
            updateCustomer(categoriesModel);

//            ContentValues contentValues = new ContentValues();
//            contentValues.put(DbConstants.cust_name, categoriesModel.getName());
//            contentValues.put(DbConstants.cust_id, categoriesModel.getName());
//            contentValues.put(DbConstants.cust_email, categoriesModel.getEmail());
//            contentValues.put(DbConstants.cust_address, categoriesModel.getEmail());
//            contentValues.put(DbConstants.cust_physical_address, categoriesModel.getPhysical_address());
//            contentValues.put(DbConstants.cust_telephone, categoriesModel.getTelephone());
//
//
//            if (new DbOperations(getActivity()).update(DbConstants.TABLE_CLIENT, DbConstants.KEY_ID, keyid, contentValues)) {
//                dialog.dismiss();
//            }

        }


    }


}
