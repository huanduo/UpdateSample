package com.updatesample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.updatesample.rn.RNActivity;
import com.updatesample.rn.RNOneActivity;
import com.updatesample.rn.RNTwoActivity;
import com.updatesample.update.CheckVersionBean;
import com.updatesample.update.UpdateManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    UpdateManager updateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateManager = new UpdateManager(this);
    }

    public void goRNActivity(View v) {
        startActivity(new Intent(this, RNActivity.class));
    }
    public void goOneActivity(View v) {
        startActivity(new Intent(this, RNOneActivity.class));
    }
    public void goTwoActivity(View v) {
        startActivity(new Intent(this, RNTwoActivity.class));
    }

    public void downloadOneBundle(View v) {
        String url = "http://192.168.31.113:8080/bundle/bundle_one.zip";
        String localDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        CheckVersionBean bean = new CheckVersionBean("one", url, "one.rar", localDir);
        updateManager.checkUpdate(bean);

        if ( getApplication() != null)
            ((MainApplication) getApplication()).getReactNativeHost().clear();
    }

    public void downloadTwoBundle(View v) {
        String url = "http://192.168.31.113:8080/bundle/bundle_two.zip";
        String localDir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        CheckVersionBean bean = new CheckVersionBean("two", url, "two.rar", localDir);
        updateManager.checkUpdate(bean);
    }

    public void clearBundles(View v) {
        if ( getApplication() != null)
            ((MainApplication) getApplication()).getReactNativeHost().clear();

        deleteFileOrDir(new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()));
        Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
    }

    public static void deleteFileOrDir(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteFileOrDir(f);
            }
            file.delete();
        }

    }


}
