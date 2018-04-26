package com.shanlian.genetic.util;

import android.app.ProgressDialog;
import android.os.Environment;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by FY on 2016/12/26.
 */

public class VersionManager {
    //获取服务端升级信息
    public static CUpdateInfo getUpdateInfo(String path) throws Exception {
        CUpdateInfo info = new CUpdateInfo();//实体
        //包装成url的对象
        try
        {
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            InputStream is =conn.getInputStream();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, "utf-8");//设置解析的数据源
            int type = parser.getEventType();
            while(type != XmlPullParser.END_DOCUMENT ){
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if("version".equals(parser.getName()))
                        {
                            info.SetVersion(parser.nextText()); //获取版本号
                        }
                        else if ("url".equals(parser.getName()))
                        {
                            info.SetUrl(parser.nextText()); //获取要升级的APK文件
                        }
                        else if ("description".equals(parser.getName()))
                        {
                            info.SetDescription(parser.nextText()); //获取该文件的信息
                        }
                        break;
                }
                type = parser.next();
            }
        }
        catch(Exception exp)
        {
            String a = exp.getMessage();
            info.SetVersion("GETERROR");
        }
        return info;
    }

    //获取服务器端的文件
    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            try
            {
                URL url = new URL(path);
                HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                //获取到文件的大小
                pd.setMax(conn.getContentLength());
                InputStream is = conn.getInputStream();
                String dir = Environment.getExternalStorageDirectory() + "/";
                File file = new File(dir, "app-release.apk");
                FileOutputStream fos = new FileOutputStream(file);
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] buffer = new byte[1024];
                int len ;
                int total=0;
                while((len =bis.read(buffer))!=-1){
                    fos.write(buffer, 0, len);
                    total+= len;
                    //获取当前下载量
                    pd.setProgress(total);
                }
                fos.close();
                bis.close();
                is.close();
                return file;
            }
            catch(Exception exp)
            {
                String a = exp.getCause().toString();
                String b = exp.getMessage();
                return null;
            }
        }
        else
        {
            return null;
        }
    }
}
