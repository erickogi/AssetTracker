package com.assettrack.assettrack.Adapters.V1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assettrack.assettrack.Adapters.ViewHolders.AssetViewHolder;
import com.assettrack.assettrack.Adapters.ViewHolders.IssueViewHolder;
import com.assettrack.assettrack.Interfaces.UtilListeners.OnclickRecyclerListener;
import com.assettrack.assettrack.Models.IssueModel;
import com.assettrack.assettrack.R;

import java.util.ArrayList;

public class IssueAdapter extends RecyclerView.Adapter<IssueViewHolder> {

    private ArrayList<IssueModel> issueModels;
   
    private OnclickRecyclerListener onclickRecyclerListener;

    private Context context;

    public IssueAdapter(Context context, ArrayList<IssueModel> issueModels, OnclickRecyclerListener onclickRecyclerListener) {
        this.issueModels = issueModels;
        this.context = context;

        this.onclickRecyclerListener = onclickRecyclerListener;
    }

   

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_item_view, parent, false);

        return new IssueViewHolder(itemView, onclickRecyclerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {
//        public RelativeLayout issue_background;
//        public TextView status,statusLbl,workTicket,workTicketLbl,customerName,customerNameLbl,assetName,assetNameLbl,
//                engineerName,engineerNameLbl,start,startLbl,close,closeLbl;
        IssueModel issueModel=issueModels.get(position);
        if(issueModel!=null){
            switch (issueModel.getIssue_status()){
                case 0:
               //     holder.issue_background.setBackgroundResource(R.color.transparent);
                    break;
                case 1:
               //     holder.issue_background.setBackgroundResource(R.color.orange_color_picker);
                    break;
                    default:
                //        holder.issue_background.setBackgroundResource(R.color.transparent);

            }
            holder.assetName.setText(issueModel.getAssetModel().getAsset_name());
            holder.customerName.setText(issueModel.getCustomerModel().getName());
            holder.engineerName.setText(issueModel.getEngineername());
            holder.status.setText(issueModel.getStatename());
            holder.workTicket.setText(issueModel.getWork_tickets());
            holder.start.setText(issueModel.getStartdate());
            holder.close.setText(issueModel.getClosedate());

        }


    }

    @Override
    public int getItemCount() {
       if(issueModels==null||issueModels.size()<1){
           return 0;
       }return issueModels.size();
    }

   

}
