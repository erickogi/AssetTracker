package com.assettrack.assettrack.CustomSearchDialog.Customer;

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

import com.assettrack.assettrack.Models.CustomerModel;
import com.assettrack.assettrack.R;

import java.util.ArrayList;
import java.util.List;

import ir.mirrajabi.searchdialog.StringsHelper;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class CustomerModelAdapter<T extends Searchable>
        extends RecyclerView.Adapter<CustomerModelAdapter.ViewHolder> {
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

    public CustomerModelAdapter(Context context, @LayoutRes int layout, List<T> items) {
        this(context, layout, null, items);
    }

    public CustomerModelAdapter(Context context, AdapterViewBinder<T> viewBinder,
                                @LayoutRes int layout, List<T> items) {
        this(context, layout, viewBinder, items);
    }

    public CustomerModelAdapter(Context context, @LayoutRes int layout,
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

    public CustomerModelAdapter<T> setViewBinder(AdapterViewBinder<T> viewBinder) {
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
    public void onBindViewHolder(CustomerModelAdapter.ViewHolder holder, int position) {
        initializeViews(getItem(position), holder, position);
    }

    private void initializeViews(final T object, final CustomerModelAdapter.ViewHolder holder,
                                 final int position) {
        if (mViewBinder != null)
            mViewBinder.bind(holder, object, position);
        TextView statusLbl, status, codeLbl, idLbl, id, nameLbl, name, locationLbl, location,
                emailLbl, email, phoneLbl, phone, countLbl, count, createdLbl, createdOn;
        RelativeLayout customer_background;

        customer_background = holder.getViewById(R.id.customer_background);
        status = holder.getViewById(R.id.txt_status);
        id = holder.getViewById(R.id.txt_id);
        name = holder.getViewById(R.id.txt_name);
        location = holder.getViewById(R.id.txt_location);
        email = holder.getViewById(R.id.txt_email);
        phone = holder.getViewById(R.id.txt_phone);
        count = holder.getViewById(R.id.txt_count);
        createdOn = holder.getViewById(R.id.txt_created_on);

        CustomerModel customerModel = (CustomerModel) object;

        status.setText(R.string.customer_default_status);
        phone.setText(customerModel.getTelephone());
        id.setText(String.valueOf(customerModel.getId()));
        name.setText(customerModel.getName());
        location.setText(customerModel.getPhysical_address());
        email.setText(customerModel.getAddress());
        createdOn.setText(customerModel.getCreated_at());
        count.setText(String.valueOf(customerModel.getAssetCount()));


        if (mSearchTag != null && mHighlightPartsInCommon)
            name.setText(StringsHelper.highlightLCS(customerModel.getName(), getSearchTag(),
                    Color.parseColor(mHighlightColor)));
        else name.setText(customerModel.getName());

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

    public CustomerModelAdapter setSearchTag(String searchTag) {
        mSearchTag = searchTag;
        return this;
    }

    public boolean isHighlightPartsInCommon() {
        return mHighlightPartsInCommon;
    }

    public CustomerModelAdapter setHighlightPartsInCommon(boolean highlightPartsInCommon) {
        mHighlightPartsInCommon = highlightPartsInCommon;
        return this;
    }

    public CustomerModelAdapter setHighlightColor(String highlightColor) {
        mHighlightColor = highlightColor;
        return this;
    }

    public CustomerModelAdapter setSearchDialog(BaseSearchDialogCompat searchDialog) {
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