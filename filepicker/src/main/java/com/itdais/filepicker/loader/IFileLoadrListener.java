package com.itdais.filepicker.loader;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;

/**
 * @author ding.jw
 */
public interface IFileLoadrListener {
    void onCreate(FragmentActivity activity, FileLoaderCallbacks callbacks);

    void onDestroy();
}
