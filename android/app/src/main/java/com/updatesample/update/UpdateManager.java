package com.updatesample.update;

import android.app.Activity;
import android.os.Environment;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UpdateManager {
    private Activity activity;
    private static String LOCAL_FILE_DIR_PATH;

    public UpdateManager(Activity activity) {
        this.activity = activity;
        LOCAL_FILE_DIR_PATH = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * 模拟网络获取服务器版本
     */
    public void checkUpdate(CheckVersionBean bean) {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                long currentVersion = getLocalVersion();
                ServerVersionBean serverVersionBean = new ServerVersionBean(currentVersion + 1, bean.url, "有新版本");
                if (serverVersionBean.version > currentVersion) {//需要更新
                    downloadBundleOnNewThread(bean);
                }
            }

        }).start();
    }

    public int getLocalVersion() {
        //如果
        return 0;
    }

    public void downloadBundleOnNewThread(CheckVersionBean bean) {
        new Thread(() -> downloadBundle(bean)).start();
    }


    public void downloadBundle(CheckVersionBean bean) {
        //目录不存在则创建
        File dir = new File(LOCAL_FILE_DIR_PATH + File.separator + bean.moduleName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //文件存在则删除
        final String path = dir.getAbsolutePath() + File.separator + bean.localFielName;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }

        FileDownloader.setup(activity);
        FileDownloader.getImpl().create(bean.url)
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        if (totalBytes > 0)
                            System.out.println("pending..." + soFarBytes / totalBytes);
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        if (totalBytes > 0)
                            System.out.println("progerss..." + soFarBytes / totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        //解压
                        decompression(path, LOCAL_FILE_DIR_PATH + File.separator + bean.moduleName);
                        Toast.makeText(activity, "下载并且解压bundle文件成功", Toast.LENGTH_SHORT).show();
                        //发送通知 reload

                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        System.out.println();
                    }
                }).start();
    }

    /**
     * 文件解压
     *
     * @param zipPath   zip文件地址
     * @param targetDir 解压到的目标文件夹
     */
    public static void decompression(String zipPath, String targetDir) {
        ZipInputStream inZip = null;
        try {
            inZip = new ZipInputStream(new FileInputStream(zipPath));

            ZipEntry zipEntry;
            String szName;
            while ((zipEntry = inZip.getNextEntry()) != null) {
                szName = zipEntry.getName();
                if (zipEntry.isDirectory()) {

                    szName = szName.substring(0, szName.length() - 1);
                    File folder = new File(targetDir + File.separator + szName);
                    folder.mkdirs();
                } else {
                    File file1 = new File(targetDir + File.separator + szName);
                    boolean s = file1.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file1);
                    int len;
                    byte[] buffer = new byte[1024];

                    while ((len = inZip.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                }
            }
            inZip.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
