package com.assettrack.assettrack.Adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.R;

import java.lang.ref.WeakReference;

public class AssetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public WeakReference<OnclickRecyclerListener> listenerWeakReference;
    public RelativeLayout asset_background;
    public TextView statusLbl,status,codeLbl,code,nameLbl,name,customerLbl,customer,
    engineerLbl,engineer,lsdLbl,lsd,nsdLbl,nsd;
    public ImageView assetImg;

    public AssetViewHolder(View itemView, OnclickRecyclerListener onclickRecyclerListener) {
        super(itemView);
        listenerWeakReference = new WeakReference<>(onclickRecyclerListener);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        asset_background=itemView.findViewById(R.id.asset_background);
        assetImg=itemView.findViewById(R.id.img_asset);
        statusLbl=itemView.findViewById(R.id.txt_status_lbl);
        status=itemView.findViewById(R.id.txt_status);
        codeLbl=itemView.findViewById(R.id.txt_code_lbl);
        code=itemView.findViewById(R.id.txt_code);
        nameLbl=itemView.findViewById(R.id.txt_name_lbl);
        name=itemView.findViewById(R.id.txt_name);
        customerLbl=itemView.findViewById(R.id.txt_customer_lbl);
        customer=itemView.findViewById(R.id.txt_customer);
        engineerLbl=itemView.findViewById(R.id.txt_engineer_lbl);
        engineer=itemView.findViewById(R.id.txt_engineer);
        lsdLbl=itemView.findViewById(R.id.lsd_lbl);
        lsd=itemView.findViewById(R.id.txt_lsd);
        nsdLbl=itemView.findViewById(R.id.nsd_lbl);
        nsd=itemView.findViewById(R.id.txt_nsd);


    }

    @Override
    public void onClick(View v) {
        listenerWeakReference.get().onClickListener(getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        listenerWeakReference.get().onClickListener(getAdapterPosition(), v);

        return true;
    }
}
