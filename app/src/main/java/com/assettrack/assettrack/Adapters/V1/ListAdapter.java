package com.assettrack.assettrack.Adapters.V1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assettrack.assettrack.Adapters.ViewHolders.ListViewHolder;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Models.ListModel;
import com.assettrack.assettrack.R;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private ArrayList<ListModel> listModel;

    private OnclickRecyclerListener onclickRecyclerListener;

    private Context context;
    private RequestOptions options;

    public ListAdapter(Context context, ArrayList<ListModel> listModel, OnclickRecyclerListener onclickRecyclerListener) {
        this.listModel = listModel;
        this.context = context;
        this.options = (new RequestOptions())
                .placeholder(R.drawable.imagepicker_image_placeholder)
                // .error(R.drawable.fixed_asset)
                .centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        this.onclickRecyclerListener = onclickRecyclerListener;
    }

    public void updateList(ArrayList<ListModel> list) {
        listModel = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);

        return new ListViewHolder(itemView, onclickRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ListModel listmodel = listModel.get(position);

        holder.label.setText(listmodel.getLabel());
        holder.value.setText(listmodel.getValue());
    }


    @Override
    public int getItemCount() {
        if (listModel == null || listModel.size() < 1) {
            return 0;
        }
        return listModel.size();
    }


}
