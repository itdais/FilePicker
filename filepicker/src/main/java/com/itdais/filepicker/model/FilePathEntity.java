package com.itdais.filepicker.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.itdais.filepicker.SelectOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件路径
 *
 * @author ding.jw
 */
public class FilePathEntity {
    /**
     * 滚动的位置
     */
    public int scrollPosition;
    public String path;

    public FilePathEntity(String path) {
        this.path = path;
    }

    public FilePathEntity(String path, int scrollPosition) {
        this.path = path;
        this.scrollPosition = scrollPosition;
    }

    @NonNull
    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FilePathEntity)) {
            return false;
        }

        FilePathEntity other = (FilePathEntity) obj;
        if (!TextUtils.isEmpty(path) && !TextUtils.isEmpty(other.path) && path.equals(other.path)) {
            return true;
        }
        return false;
    }

    /**
     * 获取路径
     *
     * @return
     */
    public static List<FilePathEntity> getPathList(String currentPath) {
        List<FilePathEntity> resultList = new ArrayList<>();
        if (!SelectOptions.DEFAULT_TARGET_PATH.equals(currentPath)) {
            String[] pataArray = currentPath.substring((SelectOptions.DEFAULT_TARGET_PATH + File.separator).length()).split("\\/");
            for (String s : pataArray) {
                resultList.add(new FilePathEntity(s));
            }
        }
        return resultList;
    }

    /**
     * 获取需要添加的路径列表
     *
     * @param oldList
     * @param newList
     * @return
     */
    public static List<FilePathEntity> getNewPathList(List<FilePathEntity> oldList, List<FilePathEntity> newList) {
        List<FilePathEntity> newModelList = new ArrayList<>();
        if (oldList == null || newList == null) {
            return newModelList;
        }
        if (oldList.size() >= newList.size()) {
            return newModelList;
        }
        for (int i = 0; i < newList.size(); i++) {
            if (i < oldList.size()) {
                continue;
            }
            newModelList.add(newList.get(i));
        }
        return newModelList;
    }

    /**
     * 获取路径List需要裁剪的位置
     *
     * @param oldList
     * @param newList
     * @return
     */
    public static List<FilePathEntity> getRemovedPathList(List<FilePathEntity> oldList, List<FilePathEntity> newList) {
        List<FilePathEntity> newModelList = new ArrayList<>();
        if (oldList == null || newList == null) {
            return newModelList;
        }
        if (oldList.size() <= newList.size()) {
            return newModelList;
        }
        for (int i = 0; i < oldList.size(); i++) {
            if (i < newList.size()) {
                continue;
            }
            newModelList.add(oldList.get(i));
        }
        return newModelList;
    }
}
