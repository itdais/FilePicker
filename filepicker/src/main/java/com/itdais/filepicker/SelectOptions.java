package com.itdais.filepicker;

import android.os.Environment;
import android.support.annotation.StyleRes;
import android.text.TextUtils;

import java.io.File;

/**
 * 选择配置
 *
 * @author ding.jw
 */
public class SelectOptions {
    /**
     * 默认目录
     */
    public static final String DEFAULT_TARGET_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    /**
     * 选择的类型
     */
    protected String[] mFileTypes;
    /**
     * 支持android7.0
     */
    public String authority;
    /**
     * 默认单选
     */
    public int maxCount = 1;
    /**
     * 选择的路径
     */
    protected String targetPath = DEFAULT_TARGET_PATH;
    /**
     * 无此路径就创建
     */
    protected boolean noExistMkdir = false;

    @StyleRes
    public int themeId = R.style.FilePicker_Default;

    private static class SelectOptionsInstance {
        private static final SelectOptions INSTANCE = new SelectOptions();
    }

    public static SelectOptions getInstance() {
        return SelectOptionsInstance.INSTANCE;
    }

    public static SelectOptions getCleanInstance() {
        SelectOptions options = getInstance();
        options.reset();
        return options;
    }

    /**
     * 默认单选
     */
    private void reset() {
        mFileTypes = new String[]{};
        maxCount = 1;
    }

    /**
     * 是否为单选
     *
     * @return
     */
    public boolean isSingleSelect() {
        return maxCount == 1;
    }

    /**
     * 获取要筛选的文件类型
     *
     * @return
     */
    public String[] getFileTypes() {
        if (mFileTypes == null || mFileTypes.length == 0) {
            return new String[]{};
        }
        return mFileTypes;
    }

    /**
     * 获取筛选文件路径
     *
     * @return
     */
    public String getTargetPath() {
        if (TextUtils.isEmpty(targetPath)) {
            targetPath = DEFAULT_TARGET_PATH;
        }
        if (!DEFAULT_TARGET_PATH.equals(targetPath)) {
            File file = new File(targetPath);
            if (!file.exists()) {
                if (noExistMkdir) {
                    file.mkdirs();
                } else {
                    return DEFAULT_TARGET_PATH;
                }
            }
        }
        return targetPath;
    }

}
