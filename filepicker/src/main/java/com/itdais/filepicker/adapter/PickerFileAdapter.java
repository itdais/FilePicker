package com.itdais.filepicker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.itdais.filepicker.R;
import com.itdais.filepicker.model.FileEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * 选择文件适配器
 *
 * @author ding.jw
 */
public class PickerFileAdapter extends RecyclerView.Adapter<PickerFileAdapter.FileViewHolder> {
    private List<FileEntity> dataList;
    private Context mContext;
    private OnFileItemClickListener mListener;
    private int maxSelect;

    public PickerFileAdapter(int maxSelect) {
        this.dataList = new ArrayList<>();
        this.maxSelect = maxSelect;
    }

    public void cleanData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public void replaceData(List<FileEntity> fileList) {
        dataList.clear();
        if (fileList != null) {
            dataList.addAll(fileList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new FileViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fp_adapter_file_picker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final FileViewHolder holder, int position) {
        FileEntity fileEntity = dataList.get(position);
        holder.tvName.setText(fileEntity.fileName);
        if (maxSelect > 1) {
            holder.cbChoose.setVisibility(View.VISIBLE);
        } else {
            holder.cbChoose.setVisibility(View.GONE);
        }
        if (fileEntity.isDirectory) {
            holder.ivType.setImageResource(R.drawable.fp_folder);
            holder.tvDetail.setVisibility(View.GONE);
        } else {
            holder.tvDetail.setVisibility(View.VISIBLE);
            holder.tvDetail.setText(fileEntity.fileSize + "    " + fileEntity.lastDate);
            switch (fileEntity.extension) {
                case "txt":
                    holder.ivType.setImageResource(R.drawable.fp_txt);
                    break;
                case "zip":
                case "rar":
                case "7z":
                    holder.ivType.setImageResource(R.drawable.fp_zip);
                    break;
                default:
                    holder.ivType.setImageResource(R.drawable.fp_unknow);
                    break;
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(dataList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class FileViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivType;
        private TextView tvName;
        private TextView tvDetail;
        private CheckBox cbChoose;

        public FileViewHolder(View itemView) {
            super(itemView);
            ivType = itemView.findViewById(R.id.iv_type);
            cbChoose = itemView.findViewById(R.id.cb_choose);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDetail = itemView.findViewById(R.id.tv_detail);
        }
    }

    public interface OnFileItemClickListener {
        void onClick(FileEntity fileEntity, int position);
    }

    public void setListener(OnFileItemClickListener listener) {
        mListener = listener;
    }
}
