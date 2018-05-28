package com.assettrack.assettrack.Views.Admin.Engineers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.assettrack.assettrack.Adapters.V1.AssetAdapter;
import com.assettrack.assettrack.Data.PrefManager;
import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.CustomerModel;
import com.assettrack.assettrack.Models.EngineerModel;
import com.assettrack.assettrack.R;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentView extends Fragment {
  
 private View view;
    // private Button btnEdit;

    private EngineerModel engineerModel;
    private Fragment fragment;
    private Button btnEdit;
    TextView name, sname, email, phone, speciality, idd;


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
        return inflater.inflate(R.layout.fragment_view_engineer,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        ((ActivityManageEngineers) Objects.requireNonNull(getActivity())).setFab(R.drawable.ic_save_black_24dp,false);

        Bundle args=getArguments();
        if(args!=null){
            try{
                engineerModel=(EngineerModel) getArguments().getSerializable("data");
            }catch (Exception nm){
                nm.printStackTrace();
            }
        }

        name = view.findViewById(R.id.txt_eng_first_name);
        sname = view.findViewById(R.id.txt_eng_last_name);
        email = view.findViewById(R.id.txt_eng_email);
        phone = view.findViewById(R.id.txt_eng_phone_no);
        speciality = view.findViewById(R.id.txt_eng_speciality);
        idd = view.findViewById(R.id.txt_eng_id);
        // name=view.findViewById(R.id.txt_customer_name);
        btnEdit=view.findViewById(R.id.btn_edit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popOutFragments();
                fragment=new FragmentEdit();
                Bundle args=new Bundle();
                args.putSerializable("data",engineerModel);
                fragment.setArguments(args);
                setUpView();

            }
        });

        if(engineerModel!=null){
            name.setText(engineerModel.getFirstname());
            email.setText(engineerModel.getEmail());
            sname.setText(engineerModel.getLastname());
            phone.setText(engineerModel.getPhoneNumber());
            idd.setText(engineerModel.getEmployeeid());
            speciality.setText(engineerModel.getDesignation());

        }



    }
}
