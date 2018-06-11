package com.assettrack.assettrack.Adapters.V1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assettrack.assettrack.Adapters.ViewHolders.CustomerViewHolder;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Models.CategoriesModel;
import com.assettrack.assettrack.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CustomerViewHolder> {

    private ArrayList<CategoriesModel> categoriesModels;

    private OnclickRecyclerListener onclickRecyclerListener;

    private Context context;

    public CategoryAdapter(Context context, ArrayList<CategoriesModel> categoriesModels, OnclickRecyclerListener onclickRecyclerListener) {
        this.categoriesModels = categoriesModels;
        this.context = context;

        this.onclickRecyclerListener = onclickRecyclerListener;
    }

    public void updateList(ArrayList<CategoriesModel> list) {
        categoriesModels = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item_view, parent, false);

        return new CustomerViewHolder(itemView, onclickRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {

        CategoriesModel customerModel = categoriesModels.get(position);
        if (customerModel != null) {
            //  holder.customer_background.setBackgroundResource(R.color.transparent);
            holder.status.setText(R.string.customer_default_status);
            holder.id.setText(String.valueOf(customerModel.getId()));
            holder.name.setText(customerModel.getName());
            holder.createdOn.setText(customerModel.getCreated_on());

        }


    }

    @Override
    public int getItemCount() {
        if (categoriesModels == null || categoriesModels.size() < 1) {
            return 0;
        }
        return categoriesModels.size();
    }


}
