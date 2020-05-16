package com.itdais.filepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itdais.filepicker.R;
import com.itdais.filepicker.model.FilePathEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ding.jw
 */
public class FilePickerPathAdapter extends RecyclerView.Adapter<FilePickerPathAdapter.PathViewHolder> {
    private List<FilePathEntity> mDataList;
    private Context mContext;
    private OnPathItemClickListener mListener;

    public FilePickerPathAdapter() {
        mDataList = new ArrayList<>();
    }

    public List<FilePathEntity> getDataList() {
        return mDataList;
    }

    public FilePathEntity getItem(int position) {
        return mDataList.get(position);
    }

    public void cleanData() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void replaceData(List<FilePathEntity> pathList) {
        mDataList.clear();
        if (pathList != null) {
            mDataList.addAll(pathList);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加条目 取差集
     *
     * @param pathList
     */
    public void addItem(List<FilePathEntity> pathList) {
        if (pathList != null && pathList.size() > 0) {
            pathList.removeAll(mDataList);
            mDataList.addAll(pathList);
            notifyItemRangeInserted(mDataList.size() - pathList.size(), pathList.size());
        }
    }

    /**
     * 移除条目 取交集
     *
     * @param pathList
     */
    public void removeItem(List<FilePathEntity> pathList) {
        if (pathList != null && pathList.size() > 0) {
            int changeCount = mDataList.size() - pathList.size();
            mDataList.retainAll(pathList);
            notifyItemRangeRemoved(mDataList.size(), changeCount);
        }
    }

    @Override
    public PathViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        return new PathViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fp_adapter_file_picker_path, parent, false));
    }

    @Override
    public void onBindViewHolder(final PathViewHolder holder, int position) {
        holder.tvItem.setText(getItem(position).path);
        holder.tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class PathViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public PathViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }

    public void setListener(OnPathItemClickListener listener) {
        mListener = listener;
    }

    public interface OnPathItemClickListener {
        void onClick(int position);
    }
}
