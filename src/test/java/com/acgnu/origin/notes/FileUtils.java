package com.acgnu.origin.notes;

import org.apache.commons.io.IOUtils;

import java.io.*;


/**
 * 文件处理工具
 * @author Administrator
 */
public class FileUtils {
	public static final String CHARSET_UTF_8 = "UTF-8";
	
	/**
	 * 从文件读取信息
	 * @param toRead
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(File toRead) throws IOException {
		 InputStream in = null;  
         try {  
             in = openInputStream(toRead);  
             return IOUtils.toString(in, CHARSET_UTF_8);  
         } finally {  
             IOUtils.closeQuietly(in);  
         }  
	}
	
	/**
	 * 打开输入流
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileInputStream openInputStream(File file) throws IOException {  
        if (file.exists()) {  
            if (file.isDirectory()) {  
                throw new IOException("File '" + file + "' exists but is adirectory");  
            }  
            if (file.canRead() == false) {  
                throw new IOException("File '" + file + "' cannot be read");  
            }  
        } else {  
            throw new FileNotFoundException("File '" + file + "' does notexist");  
        }  
        return new FileInputStream(file);  
    }

	/**
	 * 保存信息到文件
	 * @param file
	 * @param data
	 * @throws IOException
	 */
	public static void saveStringToFile(File file, String data) throws IOException  {
		 OutputStream out = null;  
         try {  
             out = openOutputStream(file);  
             IOUtils.write(data, out, CHARSET_UTF_8);  
         } finally {  
             IOUtils.closeQuietly(out);  
         }  
	}  
	
	/**
	 * 打开输出流
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static FileOutputStream openOutputStream(File file) throws IOException {  
        if (file.exists()) {  
            if (file.isDirectory()) {  
                throw new IOException("File'" + file + "' exists but is a directory");  
            }  
            if (file.canWrite() == false) {  
                throw new IOException("File '" + file + "' cannot be written to");  
            }  
        } else {  
            File parent = file.getParentFile();  
            if (parent != null &&parent.exists() == false) {  
                if (parent.mkdirs() ==false) {  
                    throw new IOException("File '" + file + "' could not be created");  
                }  
            }  
        }  
        return new FileOutputStream(file);  
    }
}
