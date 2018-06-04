package com.assettrack.assettrack.Adapters.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.R;

import java.lang.ref.WeakReference;

public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public WeakReference<OnclickRecyclerListener> listenerWeakReference;
    public TextView statusLbl,status,codeLbl,idLbl,id,nameLbl,name,locationLbl,location,
    emailLbl,email,phoneLbl,phone,countLbl,count,createdLbl,createdOn;
    public RelativeLayout customer_background;

    public CustomerViewHolder(View itemView,OnclickRecyclerListener onclickRecyclerListener) {
        super(itemView);
        listenerWeakReference = new WeakReference<>(onclickRecyclerListener);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        customer_background=itemView.findViewById(R.id.customer_background);
        status=itemView.findViewById(R.id.txt_status);
        id=itemView.findViewById(R.id.txt_id);
        name=itemView.findViewById(R.id.txt_name);
        location=itemView.findViewById(R.id.txt_location);
        email=itemView.findViewById(R.id.txt_email);
        phone=itemView.findViewById(R.id.txt_phone);
        count=itemView.findViewById(R.id.txt_count);
        createdOn=itemView.findViewById(R.id.txt_created_on);

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
