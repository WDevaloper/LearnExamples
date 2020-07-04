package com.github.netloader;


import android.os.Build;

import java.util.Map;

public class BannerRequest extends BaseRequest {
    private String id;
    private int age;

    private BannerRequest(Builder builder) {
        this.age = builder.age;
        this.id = builder.id;
    }

    @Override
    void registerPairParams(Map<String, Object> params) {
        params.put("id", id);
        params.put("age", age);
    }


    static class Builder {
        private String id;
        private int age;

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        BannerRequest build() {
            return new BannerRequest(this);
        }
    }


    @Override
    public String toString() {
        return "BannerRequest{" +
                "id='" + id + '\'' +
                ", age=" + age +
                ", sign='" + sign + '\'' +
                '}';
    }
}
