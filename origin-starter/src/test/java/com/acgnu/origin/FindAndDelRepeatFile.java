package com.acgnu.origin;//
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.*;

public class FindAndDelRepeatFile {

    private FindAndDelRepeatFile() {

    }

    private static char[] hexChar = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static String getHash(String fileName, String hashType) throws IOException, NoSuchAlgorithmException {

        File f = new File(fileName);
////        System.out.println(" -------------------------------------------------------------------------------");
////        System.out.println("|当前文件名称:" + f.getName());
////        System.out.println("|当前文件大小:" + (f.length() / 1024 / 1024) + "MB");
////        System.out.println("|当前文件路径[绝对]:" + f.getAbsolutePath());
////        System.out.println("|当前文件路径[---]:" + f.getCanonicalPath());
////        System.out.println(" -------------------------------------------------------------------------------");

        InputStream ins = new FileInputStream(f);

        byte[] buffer = new byte[8192];
        MessageDigest md5 = MessageDigest.getInstance(hashType);

        int len;
        while ((len = ins.read(buffer)) != -1) {
            md5.update(buffer, 0, len);
        }

        ins.close();
////        也可以用apache自带的计算MD5方法
        return DigestUtils.md5DigestAsHex(md5.digest());
////        自己写的转计算MD5方法
////        return toHexString(md5.digest());
    }

    public static String getHash2(String fileName) {
        File f = new File(fileName);
        return String.valueOf(f.lastModified());
    }


    protected static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte value : b) {
            sb.append(hexChar[(value & 0xf0) >>> 4]);
            sb.append(hexChar[value & 0x0f]);
        }
        return sb.toString();
    }

    /*
获取MessageDigest支持几种加密算法
**/
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static String[] getCryptolmpls(String serviceType) {
        Set result = new HashSet();
////        all providers
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            //            get services provided by each provider
            Set keys = provider.keySet();
            for (Object o : keys) {
                String key = o.toString();
                key = key.split(" ")[0];

                if (key.startsWith(serviceType + ".")) {
                    result.add(key.substring(serviceType.length() + 1));
                } else if (key.startsWith("Alg.Alias." + serviceType + ".")) {
                    result.add(key.substring(serviceType.length() + 11));
                }
            }
        }
        return (String[]) result.toArray(new String[0]);
    }


    public static void main(String[] args) throws Exception {
////        调用方法
////        String[] names = getCryptolmpls("MessageDigest");
//                for(String name:names){
//                    System.out.println(name);
//                }
        long start = System.currentTimeMillis();
        System.out.println("开始计算文件MD5值,请稍后...");
        File file = new File("F:\\我的图片");

        List<fc> fileLi = new ArrayList<>();
        List<fc> repeatLi = new ArrayList<>();

        findRepeatFile(file, fileLi, repeatLi);
////        System.out.println("重复的项");
////        for(fc c : repeatLi){
////            System.out.println(c.getF().getName());
////        }
//
////         String fileName = "F:\\我的视频\\玩伴猫耳娘\\[SumiSora][Asoiku][BDRip][01][GB][x264_aac][720P].mp4";
//// ////        String fileName = "E:\\SoTowerStudio-3.1.0.exe";
////         String hashType = "MD5";
////         String hash = getHash(fileName,hashType);
////         System.out.println("MD5:"+hash);
////         long end = System.currentTimeMillis();
////         System.out.println("一共耗时:"+(end-start)+"毫秒");
////            }
    }

    private static void findRepeatFile(File f, List<fc> fcLi, List<fc> repeatLi) throws IOException, NoSuchAlgorithmException {
        if(f.isDirectory()){
            File[] subFiles = f.listFiles();
            for(File subFile : subFiles){
                if(subFile.isDirectory()){
                    findRepeatFile(subFile, fcLi, repeatLi);
                }else {
                    String md5 = getHash(subFile.getAbsolutePath(), "MD5");
                    String p = compare(md5, fcLi);
                    if(p != null){
                        System.out.println(p);
                        System.out.println(subFile.getAbsolutePath());
                        System.out.println("------------\n");
////                        subFile.delete();
////                        repeatLi.add(new fc(md5, subFile));
                    }else {
                        fcLi.add(new fc(md5, subFile));
                    }
                }
            }
        }
    }

    private static String compare(String md5, List<fc> fcLi) throws IOException, NoSuchAlgorithmException {
        for(fc c : fcLi){
            if(c.getMd5().equals(md5))
                return c.getF().getAbsolutePath();
        }
        return null;
    }

    private static void getAllFile(File f, List<File> fl, List<File> rpf, String parent){
        if(f.isDirectory()){
            File[] fns = f.listFiles();
            for(File fn : fns){
                if(fn.isDirectory()){
                    getAllFile(fn, fl, rpf, parent);
                }
                else if(hasRepeat2(fl, fn)){
                    if(fn.getParent().endsWith(parent)){
                        System.out.println(fn.getParent());
                        fn.delete();
                    }else{
                        delOutAddIn(fl, fn);
                    }
////                    rpf.add(fn);
                }else{
                    fl.add(fn);
                }
            }
        }
    }

    /**
     * 删掉外面的
     * @param fl
     * @param fn
     */
    private static void delOutAddIn(List<File> fl, File fn) {
        for(File f : fl){
            if(f.getName().equals(fn.getName())){
                System.out.println(f.getName());
                f.delete();
            }
        }
    }

    private static boolean hasRepeat2(List<File> l, File s){
        for(File ss : l){
            if(ss.getName().equals(s.getName()))
                return true;
        }
        return false;
    }

    static class fc{
        File f;
        String md5;

        public fc(String md5, File f) {
            this.md5 = md5;
            this.f = f;
        }

        public File getF() {
            return f;
        }

        public String getMd5() {
            return md5;
        }
    }
}