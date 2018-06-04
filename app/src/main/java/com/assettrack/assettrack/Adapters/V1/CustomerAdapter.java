package com.assettrack.assettrack.Adapters.V1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assettrack.assettrack.Adapters.ViewHolders.CustomerViewHolder;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Models.CustomerModel;
import com.assettrack.assettrack.R;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerViewHolder> {

    private ArrayList<CustomerModel> customerModels;
   
    private OnclickRecyclerListener onclickRecyclerListener;

    private Context context;

    public CustomerAdapter(Context context, ArrayList<CustomerModel> customerModels, OnclickRecyclerListener onclickRecyclerListener) {
        this.customerModels = customerModels;
        this.context = context;

        this.onclickRecyclerListener = onclickRecyclerListener;
    }

    public void updateList(ArrayList<CustomerModel> list) {
        customerModels = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item_view, parent, false);

        return new CustomerViewHolder(itemView, onclickRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {

        CustomerModel customerModel=customerModels.get(position);
        if(customerModel!=null){
          //  holder.customer_background.setBackgroundResource(R.color.transparent);
            holder.status.setText(R.string.customer_default_status);
            //holder.id.setText(String.valueOf(customerModel.getId()));
            holder.name.setText(customerModel.getName());
            holder.location.setText(customerModel.getPhysical_address());
            holder.email.setText(customerModel.getAddress());
            holder.createdOn.setText(customerModel.getCreated_at());
            holder.count.setText(String.valueOf(customerModel.getAssetCount()));
        }


    }

    @Override
    public int getItemCount() {
       if(customerModels==null||customerModels.size()<1){
           return 0;
       }return customerModels.size();
    }

   

}
