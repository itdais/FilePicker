package com.itdais.filepicker.loader;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.lang.ref.WeakReference;

/**
 * @author ding.jw
 */
public class FileLoaderCollection implements LoaderManager.LoaderCallbacks<Cursor>, IFileLoadrListener {
    public static final int LOADER_ID = 3;
    private static final String ARGS_EXTENSION = "args_extension";
    private WeakReference<Context> mContext;
    private FileLoaderCallbacks mCallbacks;
    private LoaderManager mLoaderManager;

    @Override
    public void onCreate(@NonNull FragmentActivity context, @NonNull FileLoaderCallbacks callbacks) {
        mContext = new WeakReference<Context>(context);
        mLoaderManager = context.getSupportLoaderManager();
        mCallbacks = callbacks;
    }

    @Override
    public void onDestroy() {
        mLoaderManager.destroyLoader(LOADER_ID);
        mCallbacks = null;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }

        String extension = args.getString(ARGS_EXTENSION);
        return FileLoader.newInstance(context, extension);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
