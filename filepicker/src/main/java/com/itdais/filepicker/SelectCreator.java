package com.itdais.filepicker;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;

import com.itdais.filepicker.ui.FilePickerActivity;

/**
 * 选择配置
 *
 * @author ding.jw
 * <p>
 * 使用遍历文件夹:
 * 优点：可以实时拿到所要的数据
 * 缺点：效率相对比较低
 * <p>
 * 使用Android MediaStore:
 * 优点：效率比较高
 * 缺点：在通过第三方app下载之后，如果该app没有同步多媒体库，需要自己手动去同步多媒体库才能获取到实时的数据
 */
public final class SelectCreator {
    /**
     * 按文件目录
     */
    public static final String CHOOSE_FOR_DIRECTORY = "choose_for_directory";
    /**
     * 按照文件所属类型 如".txt" ".apk" ".jpg" ".png"
     */
    public static final String CHOOSE_FOR_MIME = "choose_for_mime";

    /**
     * 按图像/视频资源查找
     */
    public static final String CHOOSE_FOR_ALBUM = "choose_for_directory";

    private final FilePicker filePicker;
    private final SelectOptions selectOptions;
    private String chooseType;

    public SelectCreator(FilePicker filePicker, String chooseType) {
        this.chooseType = chooseType;
        this.filePicker = filePicker;
        selectOptions = SelectOptions.getCleanInstance();
    }

    public SelectCreator setMaxCount(@IntRange(from = 1, to = 9) int count) {
        selectOptions.maxCount = count;
        return this;
    }

    public SelectCreator setSingle() {
        selectOptions.maxCount = 1;
        return this;
    }

    /**
     * @param authority
     * @return
     */
    public SelectCreator setAuthority(String authority) {
        selectOptions.authority = authority;
        return this;
    }

    /**
     * 设置主题
     *
     * @param themeId
     * @return
     */
    public SelectCreator setThemeId(@StyleRes int themeId) {
        selectOptions.themeId = themeId;
        return this;
    }

    public SelectCreator setTargetPath(String path) {
        setTargetPath(path, false);
        return this;
    }

    /**
     * @param path
     * @param noExistMkdir 无此路径就创建
     * @return
     */
    public SelectCreator setTargetPath(String path, boolean noExistMkdir) {
        selectOptions.targetPath = path;
        selectOptions.noExistMkdir = noExistMkdir;
        return this;
    }

    public SelectCreator setFileType(String... fileTypes) {
        selectOptions.mFileTypes = fileTypes;
        return this;
    }

    public void start(int requestCode) {
        final Activity activity = filePicker.getActivity();
        if (activity == null) {
            return;
        }
        Intent intent = new Intent();
        if (SelectCreator.this.chooseType.equals(CHOOSE_FOR_DIRECTORY)) {
            intent.setClass(activity, FilePickerActivity.class);
        } else {
            return;
        }
        Fragment fragment = filePicker.getFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }
}
