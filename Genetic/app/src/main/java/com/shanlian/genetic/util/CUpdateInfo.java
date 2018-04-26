package com.shanlian.genetic.util;

/**
 * Created by FY on 2016/12/26.
 * 版本更新实体类
 */

public class CUpdateInfo {
    private String version = "";
    private String url = "";
    private String description = "";

    public CUpdateInfo() {
        version = "";
        url = "";
        description = "";
    }

    @Override
    public String toString() {
        // 为什么要重写toString()呢？因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
        return version;
    }

    public String GetVersion() {
        return version;
    }

    public String GetUrl() {
        return url;
    }

    public String GetDescription() {
        return description;
    }

    public void SetVersion(String _version) {
        version = _version;
    }

    public void SetUrl(String _url) {
        url = _url;
    }

    public void SetDescription(String _description) {
        description = _description;
    }

}
