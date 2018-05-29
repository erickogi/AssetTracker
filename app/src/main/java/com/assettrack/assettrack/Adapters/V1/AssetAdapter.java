package com.assettrack.assettrack.Adapters.V1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assettrack.assettrack.Adapters.ViewHolders.AssetViewHolder;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static com.assettrack.assettrack.R.drawable.fixed_asset;

public class AssetAdapter extends RecyclerView.Adapter<AssetViewHolder> {

    private ArrayList<AssetModel> assetModels;

    private OnclickRecyclerListener onclickRecyclerListener;

    private Context context;
    private RequestOptions options;

    public AssetAdapter(Context context, ArrayList<AssetModel> assetModels, OnclickRecyclerListener onclickRecyclerListener) {
        this.assetModels = assetModels;
        this.context = context;
        this.options = (new RequestOptions())
                .placeholder(R.drawable.imagepicker_image_placeholder)
                .error(R.drawable.fixed_asset)
                .centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        this.onclickRecyclerListener = onclickRecyclerListener;
    }



    @NonNull
    @Override
    public AssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.asset_item_view, parent, false);

        return new AssetViewHolder(itemView, onclickRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AssetViewHolder holder, int position) {
    AssetModel assetModel=assetModels.get(position);
        if(assetModel!=null){

            switch (assetModel.getState()){
                case 0:
                    holder.status.setTextColor(context.getResources().getColor(R.color.green));
                break;
                case 1:
                    holder.status.setTextColor(context.getResources().getColor(R.color.red));
                    break;
                case 2:
                    holder.status.setTextColor(context.getResources().getColor(R.color.orange_color_picker));
                    break;
                default:
                    holder.status.setTextColor(context.getResources().getColor(R.color.green));

            }
            holder.status.setText(assetModel.getStatename());
            holder.code.setText(assetModel.getAsset_code());
            holder.name.setText(assetModel.getAsset_name());
            holder.customer.setText(assetModel.getCustomerModel().getName());
            holder.engineer.setText(assetModel.getEngineerModel().getFull_name());
            holder.lsd.setText(assetModel.getLastservicedate());
            holder.nsd.setText(assetModel.getNextservicedate());
            Glide.with(context).load(assetModel.getAsset_image()).apply(options).into(holder.assetImg).onLoadFailed(context.getResources().getDrawable(fixed_asset));
        }


    }

    @Override
    public int getItemCount() {
       if(assetModels==null||assetModels.size()<1){
           return 0;
       }return assetModels.size();
    }



}
