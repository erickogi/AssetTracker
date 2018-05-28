package com.assettrack.assettrack.Views.Admin.Issues;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;

import com.androidnetworking.error.ANError;
import com.assettrack.assettrack.Adapters.AssignmentSearchAdapter;
import com.assettrack.assettrack.Constatnts.APiConstants;
import com.assettrack.assettrack.Constatnts.GLConstants;
import com.assettrack.assettrack.Data.Parsers.AllEngineerParser;
import com.assettrack.assettrack.Data.Parsers.AssetParser;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Data.Request;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Interfaces.UtilListeners.RequestListener;
import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.EngineerModel;
import com.assettrack.assettrack.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class FragmentNewIssues extends Fragment {
    private View view;
    EditText assetId,engId;
    private Button btnSubmit;
    private int assetid,engid;
    private RecyclerView recyclerView;
    private PrefManager prefManager;
    private AVLoadingIndicatorView avi;
    private ProgressDialog progressDialog;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private LinearLayout linearLayoutEmpty;

    private AssetModel assetModel=new AssetModel();
    private EngineerModel engineerModel=new EngineerModel();


    private ArrayList<EngineerModel> engineerModels;
    private ArrayList<AssetModel> assetModels;
    private Fragment fragment;
    AssignmentSearchAdapter searchAdapter;
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
        return inflater.inflate(R.layout.fragment_new_issue,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        prefManager=new PrefManager(Objects.requireNonNull(getActivity()));

        avi = view.findViewById(R.id.avi);
        avi.hide();


        assetId = view.findViewById(R.id.edt_asset_id);
        engId = view.findViewById(R.id.edt_eng_id);
        recyclerView = view.findViewById(R.id.recyclerView);
        btnSubmit=view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitIssue();
            }
        });



        avi.show();
        getEngineers();
        getAssets();
        
    }

    private void submitIssue() {
        if(assetModel==null||engineerModel==null){
            snack("No data loaded");
        }else {
//            HashMap<String,String> params=new HashMap<>();
//            params.put("Asset",String.valueOf(assetModel.getId()));
//            params.put("Engineer",""+engineerModel.getEmployeeid());
//            params.put("AssetCode",assetModel.getAsset_code());
//            params.put("CustomerName",assetModel.getCustomerModel().getName());
//            params.put("CustomerId",""+assetModel.getCustomers_id());
//            params.put("StartDate","2018-01-01 12:12:12"
//                                                    params.put("CloseDate:2018-01-02 12:12:12
//                                                            params.put("NextdueService:2018-01-02
//                                                                    params.put("TravelHours:
//                                                                            params.put("LabourHours:
//                                                                                    params.put("FailureDesc:
//                                                                                            params.put("FailurSolution:
//                                                                                                    params.put("EngineerComment:
//                                                                                                            params.put("CustomerComment:
//                                                                                                                    params.put("Safety:
//                                                                                                                            params.put("Status:
//                                                                                                                                    params.put("PartsState:
//                                                                                                                                            params.put("workticketparts:[{" partnumber":1," description":" A4"," quantity":2," state":1}]
        }
    }

    private void getAssets() {
        getData();
    }

    private void getEngineers() {
        initData();
        
        
    }

    private ArrayList<EngineerModel> initData() {
        engineerModels = new ArrayList<>();

       String url = APiConstants.Companion.getAllEngineers();
       Request.Companion.getRequest(url, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {
                snack(error.getMessage());
                Log.d("getData", error.getErrorBody());
            }

            @Override
            public void onError(@NotNull String error) {

               snack(error);
                Log.d("getData", error);

            }

            @Override
            public void onSuccess(@NotNull String response) {

                Log.d("getData", response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    //if(!jsonObject.getBoolean("error")){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    engineerModels = AllEngineerParser.parse(jsonArray);
                    //}
                } catch (Exception nm) {
                    snack("Error getting Engineers");

                    Log.d("getData", nm.toString());
                }


            }
        });


        return engineerModels;
    }

    private ArrayList<AssetModel> getData() {
        assetModels = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();


        String url = APiConstants.Companion.getAllAssets();


        Log.d("getData", url + "\n" + params.toString() + "\n" + prefManager.getToken());
        Request.Companion.getRequest(url, prefManager.getToken(), new RequestListener() {
            @Override
            public void onError(@NotNull ANError error) {

                Log.d("getData", error.getErrorBody());
                snack(error.getMessage());
            }

            @Override
            public void onError(@NotNull String error) {

               snack(error);
                Log.d("getData", error);

            }

            @Override
            public void onSuccess(@NotNull String response) {

                Log.d("getData", response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    //if(!jsonObject.getBoolean("error")){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    assetModels = AssetParser.parse(jsonArray);


                    if(engineerModels!=null&&assetModels!=null){
                        avi.hide();
                        initUI();

                    }

                    //}
                } catch (Exception nm) {

                    snack("Error getting assets");
                    Log.d("getData", nm.toString());
                }

            }
        });


        return assetModels;
    }

    private void initUI() {



        assetId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchAdapter=new AssignmentSearchAdapter(getActivity(), assetModels, new OnclickRecyclerListener() {
                    @Override
                    public void onClickListener(int position) {
                        assetId.setText(assetModels.get(position).getAsset_name());
                        assetModel=assetModels.get(position);

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
                });
                searchAdapter.notifyDataSetChanged();
                mStaggeredLayoutManager =new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                //mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mStaggeredLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                searchAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(searchAdapter);



            }
        });
        engId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAdapter=new AssignmentSearchAdapter(1,engineerModels, new OnclickRecyclerListener() {
                    @Override
                    public void onClickListener(int position) {

                        engineerModel=engineerModels.get(position);
                        engId.setText(engineerModel.getFull_name());

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
                });

                searchAdapter.notifyDataSetChanged();
                mStaggeredLayoutManager =new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                //mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mStaggeredLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                searchAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(searchAdapter);

            }
        });





    }

    private void snack(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }


}