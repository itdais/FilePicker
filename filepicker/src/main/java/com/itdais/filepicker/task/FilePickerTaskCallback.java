package com.itdais.filepicker.task;

import com.itdais.filepicker.model.FileEntity;

import java.io.File;
import java.util.List;

/**
 * 查询文件列表
 *
 * @author ding.jw
 */
public interface FilePickerTaskCallback {
    void onLoaderSuccess(String queryPath, List<FileEntity> fileList);
}
