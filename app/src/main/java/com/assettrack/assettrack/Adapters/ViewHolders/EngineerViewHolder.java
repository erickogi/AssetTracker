package com.assettrack.assettrack.Adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.R;

import java.lang.ref.WeakReference;

public class EngineerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private WeakReference<OnclickRecyclerListener> listenerWeakReference;
    public RelativeLayout engineer_background;
    public TextView statusLbl,status,id,idLbl,nameLbl,name,phoneLbl,phone,designationLbl,designation,created0nLbl,createdOn;



    public EngineerViewHolder(View itemView, OnclickRecyclerListener onclickRecyclerListener) {
        super(itemView);
        listenerWeakReference = new WeakReference<>(onclickRecyclerListener);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        engineer_background=itemView.findViewById(R.id.engineer_background);
        status=itemView.findViewById(R.id.txt_status);
        id=itemView.findViewById(R.id.txt_id);
        name=itemView.findViewById(R.id.txt_name);
        phone=itemView.findViewById(R.id.txt_phone);
        designation=itemView.findViewById(R.id.txt_designation);
        createdOn=itemView.findViewById(R.id.txt_created_on);


    }

    @Override
    public void onClick(View v) {
        listenerWeakReference.get().onClickListener(getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        listenerWeakReference.get().onLongClickListener(getAdapterPosition());

        return true;
    }
}
