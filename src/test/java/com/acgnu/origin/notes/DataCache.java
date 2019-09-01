package com.acgnu.origin.notes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据缓存对象, 此处当数据库使用
 * @author Administrator
 */
public class DataCache {
	private Integer maxID = 0;	//学生信息最大ID (用于新增)
	private File dataFile = null;	//数据文件, 在系统初始化的时候赋值
	private List<Student> stuList = new ArrayList<Student>();	//读取到的学生信息
	private static DataCache instance;		//此对象实例
    private DataCache(){}
    public static DataCache getInstance(){
        if(instance==null){
            instance=new DataCache();
        }
        return instance;
    }
    
    /**
     * 初始化数据, 将json文件中的学生信息读取到此缓存中, 在系统启动的时候被调用
     * @param jsonArr
     */
    public void initCache(JSONArray jsonArr){
    	if(null == jsonArr || jsonArr.size() == 0){
    		return;
    	}
    	stuList.clear();
    	for(int i = 0; i < jsonArr.size(); i++){
    		Student stu = jsonArr.getJSONObject(i).toJavaObject(Student.class);
    		if(stu.getId() > maxID){
    			maxID = stu.getId();
    		}
    		stuList.add(stu);
    	}
    }
    
    /**
     * 得到所有学生信息
     * @return
     */
    public List<Student> getAll(){
    	return stuList;
    }
    
    /**
     * 将此对象中缓存的所有学生信息写入数据文件
     * @throws IOException
     */
    public void saveAll() throws IOException{
    	FileUtils.saveStringToFile(dataFile, JSON.toJSONString(stuList));
    }
    
    /**
     * 使用过滤器得到指定的学生信息
     * @param assignFilter 过滤器对象, 查询条件自己实现
     * @return 指定对象
     */
    public <T> T getAssign(AssignFilter<T> assignFilter){
    	return assignFilter.doFilter(stuList);
    }

    /**
     * 添加新的学生信息并保存文件
     * @param student 学生信息
     * @throws IOException
     */
	public void addNew(Student student) throws IOException {
		student.setId(++maxID);
		stuList.add(student);
		saveAll();
	}
	
    public void setFile(File file){
    	this.dataFile = file;
    }
}
