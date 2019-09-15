package com.acgnu.origin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by _ORIGINAL™ on 2017/9/9.
 */
public class DownloadFileFromNet {
    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        //conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        conn.setRequestMethod("GET") ;
        conn.setRequestProperty(
                "Accept",
                "image/gif, image/jpeg, image/pjpeg, image/pjpeg, " +
                        "application/x-shockwave-flash, application/xaml+xml, " +
                        "application/vnd.ms-xpsdocument, application/x-ms-xbap, " +
                        "application/x-ms-application, application/vnd.ms-excel, " +
                        "application/vnd.ms-powerpoint, application/msword, */*");
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Accept-Charset", "utf-8");
        conn.setRequestProperty("contentType", "utf-8");

        fileName = substactFileName(conn.getURL().getPath());

        //得到输入流
        InputStreamReader inputStreamReader =  new InputStreamReader(conn.getInputStream(), "UTF-8");

        //获取自己数组
        byte[] getData = readInputStream(inputStreamReader);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStreamReader!=null){
            inputStreamReader.close();
        }

        System.out.println("info:"+url+" download success");
    }

    public static String substactFileName(String path) throws  Exception{
        StringBuffer s = new StringBuffer(new String(path.getBytes("ISO-8859-1"), "UTF-8"));
        s.delete(0,s.lastIndexOf("/") + 1);
        return s.toString();
    }


    /**
     * 从输入流中获取字节数组
     * @param inputStreamReader
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStreamReader inputStreamReader) throws IOException {
        byte[] buffer = new byte[1024];
        char[] c = new char[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStreamReader.read(c)) != -1) {
            buffer = new String(c).getBytes();
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    public static void main(String[] args) {
        try{
            downLoadFromUrl("https://api.ikmoe.com/moeu-rand-background.php", "","F:\\我的图片");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
