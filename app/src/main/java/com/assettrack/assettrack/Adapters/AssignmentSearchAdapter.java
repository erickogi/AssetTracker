package com.assettrack.assettrack.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.EngineerModel;
import com.assettrack.assettrack.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AssignmentSearchAdapter extends RecyclerView.Adapter<AssignmentSearchAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<AssetModel> modelList;
    private ArrayList<EngineerModel> engineerModels;
    private OnclickRecyclerListener onclickRecyclerListener;
    private int status=0;

    public AssignmentSearchAdapter(Context context, ArrayList<AssetModel> modelList, OnclickRecyclerListener listener) {
        this.context = context;
        this.modelList = modelList;
        this.onclickRecyclerListener = listener;
    }

    public AssignmentSearchAdapter(int status, ArrayList<EngineerModel> engineerModels, OnclickRecyclerListener listener) {
        this.context = context;
        this.engineerModels = engineerModels;
        this.status = status;
        this.onclickRecyclerListener = listener;
    }

    @Override
    public AssignmentSearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item_layout_1, parent, false);
        return new MyViewHolder(itemView, onclickRecyclerListener);
    }

    @Override
    public void onBindViewHolder(AssignmentSearchAdapter.MyViewHolder holder, int position) {

        holder.txtDate.setVisibility(View.VISIBLE);
        if (status == 0) {
            AssetModel customerModel = modelList.get(position);
            holder.txtDate.setVisibility(View.GONE);
            holder.txtDate.setText(customerModel.getInstallation_date());
            holder.txtCustomerName.setText(customerModel.getAsset_name());
            holder.txtCustomerLocation.setText(customerModel.getAsset_code());
        } else {

            EngineerModel engineerModel = engineerModels.get(position);
            Log.d("debugig",engineerModel.getFull_name());

            holder.txtDate.setVisibility(View.VISIBLE);
            holder.txtCustomerName.setText(engineerModel.getFull_name());
            holder.txtCustomerLocation.setText(engineerModel.getDesignation());
        }


    }

    @Override
    public int getItemCount() {

        if(engineerModels!=null){
            return engineerModels.size();
        }else if(modelList!=null){
            return modelList.size();
        }else {
            return 0;
        }
    }

    public void updateList(ArrayList<AssetModel> newList) {
        modelList = newList;
        notifyDataSetChanged();
    }

    public void updateList(int status, ArrayList<EngineerModel> newList) {
        engineerModels = newList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtCustomerName, txtCustomerLocation, txtDate;

        private WeakReference<OnclickRecyclerListener> listenerWeakReference;

        MyViewHolder(View itemView, OnclickRecyclerListener onclickRecyclerListener) {
            super(itemView);
            listenerWeakReference = new WeakReference<>(onclickRecyclerListener);

            txtDate = itemView.findViewById(R.id.txt_date);
            txtCustomerName = itemView.findViewById(R.id.txt_customer_name);
            txtCustomerLocation = itemView.findViewById(R.id.txt_customer_location);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listenerWeakReference.get().onClickListener(getAdapterPosition());
        }
    }
}
