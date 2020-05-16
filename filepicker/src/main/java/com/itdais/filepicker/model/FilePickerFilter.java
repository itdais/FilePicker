package com.itdais.filepicker.model;

import java.io.File;
import java.io.FileFilter;

/**
 * 文件筛选
 *
 * @author ding.jw
 */
public class FilePickerFilter implements FileFilter {
    private String[] mFileType;

    public FilePickerFilter(String[] fileType) {
        this.mFileType = fileType;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory() && !file.isHidden()) {
            return true;
        }
        if (mFileType != null && mFileType.length > 0) {
            for (String mType : mFileType) {
                if ((file.getName().endsWith(mType.toLowerCase()) || file.getName().endsWith(mType.toUpperCase())) && !file.isHidden()) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }
}
