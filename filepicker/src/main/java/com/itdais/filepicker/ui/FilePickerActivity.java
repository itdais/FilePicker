package com.itdais.filepicker.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.itdais.filepicker.R;
import com.itdais.filepicker.SelectOptions;
import com.itdais.filepicker.adapter.FilePickerPathAdapter;
import com.itdais.filepicker.adapter.PickerFileAdapter;
import com.itdais.filepicker.model.FileEntity;
import com.itdais.filepicker.model.FilePathEntity;
import com.itdais.filepicker.task.FilePickerTask;
import com.itdais.filepicker.task.FilePickerTaskCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 文件浏览选择页
 *
 * @author ding.jw
 */
public class FilePickerActivity extends AppCompatActivity implements FilePickerTaskCallback {
    public static final String EXTRA_RESULT_URI = "extra_result_selection_uri";
    public static final String EXTRA_RESULT_PATH = "extra_result_selection_path";

    private Toolbar mToolbar;
    private TextView mTvRoot;
    private RecyclerView mPathRecyclerview;
    private RecyclerView mDataRecyclerview;
    private View mEmptyView;
    private FilePickerPathAdapter mPathAdapter;
    private PickerFileAdapter mFileAdapter;

    private String mCurFolder;

    private FilePickerTask mFilePickerTask;
    private List<FileEntity> selectDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(SelectOptions.getInstance().themeId);
        setContentView(R.layout.fp_activity_picker_file);
        selectDataList = new ArrayList<>();
        initToolbar();
        initView();
        initRecyclerView();
        executeTask(mCurFolder);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("选择文件");
    }

    private void initView() {
        mTvRoot = findViewById(R.id.tv_path_root);
        mPathRecyclerview = findViewById(R.id.recyclerview_path);
        mDataRecyclerview = findViewById(R.id.recyclerview_data);
        mEmptyView = findViewById(R.id.empty_view);
        mTvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurFolder.equals(SelectOptions.DEFAULT_TARGET_PATH)) {
                    return;
                }
                executeTask(SelectOptions.DEFAULT_TARGET_PATH);
            }
        });
    }

    private void initRecyclerView() {
        mCurFolder = SelectOptions.getInstance().getTargetPath();
        mPathAdapter = new FilePickerPathAdapter();
        mPathAdapter.setListener(new FilePickerPathAdapter.OnPathItemClickListener() {
            @Override
            public void onClick(int position) {
                String filePath = buildFilePathByPosition(mPathAdapter.getDataList(), position);
                if (mCurFolder.equals(filePath)) {
                    return;
                }
                executeTask(filePath);
            }
        });
        mPathRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPathRecyclerview.setAdapter(mPathAdapter);

        mFileAdapter = new PickerFileAdapter(SelectOptions.getInstance().maxCount);
        mFileAdapter.setListener(new PickerFileAdapter.OnFileItemClickListener() {
            @Override
            public void onClick(FileEntity fileEntity, int position) {
                if (fileEntity == null) {
                    return;
                }
                if (fileEntity.isDirectory) {
                    executeTask(fileEntity.filePath);
                } else {
                    selectDataList.add(fileEntity);
                    if (SelectOptions.getInstance().isSingleSelect()) {
                        setResultData();
                    }
                }
            }
        });
        mDataRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mDataRecyclerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mDataRecyclerview.setHasFixedSize(true);
        mDataRecyclerview.setAdapter(mFileAdapter);
    }

    /**
     * 设置结果
     */
    public void setResultData() {
        Intent result = new Intent();
        ArrayList<Uri> uriList = new ArrayList<>();
        ArrayList<String> pathList = new ArrayList<>();
        String authority = SelectOptions.getInstance().authority;
        for (FileEntity fileEntity : selectDataList) {
            if (!TextUtils.isEmpty(authority)) {
                uriList.add(fileEntity.getFileUri(this, authority));
            }
            pathList.add(fileEntity.filePath);
        }
        result.putParcelableArrayListExtra(EXTRA_RESULT_URI, uriList);
        result.putStringArrayListExtra(EXTRA_RESULT_PATH, pathList);
        setResult(RESULT_OK, result);
        finish();
    }

    /**
     * 执行查询
     *
     * @param queryPath
     */
    private void executeTask(String queryPath) {
        //保留当前的滚动进度
        if (mPathAdapter.getItemCount() > 0 && !queryPath.equals(SelectOptions.DEFAULT_TARGET_PATH)) {
            mPathAdapter.getDataList().get(mPathAdapter.getItemCount() - 1).scrollPosition = mDataRecyclerview.computeVerticalScrollOffset();
        }
        mFileAdapter.cleanData();
        mFilePickerTask = new FilePickerTask(queryPath, SelectOptions.getInstance().getFileTypes(), this);
        mFilePickerTask.execute();
    }

    @Override
    public void onLoaderSuccess(String queryPath, List<FileEntity> fileList) {
        //设置数据
        mFileAdapter.replaceData(fileList);
        if (fileList == null || fileList.isEmpty()) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }
        //设置路径
        mCurFolder = queryPath;
        if (!mCurFolder.equals(SelectOptions.DEFAULT_TARGET_PATH)) {
            List<FilePathEntity> newPathList = FilePathEntity.getPathList(mCurFolder);
            if (newPathList.size() > mPathAdapter.getItemCount()) {
                //新增
                mPathAdapter.addItem(newPathList);
            } else {
                //减少
                mPathAdapter.removeItem(newPathList);
            }
            if (mPathAdapter.getItemCount() > 1) {
                mPathRecyclerview.smoothScrollToPosition(mPathAdapter.getItemCount() - 1);
            }
        } else {
            mPathAdapter.cleanData();
        }

        mDataRecyclerview.scrollToPosition(0);
        if (mPathAdapter.getItemCount() > 0) {
            //先让其滚动到顶部，然后再scrollBy，滚动到之前保存的位置
            int scrollYPosition = mPathAdapter.getItem(mPathAdapter.getItemCount() - 1).scrollPosition;
            //恢复之前的滚动位置
            mDataRecyclerview.scrollBy(0, scrollYPosition);
        }
    }

    /**
     * 获取当前文件路径
     *
     * @param position
     * @return
     */
    private static String buildFilePathByPosition(List<FilePathEntity> pathList, int position) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= position; i++) {
            sb.append(File.separator).append(pathList.get(i).path);
        }
        return SelectOptions.DEFAULT_TARGET_PATH + sb.toString();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFilePickerTask != null) {
            mFilePickerTask.cancel(true);
        }
    }

}
