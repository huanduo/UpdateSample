package com.updatesample.update;

/**
 * Created by ivesj on 18/3/24.
 */

public class ServerVersionBean {
    public long version;
    public String url;
    public String desc;

    public ServerVersionBean(){

    }
    public ServerVersionBean(long version, String url, String desc) {
        this.version = version;
        this.url = url;
        this.desc = desc;
    }
}
