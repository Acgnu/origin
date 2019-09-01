package com.acgnu.origin.notes;

//import jxl.Workbook;
//import jxl.format.Alignment;
//import jxl.format.VerticalAlignment;
//import jxl.write.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by zjh on 2016/10/29.
 */
public class ExportExcel {
//
//    /**
//     *
//     * @param map 前端参数-表头（列名）
//     * @param list 导出的list<Map<String,String>>
//     * @param excelName Excel表名
//     * @param response
//     * @throws Exception
//     */
//    public static void excelWrite(Map<String,String> map, List<Map<String, String>> list,
//                                  String excelName,HttpServletRequest request, HttpServletResponse response) throws Exception
//    {
//        OutputStream out=response.getOutputStream();
//        String fileName = excelName;
//        response.reset();//清空输出流
//
//        response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
//        fileName = java.net.URLEncoder.encode(fileName,"UTF-8");
//        if ("FF".equals(getBrowserType(request))) {
//            // 针对火狐浏览器处理方式不一样了
//            fileName = new String(excelName.getBytes("UTF-8"),
//                    "iso-8859-1");
//        }
//        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xls");
//        response.setContentType("application/ms-excel");//定义输出类型
//        String json = map.get("columns");
//        ArrayList rows = (ArrayList)CommonUtils.Decode(json);
//        try {
//            WritableCellFormat headerFormat = new WritableCellFormat();
//            //水平居中对齐
//            headerFormat.setAlignment(Alignment.CENTRE);
//            //竖直方向居中对齐
//            headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//            // 获得开始时间
//            // long start = System.currentTimeMillis();
//            // 创建Excel工作薄
//            WritableWorkbook workbook = Workbook.createWorkbook(out);
//            // 添加第一个工作表并设置第一个Sheet的名字
//            WritableSheet sheet = workbook.createSheet(excelName, 0);
//            //sheet.setColumnView(0,20);//列宽
//            Label label;
//            //写出列名
//            for(int i=0;i<rows.size();i++){
//                HashMap hm = (HashMap)rows.get(i);
//                Iterator iterator = hm.keySet().iterator();
//                String columnName = hm.get("header").toString();//列名
//                label = new Label(i,0,columnName,headerFormat);
//                sheet.addCell(label);
//                sheet.setColumnView(i,columnName.getBytes().length + 4);//设置列宽
//            }
//            //写出数据
//            //List<Object> list = Search(map, response);
//            for (int i = 1; i <=list.size(); i++) {
//                //Map hm1 = CommonUtils.beanToMap(list.get(i-1));
//                Map<String, String> hm1 = list.get(i - 1);
//                for (int k = 1; k <=hm1.size(); k++) {
//                    for(int j=0;j<rows.size();j++){
//                        HashMap hm = (HashMap)rows.get(j);
//                        String key = hm.get("field").toString();
//                        String value=String.valueOf(hm1.get(key));
//                        if(hm1.get(key) == null || "null".equals(value)){
//                            value = "";
//                        }
//                        label = new Label(j,i,value,headerFormat);
//                        sheet.addCell(label);
//                        //sheet.setColumnView(i, value.getBytes().length + 4);
//
////                        if("avableQty".equals(key)){
////                            step.1 download file from file services
////                            step.2 convert file to .png
////                            step.3 add file to sheet
////                            WritableImage ri=new WritableImage(j,i,1,1,new File("C:\\Users\\NIK\\Desktop\\mopher\\QQ图片20170320100750.png"));
////                            sheet.addImage(ri);
////                            sheet.getCell(j, i);
////                            sheet.setRowView(i, );
////                        }
//                    }
//                }
//            }
//;
//            // 写入数据
//            workbook.write();
//            // 关闭文件
//            workbook.close();
//            out.close();
//            System.out.println("download complate");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("download failue");
//        }
//    }
//
//    /**
//     *
//     * @param headerMap 前端参数-表头（列名）多个表列明集合
//     * @param dataMap 多个表数据集合
//     * @param excelName Excel表名
//     * @param response
//     * @throws Exception
//     */
//    /*headerMap:
//        {
//            a:{},
//            b:{},
//            c:{}
//        }
//        dataMap:
//        {
//            a:[{},{},{}],
//            b:[{},{},{}],
//            c:[{},{},{}]
//        }*/
//
//    public static void excelListWrite(Map<String,Object> headerMap, Map<String,Object> dataMap, String excelName,HttpServletRequest request,
//                                      HttpServletResponse response) throws Exception{
//
//        OutputStream out=response.getOutputStream();
//        String fname = excelName;
//        response.reset();//清空输出流
//
//        response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
//        fname = java.net.URLEncoder.encode(fname,"UTF-8");
//        if ("FF".equals(getBrowserType(request))) {
//            // 针对火狐浏览器处理方式
//            fname = new String(excelName.getBytes("UTF-8"),
//                    "iso-8859-1");
//        }
//        response.setHeader("Content-Disposition","attachment;filename=" + fname + ".xls");
//        response.setContentType("application/ms-excel");//定义输出类型
//        try{
//                WritableCellFormat headerFormat = new WritableCellFormat();
//                //水平居中对齐
//                headerFormat.setAlignment(Alignment.CENTRE);
//                //竖直方向居中对齐
//                headerFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//
//                WritableWorkbook workbook = Workbook.createWorkbook(out);
//                Iterator it = headerMap.keySet().iterator();
//                int number=0;
//                while(it.hasNext()){
//                    String key1=it.next().toString();
//                    Map<String,Object> map = (Map<String,Object>)headerMap.get(key1);
//                    String json = map.get("columns").toString();
//                    ArrayList rows = (ArrayList)CommonUtils.Decode(json);
//                    String name = map.get("name").toString();
//                    WritableSheet sheet = workbook.createSheet(name, number);
//                    number++;
//                    //sheet.setColumnView(0,20);//列宽
//                    Label label;
//                    for(int i=0;i<rows.size();i++){
//                        HashMap hm = (HashMap)rows.get(i);
//                        String columnName = hm.get("header").toString();
//                        label = new Label(i,0,columnName,headerFormat);
//                        sheet.addCell(label);
//                        sheet.setColumnView(i,columnName.getBytes().length + 4);//设置列宽
//                    }
//                    List<Map<String,Object>> list = (List<Map<String,Object>>)dataMap.get(key1);
//
//                    for (int i = 1; i <=list.size(); i++) {
//                        //Map hm1 = CommonUtils.beanToMap(list.get(i-1));
//                        Map<String, Object> hm1 = list.get(i - 1);
//                        for (int k = 1; k <=hm1.size(); k++) {
//                            for(int j=0;j<rows.size();j++){
//                                HashMap hm = (HashMap)rows.get(j);
//                                String key =hm.get("field").toString();
//                                String value=String.valueOf(hm1.get(key));
//                                if(hm1.get(key) == null){
//                                    value = "";
//                                }
//                                label = new Label(j,i,value,headerFormat);
//                                sheet.addCell(label);
//                            }
//                        }
//                    }
//                }
//                // 写入数据
//                workbook.write();
//                // 关闭文件
//                workbook.close();
//                out.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    // 以下为服务器端判断客户端浏览器类型的方法
//    private static String getBrowserType(HttpServletRequest request) {
//        String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
//        if (UserAgent != null) {
//            if (UserAgent.indexOf("msie") >= 0)
//                return "IE";
//            if (UserAgent.indexOf("firefox") >= 0)
//                return "FF";
//            if (UserAgent.indexOf("safari") >= 0)
//                return "SF";
//        }
//        return null;
//    }
}
