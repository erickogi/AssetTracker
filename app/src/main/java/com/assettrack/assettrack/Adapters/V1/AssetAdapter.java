package com.assettrack.assettrack.Adapters.V1;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assettrack.assettrack.Adapters.ViewHolders.AssetViewHolder;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.Models.CustomerModel;
import com.assettrack.assettrack.Models.EngineerModel;
import com.assettrack.assettrack.Models.IssueModel;
import com.assettrack.assettrack.R;
import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.assettrack.assettrack.R.drawable.fixed_asset;

public class AssetAdapter extends RecyclerView.Adapter<AssetViewHolder> {

    private ArrayList<AssetModel> assetModels;

    private OnclickRecyclerListener onclickRecyclerListener;

    private Context context;

    public AssetAdapter(Context context, ArrayList<AssetModel> assetModels, OnclickRecyclerListener onclickRecyclerListener) {
        this.assetModels = assetModels;
        this.context = context;

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
               // holder.asset_background.setBackgroundResource(R.color.green);
                break;
                case 1:
                //    holder.asset_background.setBackgroundResource(R.color.orange_color_picker);
                    break;
                case 2:
                //    holder.asset_background.setBackgroundResource(R.color.red);
                    break;

            }
            holder.status.setText(assetModel.getStatename());
            holder.code.setText(assetModel.getAsset_code());
            holder.name.setText(assetModel.getAsset_name());
            holder.customer.setText(assetModel.getCustomerModel().getName());
            holder.engineer.setText(assetModel.getEngineerModel().getFull_name());
            holder.lsd.setText(assetModel.getLastservicedate());
            holder.nsd.setText(assetModel.getNextservicedate());
            Glide.with(context).load(assetModel.getAsset_image()).into(holder.assetImg).onLoadFailed(context.getResources().getDrawable(fixed_asset));
        }


    }

    @Override
    public int getItemCount() {
       if(assetModels==null||assetModels.size()<1){
           return 0;
       }return assetModels.size();
    }



}
