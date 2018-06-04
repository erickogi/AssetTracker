package com.assettrack.assettrack.Adapters.ViewHolders;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.R;

import java.lang.ref.WeakReference;

public class IssueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private WeakReference<OnclickRecyclerListener> listenerWeakReference;
    public RelativeLayout issue_background;
    public TextView status,statusLbl,workTicket,workTicketLbl,customerName,customerNameLbl,assetName,assetNameLbl,
    engineerName,engineerNameLbl,start,startLbl,close,closeLbl;
    private SwipeRefreshLayout swipe_refresh_layout;


    public IssueViewHolder(View itemView, OnclickRecyclerListener onclickRecyclerListener) {
        super(itemView);
        listenerWeakReference = new WeakReference<>(onclickRecyclerListener);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

        issue_background=itemView.findViewById(R.id.issue_background);
        status=itemView.findViewById(R.id.txt_status);
        workTicket =itemView.findViewById(R.id.txt_workticket);
        customerName=itemView.findViewById(R.id.txt_customer_name);
        assetName=itemView.findViewById(R.id.txt_asset_name);
        engineerName=itemView.findViewById(R.id.txt_engineer);
        start=itemView.findViewById(R.id.txt_start);
        close=itemView.findViewById(R.id.txt_close);



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
