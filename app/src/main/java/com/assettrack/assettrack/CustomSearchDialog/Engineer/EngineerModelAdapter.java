package com.assettrack.assettrack.CustomSearchDialog.Engineer;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assettrack.assettrack.Models.EngineerModel;
import com.assettrack.assettrack.R;

import java.util.ArrayList;
import java.util.List;

import ir.mirrajabi.searchdialog.StringsHelper;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class EngineerModelAdapter<T extends Searchable>
        extends RecyclerView.Adapter<EngineerModelAdapter.ViewHolder> {
    protected Context mContext;
    private List<T> mItems = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private int mLayout;
    private SearchResultListener mSearchResultListener;
    private AdapterViewBinder<T> mViewBinder;
    private String mSearchTag;
    private boolean mHighlightPartsInCommon = true;
    private String mHighlightColor = "#FFED2E47";
    private BaseSearchDialogCompat mSearchDialog;

    public EngineerModelAdapter(Context context, @LayoutRes int layout, List<T> items) {
        this(context, layout, null, items);
    }

    public EngineerModelAdapter(Context context, AdapterViewBinder<T> viewBinder,
                                @LayoutRes int layout, List<T> items) {
        this(context, layout, viewBinder, items);
    }

    public EngineerModelAdapter(Context context, @LayoutRes int layout,
                                @Nullable AdapterViewBinder<T> viewBinder,
                                List<T> items) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mItems = items;
        this.mLayout = layout;
        this.mViewBinder = viewBinder;
    }

    public List<T> getItems() {
        return mItems;
    }

    public void setItems(List<T> objects) {
        this.mItems = objects;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public EngineerModelAdapter<T> setViewBinder(AdapterViewBinder<T> viewBinder) {
        this.mViewBinder = viewBinder;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mLayoutInflater.inflate(mLayout, parent, false);
        convertView.setTag(new ViewHolder(convertView));
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EngineerModelAdapter.ViewHolder holder, int position) {
        initializeViews(getItem(position), holder, position);
    }

    private void initializeViews(final T object, final EngineerModelAdapter.ViewHolder holder,
                                 final int position) {
        if (mViewBinder != null)
            mViewBinder.bind(holder, object, position);
        RelativeLayout engineer_background;
        TextView statusLbl, status, id, idLbl, nameLbl, name, phoneLbl, phone, designationLbl, designation, created0nLbl, createdOn;

//        code=itemView.findViewById(R.id.txt_code);
//        nameLbl=itemView.findViewById(R.id.txt_name_lbl);
//        name=itemView.findViewById(R.id.txt_name);
//        customerLbl=itemView.findViewById(R.id.txt_customer_lbl);
//        customer=itemView.findViewById(R.id.txt_customer);
//
//

        engineer_background = holder.getViewById(R.id.engineer_background);
        status = holder.getViewById(R.id.txt_status);
        id = holder.getViewById(R.id.txt_id);
        name = holder.getViewById(R.id.txt_name);
        phone = holder.getViewById(R.id.txt_phone);
        designation = holder.getViewById(R.id.txt_designation);
        createdOn = holder.getViewById(R.id.txt_created_on);


        EngineerModel engineerModel = (EngineerModel) object;
        name.setText(engineerModel.getFull_name());
        phone.setText(engineerModel.getPhoneNumber());
        createdOn.setText(engineerModel.getCreated_at());
        designation.setText(engineerModel.getDesignation());
        id.setText(engineerModel.getEmployeeid());
        status.setText(R.string.customer_default_status);

        if (mSearchTag != null && mHighlightPartsInCommon)
            name.setText(StringsHelper.highlightLCS(engineerModel.getFull_name(), getSearchTag(),
                    Color.parseColor(mHighlightColor)));
        else name.setText(engineerModel.getFull_name());

        if (mSearchResultListener != null)
            holder.getBaseView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSearchResultListener.onSelected(mSearchDialog, object, position);
                }
            });
    }

    public SearchResultListener getSearchResultListener() {
        return mSearchResultListener;
    }

    public void setSearchResultListener(SearchResultListener searchResultListener) {
        this.mSearchResultListener = searchResultListener;
    }

    public String getSearchTag() {
        return mSearchTag;
    }

    public EngineerModelAdapter setSearchTag(String searchTag) {
        mSearchTag = searchTag;
        return this;
    }

    public boolean isHighlightPartsInCommon() {
        return mHighlightPartsInCommon;
    }

    public EngineerModelAdapter setHighlightPartsInCommon(boolean highlightPartsInCommon) {
        mHighlightPartsInCommon = highlightPartsInCommon;
        return this;
    }

    public EngineerModelAdapter setHighlightColor(String highlightColor) {
        mHighlightColor = highlightColor;
        return this;
    }

    public EngineerModelAdapter setSearchDialog(BaseSearchDialogCompat searchDialog) {
        mSearchDialog = searchDialog;
        return this;
    }

    public interface AdapterViewBinder<T> {
        void bind(ViewHolder holder, T item, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View mBaseView;

        public ViewHolder(View view) {
            super(view);
            mBaseView = view;
        }

        public View getBaseView() {
            return mBaseView;
        }

        public <T> T getViewById(@IdRes int id) {
            return (T) mBaseView.findViewById(id);
        }

        public void clearAnimation(@IdRes int id) {
            mBaseView.findViewById(id).clearAnimation();
        }
    }
}