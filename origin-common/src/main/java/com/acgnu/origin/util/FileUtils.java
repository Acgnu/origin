package com.acgnu.origin.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 文件处理工具
 * @author Administrator
 */
@Slf4j
public class FileUtils {

    /**
     * 从文件读取信息
     * @param toRead
     * @return
     * @throws IOException
     */
    public static String readFileToString(File toRead) throws IOException {
        try (InputStream in = openInputStream(toRead)) {
            return IOUtils.toString(in, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
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
            if (!file.canRead()) {
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
    public static void saveStringToFile(File file, String data) throws IOException {
        try (OutputStream out = openOutputStream(file)) {
            IOUtils.write(data, out, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 打开输出流
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static FileOutputStream openOutputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File'" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                if (!parent.mkdirs()) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file);
    }
}
