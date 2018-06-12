package com.assettrack.assettrack.Views.Engineer.NewAsset.InstallationSteps;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.androidnetworking.error.ANError;
import com.assettrack.assettrack.Adapters.V1.CategoryAdapter;
import com.assettrack.assettrack.Adapters.V1.CustomerAdapter;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Constatnts.GLConstants;
import com.assettrack.assettrack.Data.Parsers.CategoryParser;
import com.assettrack.assettrack.Data.Parsers.CustomerParser;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.CategoriesModel;
import com.assettrack.assettrack.Models.CustomerModel;
import com.assettrack.assettrack.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class InstallationStepOne extends Fragment implements BlockingStep,DialogSearchCustomer.DialogSearchListener  {

    private View view;
    private Button btnSelectCustomer;
    MaterialDialog m;
    CustomerAdapter listAdapter;


    private TextInputLayout tilWarrantyDuration;
    private RadioGroup rgWarranty;
    private RadioButton rbYes,rbNo;

    private String customerID=null;
    MaterialDialog n;
    private PrefManager prefManager;
    private AVLoadingIndicatorView avi;
    private TextInputEditText edtAssetName, edtWarranty, edtWarrantyDuration, edtModel,
            edtSerialNo, edtManufacturer, edtYrOfManufacture, edtServiceContract, edtCustomerName, edtCategory;
    private String categoryID = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_installation_one,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        prefManager = new PrefManager(Objects.requireNonNull(getActivity()));
        avi = view.findViewById(R.id.avi);
        initUI(view);

    }

    void  initUI(View view){
        btnSelectCustomer=view.findViewById(R.id.btn_customer);
        edtAssetName=view.findViewById(R.id.edt_asset_name);
        tilWarrantyDuration=view.findViewById(R.id.til_Warranty_duration);
        //edtWarranty=view.findViewById(R.id.edt_warranty);
        edtWarrantyDuration=view.findViewById(R.id.edt_warranty_duration);
        edtModel=view.findViewById(R.id.edt_model);
        edtSerialNo=view.findViewById(R.id.edt_serial);
        edtManufacturer=view.findViewById(R.id.edt_manufacturer);
        edtYrOfManufacture=view.findViewById(R.id.edt_manufacture_yr);
        edtServiceContract=view.findViewById(R.id.edt_contact);
        edtCategory = view.findViewById(R.id.edt_asset_category);

        rgWarranty=view.findViewById(R.id.rgrp_warranty);
        rbNo=view.findViewById(R.id.rbtn_warranty_no);
        rbYes=view.findViewById(R.id.rbtn_warranty_yes);

        edtCustomerName = view.findViewById(R.id.edt_customer_name);


        avi.hide();


        UiActions();
        initData();
    }

    private void initData() {

        if (GLConstants.Companion.getAssetModel() != null) {
            AssetModel assetModel = GLConstants.Companion.getAssetModel();
            if (GLConstants.Companion.getAssetModel().getCustomers_id() != null) {
                customerID = assetModel.getCustomers_id();
                edtCustomerName.setText(assetModel.getCustomers_id());

            }
            if (GLConstants.Companion.getAssetModel().getCategory_id() != 0 && GLConstants.Companion.getAssetModel().getCategory() != null) {
                edtCategory.setText(assetModel.getCategory());
                categoryID = String.valueOf(assetModel.getCategory_id());

            }
            if (assetModel.getAsset_name() != null) {
                edtAssetName.setText(assetModel.getAsset_name());
            }
            if (assetModel.getWarranty() != null && assetModel.getWarranty().equals("Yes")) {
                rbYes.setChecked(true);
                edtWarrantyDuration.setVisibility(View.VISIBLE);
                edtWarrantyDuration.setText(assetModel.getWarranty_duration());
                rbNo.setChecked(false);
            } else if (assetModel.getWarranty() != null && assetModel.getWarranty().equals("No")) {
                rbNo.setChecked(true);
                edtWarrantyDuration.setVisibility(View.GONE);
                rbYes.setChecked(false);
            }

            if (assetModel.getModel() != null) {
                edtModel.setText(assetModel.getModel());
            }
            if (assetModel.getSerial() != null) {
                edtSerialNo.setText(assetModel.getSerial());
            }
            if (assetModel.getYr_of_manufacture() != null) {
                edtYrOfManufacture.setText(assetModel.getYr_of_manufacture());
            }
            if (assetModel.getManufacturer() != null) {
                edtManufacturer.setText(assetModel.getManufacturer());
            }
            if (assetModel.getWarranty() != null) {
                edtServiceContract.setText(assetModel.getWarranty());
            }
        } else {

            //snack("null");
        }
    }
    //ArrayList<>

    ArrayList<CustomerModel> getCustomers() {
        ArrayList<CustomerModel> customerModels = new ArrayList<>();
//        Request.Companion.getRequest(url, prefManager.getToken(), new RequestListener() {
//            @Override
//            public void onError(@NotNull ANError error) {
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    // progressDialog.setMessage(error.getMessage());
//                    progressDialog.dismiss();
//                }
//                Log.d("getData", error.getErrorBody());
//            }
//
//            @Override
//            public void onError(@NotNull String error) {
//
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    // progressDialog.setMessage(error);
//                    progressDialog.dismiss();
//                }
//                Log.d("getData", error);
//
//            }
//
//            @Override
//            public void onSuccess(@NotNull String response) {
//
//                Log.d("getData", response);
//
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    //if(!jsonObject.getBoolean("error")){
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//
//                    customerModels = CustomerParser.parse(jsonArray);
//                    //}
//                } catch (Exception nm) {
//
//                    Log.d("getData", nm.toString());
//                }
//
//                initUI(customerModels);
//            }
//        });

        String url = APiConstants.Companion.getAllCustomers();


        //APiConstants.Companion.getCustomerlist()
        avi.show();
        Request.Companion.getRequest(url, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                avi.hide();
                Log.d("customerresponse", error.toString());

            }

            @Override
            public void onError(@NotNull String error) {

                avi.hide();
                Log.d("customerresponse", error);

            }

            @Override
            public void onSuccess(@NotNull String response) {

                avi.hide();
                Log.d("customerresponse", response);
                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    if (!jsonObject.optBoolean("errror")) {
//                        JSONArray data = jsonObject.optJSONArray("data");
//                       dialogSelectCustomer(CustomerListParser.parse(data));
//                    }

                    JSONObject jsonObject = new JSONObject(response);
                    //if(!jsonObject.getBoolean("error")){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    createDialogCustomer(CustomerParser.parse(jsonArray));
                    // customerModels = CustomerParser.parse(jsonArray);

                } catch (Exception nm) {

                }
            }
        });

        return customerModels;
    }

    ArrayList<CategoriesModel> getCategories() {
        String url = APiConstants.Companion.getCategorieslist();

        ArrayList<CategoriesModel> categoriesModels = new ArrayList<>();

        avi.show();
        // Log.d("getData",url+"\n"+params.toString()+"\n"+prefManager.getToken());
        Request.Companion.getRequest(url, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {

                avi.hide();
                Log.d("getData", error.getErrorBody());
            }

            @Override
            public void onError(@NotNull String error) {

                avi.hide();
                Log.d("getData", error);

            }

            @Override
            public void onSuccess(@NotNull String response) {

                Log.d("getData", response);

                avi.hide();
                try {

                    JSONObject jsonObject = new JSONObject(response).getJSONObject("data");
                    //if(!jsonObject.getBoolean("error")){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    createDialog(CategoryParser.parse(jsonArray));
                    //
                    // categoriesModels = CategoryParser.parse(jsonArray);
                    //}
                } catch (Exception nm) {

                    Log.d("getData", nm.toString());
                }

                //initUI(categoriesModels);
            }
        });

        return categoriesModels;
    }

    private void createDialog(ArrayList<CategoriesModel> parse) {
        m = new MaterialDialog.Builder(Objects.requireNonNull(getActivity()))
                .title("Categories")
                .adapter(new CategoryAdapter(getActivity(), parse, new OnclickRecyclerListener() {
                    @Override
                    public void onClickListener(int position) {
                        categoryID = String.valueOf(parse.get(position).getId());
                        edtCategory.setText(parse.get(position).getName());
                        m.dismiss();
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
                    public void onClickListener(int adapterPosition, @NotNull View view) {

                    }
                }), null)
                .theme(Theme.LIGHT)
                .titleGravity(GravityEnum.CENTER)


                .show();
    }

    private void createDialogCustomer(ArrayList<CustomerModel> parse) {
        m = new MaterialDialog.Builder(Objects.requireNonNull(getActivity()))
                .title("Customers")
                .adapter(new CustomerAdapter(getActivity(), parse, new OnclickRecyclerListener() {
                    @Override
                    public void onClickListener(int position) {
                        customerID = String.valueOf(parse.get(position).getId());
                        edtCustomerName.setText(parse.get(position).getName());
                        btnSelectCustomer.setText(parse.get(position).getName());

                        m.dismiss();
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
                    public void onClickListener(int adapterPosition, @NotNull View view) {

                    }
                }), null)
                .theme(Theme.LIGHT)
                .titleGravity(GravityEnum.CENTER)


                .show();
    }


    private void snack(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
    void UiActions(){
        btnSelectCustomer.setOnClickListener(view ->

                dialogSelectCustomer(getCustomers()));


        edtCustomerName.setOnClickListener(view1 -> getCustomers());
        edtCategory.setOnClickListener(v -> getCategories());
        edtYrOfManufacture.setOnClickListener(this::onClick);


        rgWarranty.setOnCheckedChangeListener((radioGroup, i) -> {
            if(i==R.id.rbtn_warranty_yes){
                tilWarrantyDuration.setVisibility(View.VISIBLE);
            }else if(i==R.id.rbtn_warranty_no){
                tilWarrantyDuration.setVisibility(View.GONE);
            }
        });
    }

    private void dialogSelectCustomer(ArrayList<CustomerModel> customerModels) {
        FragmentManager fm = getFragmentManager();
        DialogSearchCustomer dialogSearch = DialogSearchCustomer.newInstance("Clients", 1, customerModels);
        // dialogSearch.show(fm,"dialog");

        dialogSearch.setTargetFragment(InstallationStepOne.this, 300);
        dialogSearch.show(fm, "fragment_search");


    }
    public void show()
    {

        final Dialog d = new Dialog(Objects.requireNonNull(getContext()));
        d.setTitle("Year Of Manufacture");

        d.setContentView(R.layout.dialog);
        Button b1 = d.findViewById(R.id.button1);
        Button b2 = d.findViewById(R.id.button2);
        final NumberPicker np = d.findViewById(R.id.numberPicker1);


        np.setMaxValue( Calendar.getInstance().get(Calendar.YEAR));
        np.setMinValue(1900);
        np.setValue(Calendar.getInstance().get(Calendar.YEAR));
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener((numberPicker, i, i1) -> {

        });
        b1.setOnClickListener(v -> {
            edtYrOfManufacture.setText(String.valueOf(np.getValue()));
            //tv.setText(String.valueOf(np.getValue()));
            d.dismiss();
        });
        b2.setOnClickListener(v -> d.dismiss());
        d.show();


    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

        if (GLConstants.Companion.getAssetModel() == null) {
            GLConstants.Companion.setAssetModel(new AssetModel());

            GLConstants.Companion.getAssetModel().setCustomers_id(customerID);
            GLConstants.Companion.getAssetModel().setAsset_name(edtAssetName.getText().toString());
            //GLConstants.Companion.getAssetModel().setCustomer_name(edtCustomerName.getText().toString());
            GLConstants.Companion.getAssetModel().setWarranty(getRadioChecked(rgWarranty));
            GLConstants.Companion.getAssetModel().setModel(edtModel.getText().toString());
            GLConstants.Companion.getAssetModel().setSerial(edtSerialNo.getText().toString());
            GLConstants.Companion.getAssetModel().setManufacturer(edtManufacturer.getText().toString());
            GLConstants.Companion.getAssetModel().setYr_of_manufacture(edtYrOfManufacture.getText().toString());
            GLConstants.Companion.getAssetModel().setCategory(edtCategory.getText().toString());
            GLConstants.Companion.getAssetModel().setCategory_id(Integer.valueOf(categoryID));
            // GLConstants.Companion.getAssetModel().setContract(edtServiceContract.getText().toString());


        } else {
            GLConstants.Companion.getAssetModel().setCustomers_id(customerID);
            //GLConstants.Companion.getAssetModel().setCustomer_name(edtCustomerName.getText().toString());
            GLConstants.Companion.getAssetModel().setWarranty(getRadioChecked(rgWarranty));
            GLConstants.Companion.getAssetModel().setModel(edtModel.getText().toString());
            GLConstants.Companion.getAssetModel().setSerial(edtSerialNo.getText().toString());
            GLConstants.Companion.getAssetModel().setManufacturer(edtManufacturer.getText().toString());
            GLConstants.Companion.getAssetModel().setYr_of_manufacture(edtYrOfManufacture.getText().toString());
            GLConstants.Companion.getAssetModel().setCategory(edtCategory.getText().toString());
            GLConstants.Companion.getAssetModel().setCategory_id(Integer.valueOf(categoryID));

            //GLConstants.Companion.getAssetModel().setContract(edtServiceContract.getText().toString());

        }
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        if(verify()) {
            return null;
        }else {
            return new VerificationError("Fill all fields");
        }
    }

    @Override
    public void onSelected() {

        initUI(view);
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onSelected(CustomerModel model) {
        customerID = String.valueOf(model.getId());
        btnSelectCustomer.setText(model.getName());
        edtCustomerName.setText(model.getName());
    }

    private void onClick(View view) {
        show();
    }
    private boolean verify(){
        return isTextInputEditTextFilled(edtAssetName)
                && isRadioGroupChecked(rgWarranty)
                && isTextInputEditTextFilled(edtModel)
                && isTextInputEditTextFilled(edtCategory)
                && isTextInputEditTextFilled(edtCustomerName)
                && isTextInputEditTextFilled(edtSerialNo)
                && isTextInputEditTextFilled(edtManufacturer)
                && isTextInputEditTextFilled(edtYrOfManufacture)
                && isTextInputEditTextFilled(edtServiceContract)
                && isStringValid(customerID)
                && isStringValid(categoryID);
    }
    private boolean isStringValid(String s){
        if(s==null){
            Snackbar.make(view, "Select Customer", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        return true;
    }

    private boolean isTextInputEditTextFilled(TextInputEditText t){
        if(TextUtils.isEmpty(t.getText())){
            t.requestFocus();
            t.setError("Required");
            return false;
        }
        return true;
    }

    private String getRadioChecked(RadioGroup r) {
        if (r.getCheckedRadioButtonId() == R.id.rbtn_warranty_yes) {
            return "Yes";
        } else {
            return "No";
        }
    }
    private boolean isRadioGroupChecked(RadioGroup r){
        if(r.getCheckedRadioButtonId()==-1){

            Snackbar.make(r, "Select an option for warranty", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else {
            return true;
        }
    }
}
