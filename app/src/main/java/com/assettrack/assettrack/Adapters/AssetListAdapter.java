package com.assettrack.assettrack.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Models.AssetModel;
import com.assettrack.assettrack.R;
import com.bumptech.glide.Glide;
import com.haozhang.lib.SlantedTextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AssetListAdapter extends RecyclerView.Adapter<AssetListAdapter.MyViewHolder> {

    private ArrayList<AssetModel> assetModels;
    private int status;

    private OnclickRecyclerListener onclickRecyclerListener;

    private Context context;

    public AssetListAdapter(Context context, ArrayList<AssetModel> assetModels, int status, OnclickRecyclerListener onclickRecyclerListener) {
        this.assetModels = assetModels;
        this.context = context;
        this.status = status;
        this.onclickRecyclerListener = onclickRecyclerListener;
    }

    @NonNull
    @Override
    public AssetListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asset, parent, false);
        //this.context=parent.getContext();
        return new AssetListAdapter.MyViewHolder(itemView, onclickRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AssetListAdapter.MyViewHolder holder, int position) {
        // dbOperations = new DbOperations(context);
        AssetModel assetModel = assetModels.get(position);
        holder.mName.setText(assetModel.getAsset_name());


        holder.next.setText("Nxt : " + assetModel.getNextservicedate());
        holder.last.setText("Lst : " + assetModel.getUpdated_at());


        holder.customer.setText("Customer  ".concat(assetModel.getCustomers_id()));

        Log.d("asimg", "dfdf" + assetModel.getAsset_imageurl() + "" + assetModel.getAsset_image());
        if (assetModel.getAsset_image().equals("R")) {
            Glide.with(context).load(R.drawable.dvf).into(holder.imageView);
        } else {
            Glide.with(context).load(assetModel.getAsset_imageurl() + "" + assetModel.getAsset_image()).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return assetModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView imageView, imgMore;
        private CheckBox chckBox;
        private TextView mName, customer, last, next;
        private CardView layout;
        private SlantedTextView slantedTextView;
        private WeakReference<OnclickRecyclerListener> listenerWeakReference;


        public MyViewHolder(View itemView, OnclickRecyclerListener onclickRecyclerListener) {
            super(itemView);
            listenerWeakReference = new WeakReference<>(onclickRecyclerListener);

            imgMore = itemView.findViewById(R.id.more);
            chckBox = itemView.findViewById(R.id.checkbox);
            slantedTextView = itemView.findViewById(R.id.slanted_txt);
            customer = itemView.findViewById(R.id.txt_customer);
            mName = itemView.findViewById(R.id.txt_asset_name);
            last = itemView.findViewById(R.id.txt_last_updated);
            next = itemView.findViewById(R.id.txt_next_updated);

            imageView = itemView.findViewById(R.id.asset_image);
            // imgMore=imageView
            layout = itemView.findViewById(R.id.card);

            layout.setOnClickListener(this);
            chckBox.setOnClickListener(this);
            imgMore.setOnClickListener(this);
            layout.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.card) {
                listenerWeakReference.get().onClickListener(getAdapterPosition());
            }
            if (v.getId() == R.id.checkbox) {
                listenerWeakReference.get().onCheckedClickListener(getAdapterPosition());
            }
            if (v.getId() == R.id.more) {

                listenerWeakReference.get().onClickListener(getAdapterPosition(), v);
            }
            if (v.getId() == R.id.image) {

            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (v.getId() == R.id.card) {
                listenerWeakReference.get().onLongClickListener(getAdapterPosition());
            }
            return true;
        }
    }
}
