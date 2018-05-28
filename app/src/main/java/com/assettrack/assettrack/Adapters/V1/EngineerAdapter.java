package com.assettrack.assettrack.Adapters.V1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assettrack.assettrack.Adapters.ViewHolders.EngineerViewHolder;
import com.assettrack.assettrack.Adapters.ViewHolders.EngineerViewHolder;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Models.EngineerModel;
import com.assettrack.assettrack.R;

import java.util.ArrayList;

public class EngineerAdapter extends RecyclerView.Adapter<EngineerViewHolder> {

    private ArrayList<EngineerModel> engineerModels;
   
    private OnclickRecyclerListener onclickRecyclerListener;

    private Context context;

    public EngineerAdapter(Context context, ArrayList<EngineerModel> engineerModels, OnclickRecyclerListener onclickRecyclerListener) {
        this.engineerModels = engineerModels;
        this.context = context;

        this.onclickRecyclerListener = onclickRecyclerListener;
    }

   

    @NonNull
    @Override
    public EngineerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.engineer_item_view, parent, false);

        return new EngineerViewHolder(itemView, onclickRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EngineerViewHolder holder, int position) {
        EngineerModel engineerModel=engineerModels.get(position);
        if(engineerModel!=null){
           // holder.engineer_background.setBackgroundResource(R.color.transparent);
            holder.name.setText(engineerModel.getFull_name());
            holder.phone.setText(engineerModel.getPhoneNumber());
            holder.createdOn.setText(engineerModel.getCreated_at());
            holder.designation.setText(engineerModel.getDesignation());
            holder.id.setText(engineerModel.getEmployeeid());
            holder.status.setText(R.string.customer_default_status);
        }
       
  
    }

    @Override
    public int getItemCount() {
       if(engineerModels==null||engineerModels.size()<1){
           return 0;
       }return engineerModels.size();
    }

   

}
