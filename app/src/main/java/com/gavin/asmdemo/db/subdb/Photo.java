package com.gavin.asmdemo.db.subdb;

import com.gavin.asmdemo.db.aninations.DbTable;

/**
 * @Describe:
 * @Author: wfy
 */
@DbTable("tb_photo")
public class Photo {
    private String time;
    private String path;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "time='" + time + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
