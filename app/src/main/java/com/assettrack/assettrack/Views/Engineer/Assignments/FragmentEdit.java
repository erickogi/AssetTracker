package com.assettrack.assettrack.Views.Engineer.Assignments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidnetworking.error.ANError;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.IssueModel;
import com.assettrack.assettrack.R;
import com.assettrack.assettrack.Utils.NetworkUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class FragmentEdit extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    TextInputEditText edtStart, edtEnd, edtFix, edtSoln, edtEngRemarks, edtCustRemarks, edtSafety,
            edtTravelHours, edtLabourHours, edtNextServiceDate;
    private ProgressDialog progressDialog;
    private PrefManager prefManager;
    private View view;
    private Button btnEdit;
    private Button btnSave;

    private IssueModel issueModel = null;
    private Fragment fragment;
    private String date;
    private String time;
    private int isWhich = 0;

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
        return inflater.inflate(R.layout.fragment_edit_issue, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        //((A) Objects.requireNonNull(getActivity())).setFab(R.drawable.ic_save_black_24dp,false);

        prefManager = new PrefManager(Objects.requireNonNull(getActivity()));
        Bundle args = getArguments();
        if (args != null) {
            try {
                issueModel = (IssueModel) getArguments().getSerializable("data");
            } catch (Exception nm) {
                nm.printStackTrace();
                issueModel = null;
            }
        }
        edtStart = view.findViewById(R.id.edt_start);
        edtEnd = view.findViewById(R.id.edt_end);
        edtFix = view.findViewById(R.id.edt_fix);
        edtSoln = view.findViewById(R.id.edt_soln);
        edtEngRemarks = view.findViewById(R.id.edt_engineer_remarks);
        edtCustRemarks = view.findViewById(R.id.edt_customer_remarks);
        edtSafety = view.findViewById(R.id.edt_safety);
        edtTravelHours = view.findViewById(R.id.edt_travel_hours);
        edtLabourHours = view.findViewById(R.id.edt_labour_hours);
        edtNextServiceDate = view.findViewById(R.id.edt_next_service_date);
        btnSave = view.findViewById(R.id.btn_save);

        edtStart.setEnabled(false);
        edtEnd.setEnabled(false);
        edtEnd.setVisibility(View.GONE);

        edtNextServiceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWhich = 2;
                datePicker();
            }
        });
