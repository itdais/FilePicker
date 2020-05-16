package com.itdais.filepicker.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import com.itdais.filepicker.BuildConfig;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ding.jw
 */
public class FileUtils {

    /**
     * 获取格式化后的文件大小
     */
    public static String getSize(String path) {
        long fileSize = getLength(path);
        return ConvertUtils.toFileSizeString(fileSize);
    }

    /**
     * 获取文件后缀,不包括“.”
     */
    public static String getExtension(String pathOrUrl) {
        int dotPos = pathOrUrl.lastIndexOf('.');
        if (0 <= dotPos) {
            return pathOrUrl.substring(dotPos + 1);
        } else {
            return "ext";
        }
    }

    /**
     * 获取文件的MIME类型
     */
    public static String getMimeType(String pathOrUrl) {
        String ext = getExtension(pathOrUrl);
        return getMimeTypeForExtension(ext);
    }

    /**
     * 获取文件的MIME类型
     */
    public static String getMimeTypeForExtension(String extension) {
        MimeTypeMap map = MimeTypeMap.getSingleton();
        String mimeType;
        if (map.hasExtension(extension)) {
            mimeType = map.getMimeTypeFromExtension(extension);
        } else {
            mimeType = "*/*";
        }
        return mimeType;
    }

    /**
     * 获取格式化后的文件/目录创建或最后修改时间
     */
    public static String getDateTime(String path) {
        return getDateTime(path, "yyyy-MM-dd HH:mm");
    }

    /**
     * 获取格式化后的文件/目录创建或最后修改时间
     */
    public static String getDateTime(String path, String format) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return DateUtils.DateToString(new Date(file.lastModified()), format);
        }
        return null;
    }

    /**
     * 获取文件大小
     */
    public static long getLength(String path) {
        File file = new File(path);
        if (!file.isFile() || !file.exists()) {
            return 0;
        }
        return file.length();
    }

    /**
     * 获取文件的Uri
     *
     * @return
     */
    public static Uri getFileUri(Context context, String filePath, String authority) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return FileProvider.getUriForFile(context.getApplicationContext(), authority, file);
            } else {
                return Uri.fromFile(file);
            }
        }
        return null;
    }
}
