package com.updatesample.update;

/**
 * Created by ivesj on 18/3/24.
 */

public class CheckVersionBean {
    public String moduleName;//模块名字
    public String url;
    public String localFielName;
    public String localFileDirPath;

    public CheckVersionBean() {

    }

    public CheckVersionBean(String moduleName, String url, String localFielName, String localFileDirPath) {
        this.moduleName = moduleName;
        this.url = url;
        this.localFielName = localFielName;
        this.localFileDirPath = localFileDirPath;
    }
}

