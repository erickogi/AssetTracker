package com.assettrack.assettrack.Views.Admin.Issues;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.assettrack.assettrack.Adapters.ListAdapter;
import com.assettrack.assettrack.Adapters.V1.IssueAdapter;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Data.Parsers.IssueParser;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.IssueModel;
import com.assettrack.assettrack.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentIssueList extends Fragment {
    IssueAdapter listAdapter;
    String searchtext = "";
    private View view;
    private EditText edtSearch;
    private LinearLayout linearLayoutEmpty;
    private SearchView search;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private ActionMode mActionMode;
    private int STATUS_ID;

    private ArrayList<IssueModel> issueModels;
    private ProgressDialog progressDialog;
    private PrefManager prefManager;

    private Fragment fragment;

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

        search = view.findViewById(R.id.search_bar);
        edtSearch = view.findViewById(R.id.edt_search);

        prefManager = new PrefManager(getActivity());

        initUI(new ArrayList<>());
        initSearchView();
        initData();


    }

    private ArrayList<IssueModel> initData() {
        issueModels = new ArrayList<>();


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("working....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = APiConstants.Companion.getAllIssues();


        // Log.d("getData",url+"\n"+params.toString()+"\n"+prefManager.getToken());
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

                    issueModels = IssueParser.parse(jsonArray);
                    //}
                } catch (Exception nm) {

                    Log.d("getData", nm.toString());
                }

                initUI(issueModels);
            }
        });


        return issueModels;
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

    private void initUI(ArrayList<IssueModel> issueModels) {


        recyclerView = view.findViewById(R.id.recyclerView);
        if (issueModels != null && issueModels.size() > 0) {
            mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mStaggeredLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            // ArrayList<EngineerModel> engineerModels, int status, Context context, OnclickRecyclerListener onclickRecyclerListener

            listAdapter = new IssueAdapter(getActivity(),issueModels, new OnclickRecyclerListener() {
                @Override
                public void onClickListener(int position) {
                    fragment=new FragmentView();
                    Bundle args=new Bundle();
                    args.putSerializable("data",issueModels.get(position));
                    fragment.setArguments(args);
                    popOutFragments();
                    setUpView();
                }

                @Override
                public void onLongClickListener(int position) {

                }

                @Override
                public void onCheckedClickListener(int position) {
                    int count = 0;
                    if (issueModels.get(position).isChecked()) {
                        issueModels.get(position).setChecked(false);

                        // count--;

                    } else {
                        issueModels.get(position).setChecked(true);

                    }


                    listAdapter.notifyItemChanged(position, issueModels.get(position));


                    for (IssueModel issueModel : issueModels) {
                        if (issueModel.isChecked()) {
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

                    popupMenu(adapterPosition, view, issueModels.get(adapterPosition));
                }
            });
            // listAdapter.notifyDataSetChanged();

            recyclerView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();

            setEmptyState(listAdapter.getItemCount() < 1);


        } else {
            Log.d("Loohj", "assetmodels is null");

        }

    }

    private void popupMenu(int pos, View view, IssueModel issueModel) {
        PopupMenu popupMenu = new PopupMenu(Objects.requireNonNull(getContext()), view);
        popupMenu.inflate(R.menu.menu_asset_options);

        popupMenu.getMenu().getItem(3).setVisible(false);
        popupMenu.getMenu().getItem(5).setVisible(false);
        popupMenu.getMenu().getItem(4).setVisible(false);

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.view:

                    startViewDialog(issueModel);
                    break;
                case R.id.edit:

                    startEditDialog(issueModel);
                    break;
                case R.id.delete:

                    startDeleteDialog(issueModel);
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

    private void startDeleteDialog(IssueModel issueModel) {
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


    private void startEditDialog(IssueModel issueModel) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_new_issue, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(mView);
        alertDialogBuilderUserInput.setTitle("New  Issue");
        alertDialogBuilderUserInput.setIcon(R.drawable.ic_add_black_24dp);
        TextView txtDate, txtBy;
        txtBy = mView.findViewById(R.id.txt_by);
        txtDate = mView.findViewById(R.id.txt_date);

        EditText edtIssue, edtIssueDesc, edtFix, edtComment, edtTravel, edtLabour, edtEngRemarks, edtCustomerRemarks;
        //TextView txtDate,txtBy;


        edtIssue = mView.findViewById(R.id.edt_issue_title);
        edtIssueDesc = mView.findViewById(R.id.edt_issue_desc);
        edtFix = mView.findViewById(R.id.edt_fix);
        edtComment = mView.findViewById(R.id.edt_comment);

        edtEngRemarks = mView.findViewById(R.id.edt_engineer_remarks);
        edtCustomerRemarks = mView.findViewById(R.id.edt_customer_remarks);

        edtLabour = mView.findViewById(R.id.edt_labours_hours);
        edtTravel = mView.findViewById(R.id.edt_travel_hours);

        txtBy = mView.findViewById(R.id.txt_by);
        txtDate = mView.findViewById(R.id.txt_date);

        txtDate.setText(issueModel.getStartdate());
        txtBy.setText(issueModel.getEngineername());
        edtComment.setText(issueModel.getEngineer_comment());
        edtFix.setText(issueModel.getFailure_soln());
        edtIssue.setText(issueModel.getFailure_desc());
        edtIssueDesc.setText(issueModel.getFailure_desc());


        try {
            edtLabour.setText(issueModel.getLabour_hours());
            edtTravel.setText(issueModel.getTravel_hours());
            edtCustomerRemarks.setText(issueModel.getCustomer_comment());
            edtEngRemarks.setText(issueModel.getEngineer_comment());
        } catch (Exception nm) {
            nm.printStackTrace();
        }
        try {
            //edtLabour.setText(timeLineModel.getLabour_hours());
            edtTravel.setText(issueModel.getTravel_hours());
            edtCustomerRemarks.setText(issueModel.getCustomer_comment());
            edtEngRemarks.setText(issueModel.getEngineer_comment());
        } catch (Exception nm) {
            nm.printStackTrace();
        }
        try {
            // edtLabour.setText(timeLineModel.getLabour_hours());
            // edtTravel.setText(timeLineModel.getTravel_hours());
            edtCustomerRemarks.setText(issueModel.getCustomer_comment());
            edtEngRemarks.setText(issueModel.getEngineer_comment());
        } catch (Exception nm) {
            nm.printStackTrace();
        }
        try {
            //edtLabour.setText(timeLineModel.getLabour_hours());
            ///edtTravel.setText(timeLineModel.getTravel_hours());
            // edtCustomerRemarks.setText(timeLineModel.getCustcomments());
            edtEngRemarks.setText(issueModel.getEngineer_comment());
        } catch (Exception nm) {
            nm.printStackTrace();
        }


        // final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        alertDialogBuilderUserInput
                .setCancelable(false)
//                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogBox, int id) {
//                        // ToDo get user input here
//
//                        dialogBox.dismiss();
//
//                    }
//                })

                .setNegativeButton("Dismiss",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

        //Button theButton = alertDialogAndroid.getButton(DialogInterface.BUTTON_POSITIVE);
        //theButton.setOnClickListener(new CustomListener(alertDialogAndroid, issueModel.getId(),issueModel));

    }

    private void startViewDialog(IssueModel issueModel) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_new_issue_report, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(mView);
        alertDialogBuilderUserInput.setTitle("Issue Details");
        //alertDialogBuilderUserInput.setIcon(R.drawable.ic_add_black_24dp);

        TextView edtIssue, edtIssueDesc, edtFix, edtComment, edtTravel, edtLabour, edtEngRemarks, edtCustomerRemarks;
        TextView txtDate, txtBy;

        edtIssue = mView.findViewById(R.id.edt_issue_title);
        edtIssueDesc = mView.findViewById(R.id.edt_issue_desc);
        edtFix = mView.findViewById(R.id.edt_fix);
        edtComment = mView.findViewById(R.id.edt_comment);

        edtEngRemarks = mView.findViewById(R.id.edt_engineer_remarks);
        edtCustomerRemarks = mView.findViewById(R.id.edt_customer_remarks);

        edtLabour = mView.findViewById(R.id.edt_labours_hours);
        edtTravel = mView.findViewById(R.id.edt_travel_hours);

        txtBy = mView.findViewById(R.id.txt_by);
        txtDate = mView.findViewById(R.id.txt_date);

        txtDate.setText(issueModel.getStartdate());
        txtBy.setText(issueModel.getEngineername());
        edtComment.setText(issueModel.getEngineer_comment());
        edtFix.setText(issueModel.getFailure_soln());
        edtIssue.setText(issueModel.getFailure_desc());
        edtIssueDesc.setText(issueModel.getFailure_desc());


        try {
            edtLabour.setText(issueModel.getLabour_hours());
            edtTravel.setText(issueModel.getTravel_hours());
            edtCustomerRemarks.setText(issueModel.getCustomer_comment());
            edtEngRemarks.setText(issueModel.getEngineer_comment());
        } catch (Exception nm) {
            nm.printStackTrace();
        }
        try {
            //edtLabour.setText(timeLineModel.getLabour_hours());
            edtTravel.setText(issueModel.getTravel_hours());
            edtCustomerRemarks.setText(issueModel.getCustomer_comment());
            edtEngRemarks.setText(issueModel.getEngineer_comment());
        } catch (Exception nm) {
            nm.printStackTrace();
        }
        try {
            // edtLabour.setText(timeLineModel.getLabour_hours());
            // edtTravel.setText(timeLineModel.getTravel_hours());
            edtCustomerRemarks.setText(issueModel.getCustomer_comment());
            edtEngRemarks.setText(issueModel.getEngineer_comment());
        } catch (Exception nm) {
            nm.printStackTrace();
        }
        try {
            //edtLabour.setText(timeLineModel.getLabour_hours());
            ///edtTravel.setText(timeLineModel.getTravel_hours());
            // edtCustomerRemarks.setText(timeLineModel.getCustcomments());
            edtEngRemarks.setText(issueModel.getEngineer_comment());
        } catch (Exception nm) {
            nm.printStackTrace();
        }


        // final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
        alertDialogBuilderUserInput
                .setCancelable(false)
//                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogBox, int id) {
//                        // ToDo get user input here
//
//                        dialogBox.dismiss();
//
//                    }
//                })

                .setNegativeButton("Okay",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    public void refresh() {
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
            initUI(new ArrayList<>());
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

    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;
        private final int keyid;
        private final IssueModel issueModel;


        public CustomListener(Dialog dialog, int keyid, IssueModel issueModel) {
            this.dialog = dialog;
            this.keyid = keyid;
            this.issueModel = issueModel;

        }

        @Override
        public void onClick(View v) {
            EditText edtIssue, edtIssueDesc, edtFix, edtComment, edtSpareUsed, edtSpareNeeded, edtTravelHours, edtLabourHours, edtCustomerComents, edtEngineersComents;

            TextView txtDate, txtBy;

            edtLabourHours = dialog.findViewById(R.id.edt_labour_hours);
            edtTravelHours = dialog.findViewById(R.id.edt_travel_hours);

            edtIssue = dialog.findViewById(R.id.edt_issue_title);
            edtIssueDesc = dialog.findViewById(R.id.edt_issue_desc);
            edtFix = dialog.findViewById(R.id.edt_fix);
            edtComment = dialog.findViewById(R.id.edt_comment);
            edtSpareNeeded = dialog.findViewById(R.id.edt_spare_need);
            edtSpareUsed = dialog.findViewById(R.id.edt_spare_used);

            edtCustomerComents = dialog.findViewById(R.id.edt_customer_remarks);
            edtEngineersComents = dialog.findViewById(R.id.edt_engineer_remarks);


            txtBy = dialog.findViewById(R.id.txt_by);
            txtDate = dialog.findViewById(R.id.txt_date);

            if (edtIssue.getText().toString().isEmpty()) {
                edtIssue.setError("Required");
                edtTravelHours.requestFocus();
                return;
            }
//            if(edtComment.getText().toString().isEmpty()){
//                edtComment.setError("Required");
//                return;
//            }
            if (edtFix.getText().toString().isEmpty()) {
                edtTravelHours.requestFocus();
                edtFix.setError("Required");
                return;
            }

            if (edtIssueDesc.getText().toString().isEmpty()) {
                edtTravelHours.requestFocus();
                edtIssue.setError("Required");
                return;
            }

            if (edtSpareNeeded.getText().toString().isEmpty()) {
                edtSpareNeeded.setError("Required");
                edtTravelHours.requestFocus();
                return;
            }
            if (edtSpareUsed.getText().toString().isEmpty()) {
                edtSpareUsed.setError("Required");
                edtTravelHours.requestFocus();
                return;
            }
            if (edtCustomerComents.getText().toString().isEmpty()) {
                edtCustomerComents.setError("Required");
                edtTravelHours.requestFocus();
                return;
            }
            if (edtEngineersComents.getText().toString().isEmpty()) {
                edtEngineersComents.setError("Required");
                edtTravelHours.requestFocus();
                return;
            }
            if (edtLabourHours.getText().toString().isEmpty()) {
                edtLabourHours.setError("Required");
                edtTravelHours.requestFocus();
                return;
            }

            if (edtTravelHours.getText().toString().isEmpty()) {
                edtTravelHours.setError("Required");
                edtTravelHours.requestFocus();
                return;
            }


//            IssueModel issueModel=new IssueModel();
//            issueModel.setId((issueModel.getId()));
//            issueModel.setAsset_code(issueModel.getAsset_code());
//            issueModel.setAssets_id(issueModel.getAssets_id());
//            issueModel.setStartdate(issueModel);
//            //issueModel.setA(issueModel.getAsset_name());
//            issueModel.setCustomers_id(issueModel.getCustomers_id());
//            issueModel.setClosedate(DateTimeUtils.Companion.getNow());
//            issueModel.setEngineer_id(issueModel.getEngineer_id());
//            issueModel.setEngineername(issueModel.getEngineername());
//            issueModel.setIssue_status("0");
//            issueModel.setWork_tickets(issueModel.getWork_tickets());
//            issueModel.setNextdueservice(issueModel.getNextdueservice());
//            issueModel.setLabour_hours(edtLabourHours.getText().toString());
//            issueModel.setTravel_hours(edtTravelHours.getText().toString());
//            issueModel.setFailure_soln(edtFix.getText().toString());
//            issueModel.setFailure_desc(edtIssueDesc.getText().toString());
//           // issueModel.setA(edtSpareNeeded.getText().toString());
//            issueModel.setCustomer_comment(edtCustomerComents.getText().toString());
//            issueModel.setEngineer_comment(edtEngineersComents.getText().toString());
//            //issueModel.setTravel_hours(edtTravelHours.getText().toString());
//
//
//            "id":5,
//                    "Asset": 1,
//                    "AssetCode": "62294",
//                    "StartDate": "2018-2-1 12:41:49",
//                    "CloseDate": "2018-5-16 12:41:56",
//                    "NextdueService": "2019-5-18",
//                    "TravelHours": 2,
//                    "LabourHours": "",
//                    "FailureDesc": "description",
//                    "FailurSolution": "solution",
//                    "Status": 1,
//                    "Safety": "safety",
//                    "Engineer": 5,
//                    "EngineerComment": "comment",
//                    "CustomerId": 4,
//                    "CustomerComment": " comment"
//            fieldsName.put(DbConstants.KEY_ID, "INTEGER PRIMARY KEY  AUTOINCREMENT");
//            fieldsName.put(DbConstants.asset_id, "varchar ");
//            fieldsName.put(DbConstants.message, "varchar ");
//            fieldsName.put(DbConstants.date, "varchar ");
//            fieldsName.put(DbConstants.issue, "varchar ");
//            fieldsName.put(DbConstants.fix, "varchar ");
//            fieldsName.put(DbConstants.comment, "varchar ");
//            fieldsName.put(DbConstants.person, "varchar ");
//
//            fieldsName.put(DbConstants.parts_needed, "varchar ");
//            fieldsName.put(DbConstants.parts_used, "varchar ");
//
//
//            fieldsName.put(DbConstants.next_service, "varchar ");
//
//
//            fieldsName.put(DbConstants.cusomerremarks, "varchar ");
//            fieldsName.put(DbConstants.engineersremarks, "varchar ");
//            fieldsName.put(DbConstants.labourhours, "varchar ");
//            fieldsName.put(DbConstants.travelhours, "varchar ");
//


//            ContentValues contentValues = new ContentValues();
//            contentValues.put(DbConstants.issuse_asset_id, issueModel.getAsset_id());
//            contentValues.put(DbConstants.issuse_asset_code, issueModel.getAsset_code());
//            contentValues.put(DbConstants.issuse_asset_name, issueModel.getAsset_name());
//            contentValues.put(DbConstants.issuse_customer_name, issueModel.getCustomer_name());
//            contentValues.put(DbConstants.issuse_customer_id, issueModel.getCustomer_id());
//            contentValues.put(DbConstants.issuse_engineer_id, issueModel.getEngineer_id());
//            contentValues.put(DbConstants.issuse_engineer_name, issueModel.getEngineer_name());
//            contentValues.put(DbConstants.issuse_date, issueModel.getDate());
//            contentValues.put(DbConstants.issuse_issue_status, issueModel.getIssue_status());
//            contentValues.put(DbConstants.issuse_failure_desc, issueModel.getFailure_desc());
//            contentValues.put(DbConstants.issuse_failure_soln, issueModel.getFailure_soln());
//            contentValues.put(DbConstants.issuse_labour_hours, issueModel.getLabour_hours());
//            contentValues.put(DbConstants.travelhours, issueModel.getTravel_hours());
//            contentValues.put(DbConstants.issuse_engineer_comment, issueModel.getEngineer_comment());
//            contentValues.put(DbConstants.issuse_customer_comment, issueModel.getCustomer_comment());
//
//            DbOperations dbOperations = new DbOperations(getContext());
//
//            if (dbOperations.insert(DbConstants.TABLE_ISSUE_V1, contentValues)) {
//
//                initUI();
//            } else {
//                MyToast.toast("Issue not updated", getContext(), R.drawable.ic_error_outline_black_24dp, Constants.TOAST_LONG);
//            }


            dialog.dismiss();
        }


    }
}
