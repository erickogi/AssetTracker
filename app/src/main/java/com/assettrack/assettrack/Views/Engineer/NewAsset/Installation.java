package com.assettrack.assettrack.Views.Engineer.NewAsset;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.androidnetworking.error.ANError;
import com.assettrack.assettrack.Adapters.InstallationStepsAdapter;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Constatnts.GLConstants;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.R;
import com.assettrack.assettrack.Utils.DateTimeUtils;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Installation extends AppCompatActivity implements StepperLayout.StepperListener {
    boolean newasset = false;
    private StepperLayout mStepperLayout;
    private InstallationStepsAdapter mStepperAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_installation);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);


        Intent intent = getIntent();
        if (intent != null) {
            newasset = intent.getIntExtra("state", 0) != 1;
        }

        mStepperLayout = findViewById(R.id.stepperLayout);
        mStepperAdapter = new InstallationStepsAdapter(getSupportFragmentManager(), this);
        mStepperLayout.setAdapter(mStepperAdapter);
        mStepperLayout.setListener(this);
        mStepperLayout.setOffscreenPageLimit(4);
    }

    @Override
    public void onCompleted(View completeButton) {
        if (newasset) {
            createAsset();
        } else {
            updateAsset();
        }

//        if (new DbContentValues().insert(GLConstants.assetModel, Installation.this)) {
//            finish();
//        } else {
//            snack("Error Inserting asset");
//        }

    }

    private void updateAsset() {
        progressDialog = new ProgressDialog(Installation.this);
        progressDialog.setMessage("Updatting....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        JSONObject jsonObject = null;

        AssetModel assetModel = GLConstants.Companion.getAssetModel();
        try {

            jsonObject = new JSONObject();
//            jsonObject.put("id", assetModel.getId());
//            "Name":"MacBook Pro",
//                    "Code":"62294",
//                    "State":2,
//                    "Warranty":"Yes",
//                    "WarrantyDuration":"4",
//                    "Model":"Kangemi",
//                    "Serial":"Kangemi",
//                    "Manufacturer":"Kangemi",
//                    "ManufacturerYear":"2018",
//                    "NextService":"",
//                    "Customer":4,
//                    "ContactPosition":"Kangemi",
//                    "Department":"Kangemi",
//                    "RoomStatus":"Kangemi",
//                    "RoomSpecs":"Kangemi",
//                    "RoomExplanation":"Kangemi",
//                    "Engineer":4,
//                    "Trainees":"Kangemi",
//                    "TraineesPosition":"Kangemi",
//                    "EngineerRemarks":"Kangemi",
//                    "InstalationDate":"2018-05-18",
//                    "RecieversDate":"2018-05-18",
//                    "RecieversName":"Eddy",
//                    "RecieversDesignation":"Security",
//                    "RecieversComment":"Its ok"
            jsonObject.put("id", "" + assetModel.getId());
            jsonObject.put("Name", assetModel.getAsset_name());
            jsonObject.put("Code", assetModel.getAsset_code());
            jsonObject.put("State", "" + assetModel.getState());
            jsonObject.put("Warranty", assetModel.getWarranty());
            jsonObject.put("WarrantyDuration", assetModel.getWarranty_duration());
            jsonObject.put("Model", assetModel.getModel());
            jsonObject.put("Serial", assetModel.getSerial());
            jsonObject.put("Manufacturer", assetModel.getManufacturer());
            jsonObject.put("ManufacturerYear", assetModel.getYr_of_manufacture());
            jsonObject.put("NextService", DateTimeUtils.Companion.getToday());
            jsonObject.put("Customer", assetModel.getCustomers_id());
            jsonObject.put("ContactPosition", assetModel.getContact_person_position());
            jsonObject.put("Department", assetModel.getDepartment());
            jsonObject.put("RoomStatus", assetModel.getRoomsizestatus());
            jsonObject.put("RoomSpecs", assetModel.getRoom_meets_specification());
            jsonObject.put("RoomExplanation", assetModel.getRoom_explanation());
            jsonObject.put("Engineer", assetModel.getEngineer_id());
            jsonObject.put("Trainees", assetModel.getTrainees());
            jsonObject.put("TraineesPosition", assetModel.getTrainee_position());
            jsonObject.put("EngineerRemarks", assetModel.getEngineer_remarks());
            jsonObject.put("InstalationDate", DateTimeUtils.Companion.getNow());
            jsonObject.put("RecieversDate", DateTimeUtils.Companion.getNow());
            jsonObject.put("RecieversName", assetModel.getRecievers_name());
            jsonObject.put("Security", assetModel.getWarranty());

            jsonObject.put("RecieversDesignation", assetModel.getReceiver_designation());
            jsonObject.put("RecieversComment", assetModel.getReceiver_comments());


        } catch (Exception nm) {

            Log.d("updateAssset", nm.toString());
        }
        Log.d("nupdateAssset", jsonObject.toString());
        HashMap<String, String> params = new HashMap<>();
        params.put("id", "" + assetModel.getId());
        params.put("Name", assetModel.getAsset_name());
        params.put("Code", assetModel.getAsset_code());
        params.put("State", "" + assetModel.getState());
        params.put("Warranty", assetModel.getWarranty());
        params.put("WarrantyDuration", assetModel.getWarranty_duration());
        params.put("Model", assetModel.getModel());
        params.put("Serial", assetModel.getSerial());
        params.put("Manufacturer", assetModel.getManufacturer());
        params.put("ManufacturerYear", assetModel.getYr_of_manufacture());
        params.put("NextService", assetModel.getNextservice());
        params.put("Customer", assetModel.getCustomers_id());
        params.put("ContactPosition", assetModel.getContact_person_position());
        params.put("Department", assetModel.getDepartment());
        params.put("RoomStatus", assetModel.getRoomsizestatus());
        params.put("RoomSpecs", assetModel.getRoom_meets_specification());
        params.put("RoomExplanation", assetModel.getRoom_explanation());
        params.put("Engineer", assetModel.getEngineer_id());
        params.put("Trainees", assetModel.getTrainees());
        params.put("TraineesPosition", assetModel.getTrainee_position());
        params.put("EngineerRemarks", assetModel.getEngineer_remarks());
        params.put("InstalationDate", assetModel.getInstallation_date());
        params.put("RecieversDate", assetModel.getRecieversDate());
        params.put("RecieversName", assetModel.getRecievers_name());
        params.put("Security", assetModel.getWarranty());

        params.put("RecieversDesignation", assetModel.getReceiver_designation());
        params.put("RecieversComment", assetModel.getReceiver_comments());

        PrefManager prefManager = new PrefManager(Installation.this);
        Request.Companion.putRequest(APiConstants.Companion.getUpdateAsset() + "" + assetModel.getId() + "/update", jsonObject, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                Log.d("updateAsset", error.toString());
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onError(@NotNull String error) {
                Log.d("updateAsset", error);
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(@NotNull String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("updateAsset", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.optBoolean("errror")) {
                        snack("Updated Successfully");
                        finish();
                    } else {
                        snack("Error updating asset");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    private void createAsset() {
        progressDialog = new ProgressDialog(Installation.this);
        progressDialog.setMessage("Creating....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        AssetModel assetModel = GLConstants.Companion.getAssetModel();

        HashMap<String, String> params = new HashMap<>();
        params.put("id", "" + assetModel.getId());
        params.put("Name", assetModel.getAsset_name());
        params.put("Code", assetModel.getAsset_code());
        params.put("State", "" + assetModel.getState());
        params.put("Warranty", assetModel.getWarranty());
        params.put("WarrantyDuration", assetModel.getWarranty_duration());
        params.put("Model", assetModel.getModel());
        params.put("Serial", assetModel.getSerial());
        params.put("Manufacturer", assetModel.getManufacturer());
        params.put("ManufacturerYear", assetModel.getYr_of_manufacture());
        params.put("NextService", assetModel.getNextservice());
        params.put("Customer", assetModel.getCustomers_id());
        params.put("ContactPosition", assetModel.getContact_person_position());
        params.put("Department", assetModel.getDepartment());
        params.put("RoomStatus", assetModel.getRoomsizestatus());
        params.put("RoomSpecs", assetModel.getRoom_meets_specification());
        params.put("RoomExplanation", assetModel.getRoom_explanation());
        params.put("Engineer", assetModel.getEngineer_id());
        params.put("Trainees", assetModel.getTrainees());
        params.put("TraineesPosition", assetModel.getTrainee_position());
        params.put("EngineerRemarks", assetModel.getEngineer_remarks());
        params.put("InstalationDate", assetModel.getInstallation_date());
        params.put("RecieversDate", assetModel.getRecieversDate());
        params.put("RecieversName", assetModel.getRecievers_name());
        params.put("RecieversDesignation", assetModel.getReceiver_designation());
        params.put("Security", assetModel.getWarranty());
        params.put("RecieversComment", assetModel.getReceiver_comments());

        PrefManager prefManager = new PrefManager(Installation.this);
        Request.Companion.postRequest(APiConstants.Companion.getCreateAsset(), params, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                Log.d("updateAsset", error.toString());
                snack("" + error.getMessage());
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onError(@NotNull String error) {
                Log.d("createAsset", error);
                snack(error);
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onSuccess(@NotNull String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("createAsset", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.optBoolean("errror")) {
                        snack("Created Successfully");
                        finish();
                    } else {
                        snack("Error updating asset");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }


    private void snack(String msg) {
        Snackbar.make(mStepperLayout, msg, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {

    }
}
