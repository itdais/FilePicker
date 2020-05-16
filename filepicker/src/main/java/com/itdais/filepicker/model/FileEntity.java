package com.itdais.filepicker.model;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.itdais.filepicker.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ding.jw
 */
public class FileEntity implements Parcelable {
    private Uri fileUri;
    public String filePath;
    public String fileName;
    public String fileSize;
    public String lastDate;
    /**
     * 如 txt对应text/plain
     * jpg对应image/jpeg
     */
    public String mimeType;
    /**
     * 如 .txt .apk .pdf
     */
    public String extension;
    public boolean isDirectory = false;
    public boolean isFile = false;
    public boolean isChecked = false;

    public FileEntity(File file) {
        filePath = file.getAbsolutePath();
        fileName = file.getName();
        if (file.exists()) {
            isDirectory = file.isDirectory();
            isFile = file.isFile();
        }
        if (isFile) {
            extension = FileUtils.getExtension(file.getAbsolutePath());
            mimeType = FileUtils.getMimeTypeForExtension(extension);
            fileSize = FileUtils.getSize(file.getAbsolutePath());
            lastDate = FileUtils.getDateTime(file.getAbsolutePath());
        }
    }

    public Uri getFileUri(Context context, String authority) {
        if (fileUri == null && !TextUtils.isEmpty(authority)) {
            fileUri = FileUtils.getFileUri(context, filePath, authority);
        }
        return fileUri;
    }

    /**
     * 获取文件列表
     *
     * @param files
     * @return
     */
    public static List<FileEntity> getFileEntityList(File[] files) {
        if (files == null) {
            return new ArrayList<>();
        }
        List<FileEntity> resultList = new ArrayList<>();
        for (File file : files) {
            resultList.add(new FileEntity(file));
        }
        return resultList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.fileUri, flags);
        dest.writeString(this.filePath);
        dest.writeString(this.fileName);
        dest.writeString(this.fileSize);
        dest.writeString(this.lastDate);
        dest.writeString(this.mimeType);
        dest.writeString(this.extension);
        dest.writeByte(this.isDirectory ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFile ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected FileEntity(Parcel in) {
        this.fileUri = in.readParcelable(Uri.class.getClassLoader());
        this.filePath = in.readString();
        this.fileName = in.readString();
        this.fileSize = in.readString();
        this.lastDate = in.readString();
        this.mimeType = in.readString();
        this.extension = in.readString();
        this.isDirectory = in.readByte() != 0;
        this.isFile = in.readByte() != 0;
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<FileEntity> CREATOR = new Creator<FileEntity>() {
        @Override
        public FileEntity createFromParcel(Parcel source) {
            return new FileEntity(source);
        }

        @Override
        public FileEntity[] newArray(int size) {
            return new FileEntity[size];
        }
    };
}
