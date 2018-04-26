package com.shanlian.genetic.base;


import com.shanlian.genetic.util.FileUtils;
import com.shanlian.genetic.util.LogUtils;

/**
 * @创建者 CSDN_LQR
 * @描述 全局常量类
 */
public class AppConst {

    public static final String TAG = "kang";
    public static final int DEBUGLEVEL = LogUtils.LEVEL_ALL;//日志输出级别
    public static final String ISLOGIN = "login";//日志输出级别

    public static final String REGION = "86";

    public static final String SEARCH_TYPE = "search";

    public static final int SEARCH_FIRST = 1;//首页搜索
    public static final int SEARCH_CIRCLE = 2;//共享圈搜索


    //语音存放位置
    public static final String AUDIO_SAVE_DIR = FileUtils.getDir("audio");
    public static final int DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND = 120;
    //视频存放位置
    public static final String VIDEO_SAVE_DIR = FileUtils.getDir("video");
    //照片存放位置
    public static final String PHOTO_SAVE_DIR = FileUtils.getDir("photo");
    //头像保存位置
    public static final String HEADER_SAVE_DIR = FileUtils.getDir("header");
}
