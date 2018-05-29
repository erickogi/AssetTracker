package com.assettrack.assettrack.Views.Admin.Issues;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.assettrack.assettrack.Models.IssueModel;
import com.assettrack.assettrack.R;

import java.util.Objects;

public class FragmentView extends Fragment {
    private View view;
    // private Button btnEdit;

    private IssueModel issueModel;

    private Button btnEdit;
    TextView edtStart,edtEnd,edtFix,edtSoln,edtEngRemarks,edtCustRemarks,edtSafety,
    edtTravelHours,edtLabourHours,edtNextServiceDate;


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
        return inflater.inflate(R.layout.fragment_view_issue,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        ((ActivityManageIssues) Objects.requireNonNull(getActivity())).setFab(R.drawable.ic_save_black_24dp,false);

        Bundle args=getArguments();
        if(args!=null){
            try{
                issueModel=(IssueModel) getArguments().getSerializable("data");
            }catch (Exception nm){
                nm.printStackTrace();
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

        btnEdit=view.findViewById(R.id.btn_edit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popOutFragments();
                fragment=new FragmentEdit();
                Bundle args=new Bundle();
                args.putSerializable("data",issueModel);
                fragment.setArguments(args);
                setUpView();

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
}
