package com.assettrack.assettrack.Views.Admin.Clients;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.assettrack.assettrack.Adapters.V1.AssetAdapter;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Data.Parsers.SIngleCustomerParser;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.CustomerModel;
import com.assettrack.assettrack.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FragmentView extends Fragment {
    AssetAdapter listAdapter;
    ArrayList<AssetModel> assetModels;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private LinearLayout linearLayoutEmpty;


    private ProgressDialog progressDialog;
    private PrefManager prefManager;

    private TextView name,email,phone,address;
    private View view;
   // private Button btnEdit;

    private CustomerModel customerModel;
    private Fragment fragment;
    private Button btnEdit;

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
        return inflater.inflate(R.layout.fragment_view_client,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ActivityManageClients) Objects.requireNonNull(getActivity())).setFab(R.drawable.ic_save_black_24dp,false);

        prefManager=new PrefManager(Objects.requireNonNull(getActivity()));
        Bundle args=getArguments();
        if(args!=null){
            try{
                customerModel=(CustomerModel)getArguments().getSerializable("data");
            }catch (Exception nm){
                nm.printStackTrace();
            }
        }


        this.view=view;
        name = view.findViewById(R.id.txt_customer_name);
        email = view.findViewById(R.id.txt_customer_email);
        phone = view.findViewById(R.id.txt_customer_phone_no);
        address = view.findViewById(R.id.txt_customer_location);
        btnEdit=view.findViewById(R.id.btn_edit);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto: " + email.getText().toString()));
                startActivity(Intent.createChooser(emailIntent, "Email Client"));
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone.getText().toString(), null)));
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popOutFragments();
                fragment=new FragmentEdit();
                Bundle args=new Bundle();
                args.putSerializable("data",customerModel);
                fragment.setArguments(args);
                setUpView();

            }
        });

        if(customerModel!=null){
            name.setText(customerModel.getName());
            email.setText(customerModel.getAddress());
            address.setText(customerModel.getPhysical_address());
            phone.setText(customerModel.getTelephone());

        }
        getData();
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
        String url = APiConstants.Companion.getAllAssetsByCustomer();


        Log.d("getData", url + "\n" + params.toString() + "\n" + prefManager.getToken());
        Request.Companion.getRequest(url+""+customerModel.getId(), prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.setMessage(error.getMessage());
                    progressDialog.dismiss();
                }
                Log.d("getData", error.getErrorBody());
                snack(error.getMessage());

            }

            @Override
            public void onError(@NotNull String error) {

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.setMessage(error);
                    progressDialog.dismiss();
                }
                Log.d("getData", error);
                snack(error);

            }

            @Override
            public void onSuccess(@NotNull String response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.setMessage(response);
                    progressDialog.dismiss();
                }
                Log.d("getDataca", response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    //if(!jsonObject.getBoolean("error")){
                    //JSONArray jsonArray = jsonObject.getJSONArray("data");

                    //assetModels = AssetParser.parse(jsonArray);
                    assetModels= SIngleCustomerParser.parse(jsonObject).getAssetModels();

                   // initUI(assetModels);


                    //}
                } catch (Exception nm) {

                    Log.d("getData", nm.toString());
                    snack("Error finding assets");
                }

                initUI( assetModels);
            }
        });


        return assetModels;
    }

    private void initUI(ArrayList<AssetModel> assetModels) {


        recyclerView = view.findViewById(R.id.recyclerView);


        if (assetModels != null && assetModels.size() > 0) {
            mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(mStaggeredLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());


            listAdapter = new AssetAdapter(getContext(), assetModels , new OnclickRecyclerListener() {
                @Override
                public void onClickListener(int position) {


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

                }
            });
            // listAdapter.notifyDataSetChanged();

            recyclerView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();



        } else {
            Log.d("Loohj", "assetmodels is null");

        }

    }
    private void snack(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

}
