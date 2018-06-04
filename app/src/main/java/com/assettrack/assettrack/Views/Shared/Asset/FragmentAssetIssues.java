package com.assettrack.assettrack.Views.Shared.Asset;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.assettrack.assettrack.Adapters.TimeLine.OrderStatus;
import com.assettrack.assettrack.Adapters.TimeLine.Orientation;
import com.assettrack.assettrack.Adapters.TimeLine.TimeLineAdapter;
import com.assettrack.assettrack.Adapters.TimeLine.TimeLineModel;
import com.assettrack.assettrack.Adapters.V1.IssueAdapter;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Data.Parsers.IssueParser;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.IssueModel;
import com.assettrack.assettrack.R;
import com.assettrack.assettrack.Utils.DateTimeUtils;
import com.assettrack.assettrack.Utils.MyToast;
import com.wang.avi.AVLoadingIndicatorView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Eric on 2/28/2018.
 */

public class FragmentAssetIssues extends Fragment implements DatePickerDialog.OnDateSetListener {
    private AVLoadingIndicatorView avi;
    private RecyclerView recyclerView;
    private TimeLineAdapter timeLineAdapter;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private FloatingActionButton fab;
    private PrefManager prefManager;

    void startAnim() {
        avi.show();
        // or avi.smoothToShow();
    }

    void stopAnim() {
        avi.hide();
        // or avi.smoothToHide();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asset_issues, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        prefManager = new PrefManager(Objects.requireNonNull(getActivity()));
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> startDialog());
        if (!((AssetActivity) Objects.requireNonNull(getActivity())).editable) {
            fab.hide();
        }
        fab.hide();
        avi = view.findViewById(R.id.avi);

        mOrientation = Orientation.VERTICAL;
        mWithLinePadding = false;
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(getLinearLayoutManager());
        recyclerView.setHasFixedSize(true);


