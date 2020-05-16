package com.itdais.filepicker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.itdais.filepicker.ui.FilePickerActivity;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author ding.jw
 */
public class FilePicker {
    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;

    private FilePicker(Activity activity) {
        this(activity, null);
    }

    private FilePicker(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private FilePicker(Activity mContext, Fragment mFragment) {
        this.mContext = new WeakReference<>(mContext);
        this.mFragment = new WeakReference<>(mFragment);
    }

    public static FilePicker from(Activity activity) {
        return new FilePicker(activity);
    }

    public static FilePicker from(Fragment fragment) {
        return new FilePicker(fragment);
    }

    public SelectCreator chooseFile() {
        return new SelectCreator(this, SelectCreator.CHOOSE_FOR_DIRECTORY);
    }

    /**
     * Obtain user selected media' {@link Uri} list in the starting Activity or Fragment.
     *
     * @param data Intent passed by {@link Activity#onActivityResult(int, int, Intent)} or
     *             {@link Fragment#onActivityResult(int, int, Intent)}.
     * @return User selected media' {@link Uri} list.
     */
    public static List<Uri> obtainResult(Intent data) {
        return data.getParcelableArrayListExtra(FilePickerActivity.EXTRA_RESULT_URI);
    }

    /**
     * Obtain user selected media path list in the starting Activity or Fragment.
     *
     * @param data Intent passed by {@link Activity#onActivityResult(int, int, Intent)} or
     *             {@link Fragment#onActivityResult(int, int, Intent)}.
     * @return User selected media path list.
     */
    public static List<String> obtainPathResult(Intent data) {
        return data.getStringArrayListExtra(FilePickerActivity.EXTRA_RESULT_PATH);
    }

    @Nullable
    Activity getActivity() {
        return mContext.get();
    }

    @Nullable
    Fragment getFragment() {
        return mFragment != null ? mFragment.get() : null;
    }
}
