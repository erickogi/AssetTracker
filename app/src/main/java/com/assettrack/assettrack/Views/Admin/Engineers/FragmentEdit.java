package com.assettrack.assettrack.Views.Admin.Engineers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidnetworking.error.ANError;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.CustomerModel;
import com.assettrack.assettrack.Models.EngineerModel;
import com.assettrack.assettrack.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class FragmentEdit extends Fragment {
    private ProgressDialog progressDialog;
    private PrefManager prefManager;
    private View view;
    private EditText name, sname, email, phone, speciality, idd,password;
    private Button btnSave;

    private EngineerModel engineerModel=null;
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
        return inflater.inflate(R.layout.fragment_edit_engineer,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;

        prefManager=new PrefManager(Objects.requireNonNull(getActivity()));
        Bundle args=getArguments();
        if(args!=null){
            try{
                engineerModel=(EngineerModel) getArguments().getSerializable("data");
            }catch (Exception nm){
                nm.printStackTrace();
                engineerModel=null;
            }
        }

        name = view.findViewById(R.id.txt_eng_first_name);
        sname = view.findViewById(R.id.txt_eng_last_name);
        email = view.findViewById(R.id.txt_eng_email);
        phone = view.findViewById(R.id.txt_eng_phone_no);
        speciality = view.findViewById(R.id.txt_eng_speciality);
        idd = view.findViewById(R.id.txt_eng_id);
        password=view.findViewById(R.id.txt_eng_password);

        btnSave=view.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails();
            }
        });

        if(engineerModel!=null){
            name.setText(engineerModel.getFirstname());
            email.setText(engineerModel.getEmail());
            sname.setText(engineerModel.getLastname());
            phone.setText(engineerModel.getPhoneNumber());
            idd.setText(engineerModel.getId());
            speciality.setText(engineerModel.getDesignation());
        }


    }

    private void saveDetails() {
        if (name.getText().toString().isEmpty()) {
            name.setError("Required");
            name.requestFocus();
            return;
        }

        if (email.getText().toString().isEmpty()) {
            email.setError("Required");
            email.requestFocus();
            return;
        }

        if (phone.getText().toString().isEmpty()) {
            phone.setError("Required");
            phone.requestFocus();
            return;
        }

        if (sname.getText().toString().isEmpty()) {
            sname.setError("Required");
            sname.requestFocus();
            return;
        }
        if (speciality.getText().toString().isEmpty()) {
            speciality.setError("Required");
            speciality.requestFocus();
            return;
        }
        if (idd.getText().toString().isEmpty()) {
            idd.setError("Required");
            idd.requestFocus();
            return;
        }
        if (password.getText().toString().isEmpty()) {
            password.setError("Required");
            password.requestFocus();
            return;
        }


        int id=0;

        if(engineerModel!=null){
            id=engineerModel.getId();
        }

        EngineerModel engineerModelnew = new EngineerModel();

        engineerModelnew.setId(id);
        engineerModelnew.setFirstname(name.getText().toString());
        engineerModelnew.setEmployeeid(idd.getText().toString());
        engineerModelnew.setDesignation(speciality.getText().toString());
        engineerModelnew.setEmail(email.getText().toString());
        engineerModelnew.setLastname(sname.getText().toString());
        engineerModelnew.setPhoneNumber(phone.getText().toString());
        engineerModelnew.setPassword(password.getText().toString());

        if(engineerModel!=null){
            updateEngineer(engineerModelnew);
        }else {
            saveEngineer(engineerModelnew);
        }

    }

    private void saveEngineer(EngineerModel engineerModel) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Saving....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        HashMap<String,String> params = new HashMap<>();
        //HashMap jsonObject = new JSONObject();
        try {
            params.put("id", ""+engineerModel.getId());
            params.put("EmployeeId", engineerModel.getEmployeeid());
            params.put("firstName", engineerModel.getFirstname());
            params.put("lastName", engineerModel.getLastname());
            params.put("Email", engineerModel.getEmail());
            params.put("Speciality", engineerModel.getDesignation());
            params.put("Designation", engineerModel.getPhoneNumber());
            params.put("role", ""+engineerModel.getRole());
            params.put("password",""+ engineerModel.getPassword());


        } catch (Exception nm) {
            nm.printStackTrace();
        }

        Request.Companion.postRequest(APiConstants.Companion.getCreateEngineer() , params, prefManager.getToken(), new RequestListener() {
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
                Log.d("updateCustomer", error);
                snack(error);

            }

            @Override
            public void onSuccess(@NotNull String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("updateCustomer", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.optBoolean("errror")) {
                        snack("Updated Successfully");
                        popOutFragments();
                        //finish();
                    } else {
                        snack("Error updating asset");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    snack("Error updating");
                }

            }
        });
    }

    private void updateEngineer(EngineerModel engineerModel) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", engineerModel.getId());
            jsonObject.put("EmployeeId", engineerModel.getEmployeeid());
            jsonObject.put("firstName", engineerModel.getFirstname());
            jsonObject.put("lastName", engineerModel.getLastname());
            jsonObject.put("Email", engineerModel.getEmail());
            jsonObject.put("Speciality", engineerModel.getDesignation());
            jsonObject.put("Designation", engineerModel.getPhoneNumber());
            jsonObject.put("role", engineerModel.getRole());
            jsonObject.put("password", engineerModel.getPassword());

        } catch (Exception nm) {
            nm.printStackTrace();
        }

        Request.Companion.putRequest(APiConstants.Companion.getUpdateEngineer() + "" + engineerModel.getId() + "/update", jsonObject, prefManager.getToken(), new RequestListener() {
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
                Log.d("updateCustomer", error);
                snack(error);

            }

            @Override
            public void onSuccess(@NotNull String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("updateCustomer", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.optBoolean("errror")) {
                        snack("Updated Successfully");
                        popOutFragments();
                        //finish();
                    } else {
                        snack("Error updating asset");
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