        getIssues(((AssetActivity) Objects.requireNonNull(getActivity())).asset.getId());
        // initData();
    }

    private void initData(ArrayList<IssueModel> issueModels) {
//        List<TimeLineModel> timeLineModels = getTimeline2(issueModels);
//        timeLineAdapter = new TimeLineAdapter(timeLineModels, mOrientation, true, new OnclickRecyclerListener() {
//            @Override
//            public void onClickListener(int position) {
//                startDialog(timeLineModels.get(position));
//            }
//
//            @Override
//            public void onLongClickListener(int position) {
//
//            }
//
//            @Override
//            public void onClickListener(int adapterPosition, View view) {
//
//            }
//
//            @Override
//            public void onCheckedClickListener(int position) {
//
//            }
//
//            @Override
//            public void onMoreClickListener(int position) {
//
//            }
//        });
        IssueAdapter listAdapter = new IssueAdapter(getActivity(), issueModels, new OnclickRecyclerListener() {
            @Override
            public void onClickListener(int position) {


                startDialog(issueModels.get(position));
//                fragment=new FragmentView();
//                Bundle args=new Bundle();
//                args.putSerializable("data",issueModels.get(position));
//                fragment.setArguments(args);
//                popOutFragments();
//                setUpView();
            }

            @Override
            public void onLongClickListener(int position) {

            }

            @Override
            public void onCheckedClickListener(int position) {

            }

            @Override
            public void onMoreClickListener(int position) {


            }

            @Override
            public void onClickListener(int adapterPosition, View view) {

                //  popupMenu(adapterPosition, view, issueModels.get(adapterPosition));
            }
        });
        listAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(listAdapter);
    }

    private void startDialog(IssueModel issueModel) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View mView = layoutInflaterAndroid.inflate(R.layout.fragment_view_issue, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(mView);
        alertDialogBuilderUserInput.setTitle("Issue Details");
        //alertDialogBuilderUserInput.setIcon(R.drawable.ic_add_black_24dp);
        Button btnEdit;

        TextView edtStart, edtEnd, edtFix, edtSoln, edtEngRemarks, edtCustRemarks, edtSafety,
                edtTravelHours, edtLabourHours, edtNextServiceDate;

        edtStart = mView.findViewById(R.id.edt_start);
        edtEnd = mView.findViewById(R.id.edt_end);
        edtFix = mView.findViewById(R.id.edt_fix);
        edtSoln = mView.findViewById(R.id.edt_soln);
        edtEngRemarks = mView.findViewById(R.id.edt_engineer_remarks);
        edtCustRemarks = mView.findViewById(R.id.edt_customer_remarks);
        edtSafety = mView.findViewById(R.id.edt_safety);
        edtTravelHours = mView.findViewById(R.id.edt_travel_hours);
        edtLabourHours = mView.findViewById(R.id.edt_labour_hours);
        edtNextServiceDate = mView.findViewById(R.id.edt_next_service_date);

        btnEdit = mView.findViewById(R.id.btn_edit);
        btnEdit.setVisibility(View.GONE);

        if (issueModel != null) {
            edtStart.setText(issueModel.getStartdate());
            edtEnd.setText(issueModel.getClosedate());
            edtFix.setText(issueModel.getFailure_desc());
            edtSoln.setText(issueModel.getFailure_soln());
            edtEngRemarks.setText(issueModel.getEngineer_comment());
            edtCustRemarks.setText(issueModel.getCustomer_comment());
            edtSafety.setText(issueModel.getSafety());
            edtTravelHours.setText(issueModel.getTravel_hours());
            edtLabourHours.setText(issueModel.getLabour_hours());
            edtNextServiceDate.setText(issueModel.getNextdueservice());

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

    private List<TimeLineModel> getTimeline2(ArrayList<IssueModel> issueModels) {
        List<TimeLineModel> timeLineModels = new LinkedList<>();
        ArrayList<IssueModel> issues = new ArrayList<>();

        issues = issueModels;
        if (issues != null && issues.size() > 0) {
            for (IssueModel issues1 : issues) {
                TimeLineModel timeLineModel = new TimeLineModel();
                timeLineModel.setPerson(issues1.getEngineername());
                timeLineModel.setComment(issues1.getEngineer_comment());
                timeLineModel.setFix(issues1.getFailure_soln());
                timeLineModel.setIssue(issues1.getFailure_soln());
                timeLineModel.setStatus(OrderStatus.COMPLETED);
                timeLineModel.setDate(issues1.getUpdated_at());
                timeLineModel.semMessage(issues1.getCustomer_comment());
                timeLineModel.setLabour_hours(issues1.getLabour_hours());
                timeLineModel.setTravel_hours(issues1.getTravel_hours());
                timeLineModel.setCustcomments(issues1.getCustomer_comment());
                timeLineModel.setEngcomments(issues1.getEngineer_comment());


                timeLineModels.add(timeLineModel);
            }
        }


        return timeLineModels;
    }

    private ArrayList<IssueModel> getIssues(int id) {

        ArrayList<IssueModel> issueModels = new ArrayList<>();

        avi.show();
        Request.Companion.getRequest(APiConstants.Companion.getAllAssets().concat("/").concat(String.valueOf(id)), prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {

                avi.hide();
            }

            @Override
            public void onError(@NotNull String error) {

                avi.hide();
            }

            @Override
            public void onSuccess(@NotNull String response) {
                Log.d("issues", response);

                avi.hide();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error")) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        if (data != null) {
                            JSONArray jsonArray = data.optJSONArray("issues");

                            ArrayList<IssueModel> issueModels = IssueParser.parse(jsonArray);
                            try {
                                AssetModel assetModel = ((AssetActivity) Objects.requireNonNull(getActivity())).asset;
                                if (assetModel != null && issueModels != null) {
                                    for (int a = 0; a < issueModels.size(); a++) {
                                        issueModels.get(a).getAssetModel().setAsset_name(assetModel.getAsset_name());
                                        issueModels.get(a).getCustomerModel().setName(assetModel.getCustomerModel().getName());
                                    }
                                }
                            } catch (Exception nm) {
                                nm.printStackTrace();
                            }
                            initData(issueModels);
                        }
                    }

                } catch (Exception nm) {

                }
                // IssueParser.parse()

            }
        });

        return issueModels;
    }

    private LinearLayoutManager getLinearLayoutManager() {
        if (mOrientation == Orientation.HORIZONTAL) {
            return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else {
            return new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        }
    }

    private void startDialog(TimeLineModel timeLineModel) {

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

        txtDate.setText(timeLineModel.getDate());
        txtBy.setText(timeLineModel.getPerson());
        edtComment.setText(timeLineModel.getComment());
        edtFix.setText(timeLineModel.getFix());
        edtIssue.setText(timeLineModel.getMessage());
        edtIssueDesc.setText(timeLineModel.getIssue());


        try {
            edtLabour.setText(timeLineModel.getLabour_hours());
            edtTravel.setText(timeLineModel.getTravel_hours());
            edtCustomerRemarks.setText(timeLineModel.getCustcomments());
            edtEngRemarks.setText(timeLineModel.getEngcomments());
        } catch (Exception nm) {
            nm.printStackTrace();
        }
        try {
            //edtLabour.setText(timeLineModel.getLabour_hours());
            edtTravel.setText(timeLineModel.getTravel_hours());
            edtCustomerRemarks.setText(timeLineModel.getCustcomments());
            edtEngRemarks.setText(timeLineModel.getEngcomments());
        } catch (Exception nm) {
            nm.printStackTrace();
        }
        try {
            // edtLabour.setText(timeLineModel.getLabour_hours());
            // edtTravel.setText(timeLineModel.getTravel_hours());
            edtCustomerRemarks.setText(timeLineModel.getCustcomments());
            edtEngRemarks.setText(timeLineModel.getEngcomments());
        } catch (Exception nm) {
            nm.printStackTrace();
        }
        try {
            //edtLabour.setText(timeLineModel.getLabour_hours());
            ///edtTravel.setText(timeLineModel.getTravel_hours());
            // edtCustomerRemarks.setText(timeLineModel.getCustcomments());
            edtEngRemarks.setText(timeLineModel.getEngcomments());
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

    private void startDialog() {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_new_issue, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
        alertDialogBuilderUserInput.setView(mView);
        alertDialogBuilderUserInput.setTitle("New  Issue");
        alertDialogBuilderUserInput.setIcon(R.drawable.ic_add_black_24dp);
        TextView txtDate, txtBy;
        txtBy = mView.findViewById(R.id.txt_by);
        txtDate = mView.findViewById(R.id.txt_date);

        txtBy.setText(prefManager.getUserData().getFull_name());
        txtDate.setText(DateTimeUtils.Companion.getToday());


        // final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
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
        theButton.setOnClickListener(new CustomListener(alertDialogAndroid));

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = (dayOfMonth + "/" + (++monthOfYear) + "/" + year);
        // txtDate.setText(date);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(calendar.getTime());


    }


    void showDatePicker(IssueModel issues) {

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(FragmentAssetIssues.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(true);
        dpd.setTitle("Next Service Date");
        dpd.vibrate(true);
        dpd.dismissOnPause(true);
        dpd.showYearPickerFirst(true);
        //dpd.setMaxDate(now);//Date(now);
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);

        dpd.show(getActivity().getFragmentManager(), "DatePicker");


    }

    private void dial() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_new_issue);
        EditText edtIssue = dialog.findViewById(R.id.edt_issue_title);
        TextView txtBy = dialog.findViewById(R.id.txt_by);

        txtBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtIssue.getText().toString().isEmpty()) {
                    MyToast.toast("Fill issue", getContext(), R.drawable.ic_error_outline_black_24dp, Toast.LENGTH_LONG);
                } else {
                    dialog.dismiss();
                }
            }
        });


    }

    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;


        public CustomListener(Dialog dialog) {
            this.dialog = dialog;

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
                return;
            }
