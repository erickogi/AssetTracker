package com.assettrack.assettrack.CustomSearchDialog.Asset;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assettrack.assettrack.Models.AssetListModel;
import com.assettrack.assettrack.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.mirrajabi.searchdialog.StringsHelper;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

public class AssetModelAdapter<T extends Searchable>
        extends RecyclerView.Adapter<AssetModelAdapter.ViewHolder> {
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

    public AssetModelAdapter(Context context, @LayoutRes int layout, List<T> items) {
        this(context, layout, null, items);
    }

    public AssetModelAdapter(Context context, AdapterViewBinder<T> viewBinder,
                             @LayoutRes int layout, List<T> items) {
        this(context, layout, viewBinder, items);
    }

    public AssetModelAdapter(Context context, @LayoutRes int layout,
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

    public AssetModelAdapter<T> setViewBinder(AdapterViewBinder<T> viewBinder) {
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
    public void onBindViewHolder(AssetModelAdapter.ViewHolder holder, int position) {
        initializeViews(getItem(position), holder, position);
    }

    private void initializeViews(final T object, final AssetModelAdapter.ViewHolder holder,
                                 final int position) {
        if (mViewBinder != null)
            mViewBinder.bind(holder, object, position);

//        code=itemView.findViewById(R.id.txt_code);
//        nameLbl=itemView.findViewById(R.id.txt_name_lbl);
//        name=itemView.findViewById(R.id.txt_name);
//        customerLbl=itemView.findViewById(R.id.txt_customer_lbl);
//        customer=itemView.findViewById(R.id.txt_customer);
//
//


        //LinearLayout root = holder.getViewById(R.id.root);
        TextView textAsset = holder.getViewById(R.id.txt_name);
        TextView textAssetCode = holder.getViewById(R.id.txt_code);
        TextView textAssetCustomer = holder.getViewById(R.id.txt_customer);
        CircleImageView image = holder.getViewById(R.id.image);

        textAssetCode.setText(((AssetListModel) object).getCode());
        textAssetCustomer.setText(((AssetListModel) object).getCustomerModel().getName());

        if (mSearchTag != null && mHighlightPartsInCommon)
            textAsset.setText(StringsHelper.highlightLCS(((AssetListModel) object).getValue(), getSearchTag(),
                    Color.parseColor(mHighlightColor)));
        else textAsset.setText(((AssetListModel) object).getValue());

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

    public AssetModelAdapter setSearchTag(String searchTag) {
        mSearchTag = searchTag;
        return this;
    }

    public boolean isHighlightPartsInCommon() {
        return mHighlightPartsInCommon;
    }

    public AssetModelAdapter setHighlightPartsInCommon(boolean highlightPartsInCommon) {
        mHighlightPartsInCommon = highlightPartsInCommon;
        return this;
    }

    public AssetModelAdapter setHighlightColor(String highlightColor) {
        mHighlightColor = highlightColor;
        return this;
    }

    public AssetModelAdapter setSearchDialog(BaseSearchDialogCompat searchDialog) {
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