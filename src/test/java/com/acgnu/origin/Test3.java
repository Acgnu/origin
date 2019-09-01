package com.acgnu.origin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by _ORIGINAL™ on 2017/8/2.
 */
public class Test3 {
    public static void main(String args[]) {
        try {
            String line = null;
            StringBuilder sb = new StringBuilder();
            try {
                Process process = Runtime.getRuntime().exec("cmd.exe");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                System.out.println(sb.toString());
                process.waitFor();
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
