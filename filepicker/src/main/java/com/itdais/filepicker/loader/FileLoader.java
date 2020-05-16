package com.itdais.filepicker.loader;

import android.content.ContentResolver;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.MimeTypeFilter;
import android.webkit.MimeTypeMap;

/**
 * 文件加载
 *
 * @author ding.jw
 */
public class FileLoader extends CursorLoader {
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    /**
     * 查询列
     */
    private static final String[] PROJECTION = new String[]{
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Files.FileColumns.DATE_MODIFIED};

    private static final String ORDER_BY = MediaStore.Files.FileColumns.DATE_TAKEN + " DESC";

    public FileLoader(@NonNull Context context, @Nullable String selection, @Nullable String[] selectionArgs) {
        super(context, QUERY_URI, PROJECTION, selection, selectionArgs, ORDER_BY);
    }

    public static CursorLoader newInstance(Context context, String extension) {
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        String selection = MediaStore.Files.FileColumns.MIME_TYPE + "='" + mimeType + "'";
        String[] selectionArgs = null;

        if (extension == null) {
            extension = "";
        }
//        if (extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase("docx")) {
//            selection = MediaStore.Files.FileColumns.MIME_TYPE + " in(?,?) ";
//            selectionArgs = new String[]{Const.mimeTypeMap.get("doc"), Const.mimeTypeMap.get("docx")};
//        }
//        if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
//            selection = MediaStore.Files.FileColumns.MIME_TYPE + " in(?,?) ";
//            selectionArgs = new String[]{Const.mimeTypeMap.get("xls"), Const.mimeTypeMap.get("xlsx")};
//        }
//        if (extension.equalsIgnoreCase("ppt") || extension.equalsIgnoreCase("pptx")) {
//            selection = MediaStore.Files.FileColumns.MIME_TYPE + " in(?,?) ";
//            selectionArgs = new String[]{Const.mimeTypeMap.get("ppt"), Const.mimeTypeMap.get("pptx")};
//        }
//        if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
//            selection = MediaStore.Files.FileColumns.MIME_TYPE + " in(?,?,?) ";
//            selectionArgs = new String[]{Const.mimeTypeMap.get("png"), Const.mimeTypeMap.get("jpg"), Const.mimeTypeMap.get("jpeg")};
//            //不扫描有.nomedia文件的文件夹下的多媒体文件，带有.nomedia文件的文件夹下的多媒体文件的media_type都被置为了0
//            selection = selection + " and " + MediaStore.Files.FileColumns.MEDIA_TYPE + " != " + MediaStore.Files.FileColumns.MEDIA_TYPE_NONE;
//        }
//        if (extension.equalsIgnoreCase("apk")) {
//            selection = MediaStore.Files.FileColumns.DATA + " LIKE '%.apk' ";
//        }
        return new FileLoader(context, selection, selectionArgs);
    }
}
