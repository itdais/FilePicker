package com.itdais.filepicker.loader;

import android.database.Cursor;

import java.util.List;

/**
 * @author ding.jw
 */
public interface FileLoaderCallbacks {

    void onLoaderSuccess(Cursor cursor);

    void onFileLoaderReset();

}
