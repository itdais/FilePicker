package com.itdais.filepicker.task;

import android.os.AsyncTask;

import com.itdais.filepicker.model.FileEntity;
import com.itdais.filepicker.model.FilePickerFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 查询文件列表任务
 *
 * @author ding.jw
 */
public class FilePickerTask extends AsyncTask<Void, Void, List<FileEntity>> {
    private String mFilePath;
    private String[] mTypes;
    private FilePickerTaskCallback mCallback;

    public FilePickerTask(String filePath, String[] types, FilePickerTaskCallback callback) {
        this.mFilePath = filePath;
        this.mTypes = types;
        this.mCallback = callback;
    }

    @Override
    protected List<FileEntity> doInBackground(Void... voids) {
        File file = new File(mFilePath);
        //过滤文件
        File[] files = file.listFiles(new FilePickerFilter(mTypes));
        if (files == null) {
            return new ArrayList<>();
        }
        List<FileEntity> resultList = FileEntity.getFileEntityList(files);
        Collections.sort(resultList, new SortByExtension());
        return resultList;
    }

    @Override
    protected void onPostExecute(List<FileEntity> fileList) {
        if (mCallback != null) {
            mCallback.onLoaderSuccess(mFilePath, fileList);
        }
    }

    /**
     * 按类型排序
     */
    public static class SortByExtension implements Comparator<FileEntity> {

        public SortByExtension() {
            super();
        }

        @Override
        public int compare(FileEntity f1, FileEntity f2) {
            if (f1 == null || f2 == null) {
                if (f1 == null) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (f1.isDirectory && f2.isFile) {
                    return -1;
                } else if (f1.isFile && f2.isDirectory) {
                    return 1;
                } else {
                    return f1.fileName.compareToIgnoreCase(f2.fileName);
                }
            }
        }
    }
}
