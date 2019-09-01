package com.acgnu.origin.notes;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 配置文件读取辅助
 * @author _ORIGINAL™
 */
public class PropReader {
	/**
	 * 读取Properties文件
	 * @param conf	文件名
	 * @return	封装参数的键值对集合
	 */
	public static Map<String, String> getPropMap(String conf){
		Map<String, String> m  = new Hashtable<String, String>();
		String projectPath = "/project/path";
		FileInputStream fis = null;
		Properties prop = null;
		try{
			prop =  new Properties();
			projectPath = URLDecoder.decode(projectPath, "utf-8");
			File confFile = new File(projectPath + File.separator + conf);
			fis = new FileInputStream(confFile);
			prop.load(fis);
			for(Entry<Object, Object> e : prop.entrySet()){
				m.put(e.getKey().toString(), e.getValue().toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return m;
	}
}
