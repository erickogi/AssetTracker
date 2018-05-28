package com.assettrack.assettrack.Views.Admin.Clients;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.assettrack.assettrack.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class FragmentEdit extends Fragment {

    private ProgressDialog progressDialog;
    private PrefManager prefManager;

    private EditText name,email,phone,address;
    private View view;
    private Button btnSave;

    private CustomerModel customerModel=null;
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
        return inflater.inflate(R.layout.fragment_edit_client,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefManager=new PrefManager(Objects.requireNonNull(getActivity()));
        Bundle args=getArguments();
        if(args!=null){
            try{
                customerModel=(CustomerModel)getArguments().getSerializable("data");
            }catch (Exception nm){
                nm.printStackTrace();
                customerModel=null;
            }
        }


        this.view=view;
        name = view.findViewById(R.id.txt_customer_name);
        email = view.findViewById(R.id.txt_customer_email);
        phone = view.findViewById(R.id.txt_customer_phone_no);
        address = view.findViewById(R.id.txt_customer_location);
        btnSave=view.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDetails();
            }
        });

        if(customerModel!=null){
            name.setText(customerModel.getName());
            email.setText(customerModel.getAddress());
            address.setText(customerModel.getPhysical_address());
            phone.setText(customerModel.getTelephone());

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

        if (address.getText().toString().isEmpty()) {
            address.setError("Required");
            address.requestFocus();
            return;
        }
        int id=0;

        if(customerModel!=null){
            id=customerModel.getId();
        }

        CustomerModel customerModelnew = new CustomerModel();
        customerModelnew.setName(name.getText().toString());
        customerModelnew.setId(id);
        customerModelnew.setPhysical_address(address.getText().toString());
        customerModelnew.setAddress(email.getText().toString());
        customerModelnew.setAddress(email.getText().toString());
        customerModelnew.setTelephone(phone.getText().toString());

        if(customerModel!=null){
            update(customerModelnew);
        }else {
            save(customerModelnew);
        }

    }

    private void save(CustomerModel customerModel) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Saving....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        HashMap<String,String> params = new HashMap<>();
        try {
            params.put("id", ""+customerModel.getId());
            params.put("Name", customerModel.getName());
            params.put("Address", customerModel.getAddress());
            params.put("Telephone", customerModel.getTelephone());
            params.put("PhysicalAddress", customerModel.getPhysical_address());


        } catch (Exception nm) {
            nm.printStackTrace();
        }



        Request.Companion.postRequest(APiConstants.Companion.getCreateCustomer() , params, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("saveCustomer", error.toString());
                snack(error.getMessage());

            }

            @Override
            public void onError(@NotNull String error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("saveCustomer", error);
                snack(error);

            }

            @Override
            public void onSuccess(@NotNull String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("saveCustomer", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.optBoolean("errror")) {
                        snack("Saved Successfully");
                        popOutFragments();
                        //finish();
                    } else {
                        snack("Error saving asset");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    snack("Error saving asset");
                }

            }
        });

    }

    private void update(CustomerModel customerModel) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", customerModel.getId());
            jsonObject.put("Name", customerModel.getName());
            jsonObject.put("Address", customerModel.getAddress());
            jsonObject.put("Telephone", customerModel.getTelephone());
            jsonObject.put("PhysicalAddress", customerModel.getPhysical_address());


        } catch (Exception nm) {
            nm.printStackTrace();
        }

        Request.Companion.putRequest(APiConstants.Companion.getUpdateCustomer() + "" + customerModel.getId() + "/update", jsonObject, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.d("updateCustomer", error.toString());

            }

            @Override
            public void onError(@NotNull String error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
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
                    if (!jsonObject.optBoolean("errror")) {
                        snack("Updated Successfully");
                        //finish();
                        popOutFragments();
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
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

}