//            if(edtComment.getText().toString().isEmpty()){
//                edtComment.setError("Required");
//                return;
//            }
            if (edtFix.getText().toString().isEmpty()) {
                edtFix.setError("Required");
                return;
            }

            if (edtIssueDesc.getText().toString().isEmpty()) {
                edtIssue.setError("Required");
                return;
            }

            if (edtSpareNeeded.getText().toString().isEmpty()) {
                edtSpareNeeded.setError("Required");
                return;
            }
            if (edtSpareUsed.getText().toString().isEmpty()) {
                edtSpareUsed.setError("Required");
                return;
            }
            if (edtCustomerComents.getText().toString().isEmpty()) {
                edtCustomerComents.setError("Required");
                return;
            }
            if (edtEngineersComents.getText().toString().isEmpty()) {
                edtEngineersComents.setError("Required");
                return;
            }
            if (edtLabourHours.getText().toString().isEmpty()) {
                edtLabourHours.setError("Required");
            }

            if (edtTravelHours.getText().toString().isEmpty()) {
                edtTravelHours.setError("Required");
            }


            IssueModel issues = new IssueModel();

//            issues.setAsset_id(Constants.id);
//            issues.setComment("");
//            issues.setDate(DateTimeUtils.getToday());
//            issues.setFix(edtFix.getText().toString());
//            issues.setIssue(edtIssueDesc.getText().toString());
//            issues.setMessage(edtIssue.getText().toString());
//            issues.setPerson("Eric");
//
//
//            issues.setParts_needed(edtSpareNeeded.getText().toString());
//            issues.setParts_used(edtSpareUsed.getText().toString());
//
//            issues.setEngineers_comments(edtEngineersComents.getText().toString());
//            issues.setCustomer_coments(edtCustomerComents.getText().toString());
//
//            issues.setLabour_hour(edtLabourHours.getText().toString());
//            issues.setTravel_hours(edtTravelHours.getText().toString());
//

            // IssueModel issueModel=new IssueModel();
            // issueModel.setAsset_id(Constants.id);
            // issueModel.set

            dialog.dismiss();

            showDatePicker(issues);
            //contentValues.put(DbConstants.);


        }


    }
}
