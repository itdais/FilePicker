package com.itdais.filepicker.simple;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.itdais.filepicker.FilePicker;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnFile;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnFile = findViewById(R.id.btn_file);
        tvInfo = findViewById(R.id.tv_info);
        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilePicker.from(MainActivity.this).chooseFile()
                        .setFileType(".txt")
                        .setAuthority("com.itdais.filepicker.simple.fileprovider")
                        .setThemeId(R.style.Mystyle)
                        .setTargetPath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Mypda/test")
                        .start(1006);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1006 && resultCode == RESULT_OK) {
            List<String> filePathList = FilePicker.obtainPathResult(data);
            StringBuilder sb = new StringBuilder();
            for (final String s : filePathList) {
                sb.append(s).append("\n");
            }
            tvInfo.setText(sb.toString());
        }
    }
}