//        edtStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isWhich=0;
//                datePicker();
//            }
//        });
//        edtEnd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isWhich=1;
//                datePicker();
//            }
//        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.Companion.isConnectionFast(getActivity())) {
                    update();
                } else {
                    snack("Check your internet connection");
                }
            }
        });

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


    }

    private void update() {
//        if (edtStart.getText().toString().isEmpty()||edtStart.getText().toString().equals("null")) {
//            edtStart.setError("Required");
//            edtStart.requestFocus();
//            snack("");
//            return;
//        }
//
//        if (edtEnd.getText().toString().isEmpty()||edtEnd.getText().toString().equals("null")) {
//            edtEnd.setError("Required");
//            edtEnd.requestFocus();
//            return;
//        }

        if (edtFix.getText().toString().isEmpty() || edtFix.getText().toString().equals("null")) {
            edtFix.setError("Required");
            edtFix.requestFocus();
            return;
        }

        if (edtSoln.getText().toString().isEmpty() || edtSoln.getText().toString().equals("null")) {
            edtSoln.setError("Required");
            edtSoln.requestFocus();
            return;
        }
        if (edtEngRemarks.getText().toString().isEmpty() || edtEngRemarks.getText().toString().equals("null")) {
            edtEngRemarks.setError("Required");
            edtEngRemarks.requestFocus();
            return;
        }
        if (edtCustRemarks.getText().toString().isEmpty() || edtCustRemarks.getText().toString().equals("null")) {
            edtCustRemarks.setError("Required");
            edtCustRemarks.requestFocus();
            return;
        }
        if (edtSafety.getText().toString().isEmpty() || edtSafety.getText().toString().equals("null")) {
            edtSafety.setError("Required");
            edtSafety.requestFocus();
            return;
        }
        if (edtTravelHours.getText().toString().isEmpty() || edtTravelHours.getText().toString().equals("null")) {
            edtTravelHours.setError("Required");
            edtTravelHours.requestFocus();
            return;
        }
        if (edtLabourHours.getText().toString().isEmpty() || edtLabourHours.getText().toString().equals("null")) {
            edtLabourHours.setError("Required");
            edtLabourHours.requestFocus();
            return;
        }
        if (edtNextServiceDate.getText().toString().isEmpty() || edtNextServiceDate.getText().toString().equals("null")) {
            edtNextServiceDate.setError("Required");
            edtNextServiceDate.requestFocus();
            return;
        }


        IssueModel issueModelnew = issueModel;
        issueModelnew.setStartdate(edtStart.getText().toString());
        issueModelnew.setClosedate("2018-12-12 12:12:12");
        issueModelnew.setFailure_desc(edtFix.getText().toString());
        issueModelnew.setFailure_soln(edtSoln.getText().toString());
        issueModelnew.setEngineer_comment(edtEngRemarks.getText().toString());
        issueModelnew.setCustomer_comment(edtCustRemarks.getText().toString());
        issueModelnew.setTravel_hours(edtTravelHours.getText().toString());
        issueModelnew.setLabour_hours(edtLabourHours.getText().toString());
        issueModelnew.setNextdueservice(edtNextServiceDate.getText().toString());

        updateIssues(issueModelnew);


    }

    private void updateIssues(IssueModel issueModel) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", issueModel.getId());
            jsonObject.put("Asset", issueModel.getAssets_id());
            jsonObject.put("AssetCode", issueModel.getAsset_code());
            jsonObject.put("StartDate", issueModel.getStartdate());
            jsonObject.put("CloseDate", issueModel.getClosedate());
            jsonObject.put("NextdueService", issueModel.getNextdueservice());
            jsonObject.put("TravelHours", issueModel.getTravel_hours());
            jsonObject.put("LabourHours", issueModel.getLabour_hours());
            jsonObject.put("FailureDesc", issueModel.getFailure_desc());
            jsonObject.put("FailurSolution", issueModel.getFailure_soln());
            jsonObject.put("Status", "1");
            jsonObject.put("Safety", issueModel.getSafety());
            jsonObject.put("Engineer", issueModel.getEngineer_id());
            jsonObject.put("EngineerComment", issueModel.getEngineer_comment());
            jsonObject.put("CustomerId", issueModel.getCustomers_id());
            jsonObject.put("CustomerComment", issueModel.getCustomer_comment());


        } catch (Exception nm) {
            nm.printStackTrace();
        }

        Log.d("updateIssueur", APiConstants.Companion.getUpdateIssue() + "" + issueModel.getId() + "/update");
        Request.Companion.putRequest(APiConstants.Companion.getUpdateIssue() + "" + issueModel.getId() + "/update", jsonObject, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("updateIssue", error.toString());

                snack(error.getMessage());

            }

            @Override
            public void onError(@NotNull String error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("updateIssue", error);
                snack(error);

            }

            @Override
            public void onSuccess(@NotNull String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("updateIssue", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.optBoolean("errror")) {
                        snack("Updated Successfully");
                        alertDialog("Do you wish to close this issue now ?", issueModel);
                        //popOutFragments();
                        // ((ActivityManageIssues) Objects.requireNonNull(getActivity())).popOut();


                        //finish();
                    } else {
                        snack("Error updating issue");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    snack("Error updating");
                }

            }
        });
    }

    private void alertDialog(final String message, IssueModel issueModel) {
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        if (NetworkUtils.Companion.isConnectionFast(getActivity())) {
                            startIssue(2, issueModel.getWork_tickets());
                        } else {
                            snack("Check your internet connection");
                        }


                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();

                        break;
                }
            }
        };


        if (getActivity() != null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage(message).setPositiveButton("Okay", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener)
                    .show();
        }

    }

    private void startIssue(int i, String work_tickets) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Closing....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = APiConstants.Companion.getUpdateWorkticket();

        HashMap<String, String> params = new HashMap<>();
        params.put("code", "" + i);
        params.put("workticket", work_tickets);

        // Log.d("getData",url+"\n"+params.toString()+"\n"+prefManager.getToken());
        Request.Companion.postRequest(url, params, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.setMessage(error.getMessage());
                    progressDialog.dismiss();
                }
                Log.d("getDataaa", error.getErrorBody());
                snack(error.getMessage());
            }

            @Override
            public void onError(@NotNull String error) {

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.setMessage(error);
                    progressDialog.dismiss();
                }
                Log.d("getDataaa", error);
                snack(error);
            }

            @Override
            public void onSuccess(@NotNull String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.setMessage(response);
                    progressDialog.dismiss();
                }
                Log.d("getDataaa", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.optBoolean("error")) {
                        snack("Issue Closed Successfully");
                        Objects.requireNonNull(getActivity()).finish();
                    } else {
                        snack("Error closing issue");
                    }
                } catch (JSONException nm) {
                    nm.printStackTrace();
                    snack("Error closing issue");
                }
            }
        });

    }

    private void snack(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        //String date = (dayOfMonth + "/" + (++monthOfYear) + "/" + year);
        // txtDate.setText(date);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        date = format.format(calendar.getTime());

        if (isWhich == 0 || isWhich == 1) {
            timePicker();
        } else {
            edtNextServiceDate.setText(date);
        }

        // btnDate.setText(strDate);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        time = (hourOfDay + ":" + minute + ":" + second);

        if (isWhich == 0) {
            edtStart.setText(date + " " + time);
        } else if (isWhich == 1) {
            edtEnd.setText(date + " " + time);
        }


        //btnTime.setText(time);
    }

    private void timePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(FragmentEdit.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                true);
        tpd.setThemeDark(false);
        tpd.vibrate(true);
        tpd.dismissOnPause(true);
        tpd.setMinTime(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), Calendar.SECOND);
        // tpd.setMinTime();
        tpd.setTitle("Pick a time");
        tpd.enableSeconds(false);

        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "TimePicker");
    }

    private void datePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(FragmentEdit.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(false);
        dpd.vibrate(true);
        dpd.dismissOnPause(true);
        dpd.showYearPickerFirst(true);
        dpd.setMinDate(now);
        dpd.setTitle("Pick a date");
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);

        dpd.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "DatePicker");
    }
}
