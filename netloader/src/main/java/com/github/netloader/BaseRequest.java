package com.github.netloader;


import java.util.HashMap;
import java.util.Map;

abstract class BaseRequest {
    private String secrect;
    private String appId;
    protected String sign;
    private Map<String, Object> params = new HashMap<>();

    {
        params.put("secrect", "");
        params.put("appId", "");
        registerPairParams(params);
        sign = "ssssssssssss";
    }

    abstract void registerPairParams(Map<String, Object> params);
}
