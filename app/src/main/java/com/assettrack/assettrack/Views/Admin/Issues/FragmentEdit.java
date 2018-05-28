package com.assettrack.assettrack.Views.Admin.Issues;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.EngineerModel;
import com.assettrack.assettrack.Models.IssueModel;
import com.assettrack.assettrack.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class FragmentEdit extends Fragment {
    private ProgressDialog progressDialog;
    private PrefManager prefManager;
    private View view;
    private Button btnEdit;
    TextInputEditText edtStart,edtEnd,edtFix,edtSoln,edtEngRemarks,edtCustRemarks,edtSafety,
            edtTravelHours,edtLabourHours,edtNextServiceDate;
    private Button btnSave;

    private IssueModel issueModel=null;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_issue,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;

        prefManager=new PrefManager(Objects.requireNonNull(getActivity()));
        Bundle args=getArguments();
        if(args!=null){
            try{
                issueModel=(IssueModel) getArguments().getSerializable("data");
            }catch (Exception nm){
                nm.printStackTrace();
                issueModel=null;
            }
        }
        edtStart = view.findViewById(R.id.edt_start);
        edtEnd = view.findViewById(R.id.edt_end);
        edtFix = view.findViewById(R.id.edt_fix);
        edtSoln = view.findViewById(R.id.edt_soln);
        edtEngRemarks = view.findViewById(R.id.edt_engineer_remarks);
        edtCustRemarks = view.findViewById(R.id.edt_customer_remarks);
        edtSafety=view.findViewById(R.id.edt_safety);
        edtTravelHours=view.findViewById(R.id.edt_travel_hours);
        edtLabourHours=view.findViewById(R.id.edt_labour_hours);
        edtNextServiceDate=view.findViewById(R.id.edt_next_service_date);
        btnSave=view.findViewById(R.id.btn_save);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        if(issueModel!=null){
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
        if (edtStart.getText().toString().isEmpty()) {
            edtStart.setError("Required");
            edtStart.requestFocus();
            return;
        }

        if (edtEnd.getText().toString().isEmpty()) {
            edtEnd.setError("Required");
            edtEnd.requestFocus();
            return;
        }

        if (edtFix.getText().toString().isEmpty()) {
            edtFix.setError("Required");
            edtFix.requestFocus();
            return;
        }

        if (edtSoln.getText().toString().isEmpty()) {
            edtSoln.setError("Required");
            edtSoln.requestFocus();
            return;
        }
        if (edtEngRemarks.getText().toString().isEmpty()) {
            edtEngRemarks.setError("Required");
            edtEngRemarks.requestFocus();
            return;
        }
        if (edtCustRemarks.getText().toString().isEmpty()) {
            edtCustRemarks.setError("Required");
            edtCustRemarks.requestFocus();
            return;
        }
        if (edtSafety.getText().toString().isEmpty()) {
            edtSafety.setError("Required");
            edtSafety.requestFocus();
            return;
        }
        if (edtTravelHours.getText().toString().isEmpty()) {
            edtTravelHours.setError("Required");
            edtTravelHours.requestFocus();
            return;
        }
        if (edtLabourHours.getText().toString().isEmpty()) {
            edtLabourHours.setError("Required");
            edtLabourHours.requestFocus();
            return;
        }
        if (edtNextServiceDate.getText().toString().isEmpty()) {
            edtNextServiceDate.setError("Required");
            edtNextServiceDate.requestFocus();
            return;
        }


        IssueModel issueModelnew=issueModel;
        issueModelnew.setStartdate(edtStart.getText().toString());
        issueModelnew.setClosedate(edtEnd.getText().toString());
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
                        popOutFragments();
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
    private void snack(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
}
