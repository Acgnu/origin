package com.acgnu.origin;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

public class CommonUnitTest {
    public void testMongo() {
        try {
            MongoClient mongoClient = MongoClients.create();
            MongoDatabase testDB = mongoClient.getDatabase("test");
            MongoCollection collection = testDB.getCollection("table");

            Document row = new Document("k1", "v1");
            row.append("k1", "v1-1").append("k2", "v2");
            collection.insertOne(row);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testSocket(){
        //创建一个流套接字并将其连接到指定主机上的指定端口号
        try(Socket socket = new Socket("192.168.1.101", 50003)) {
            //读取服务器端数据
            DataInputStream input = new DataInputStream(socket.getInputStream());
            //向服务器端发送数据
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.print("请输入: \t");
            String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
            out.writeUTF(str);

            String ret = input.readUTF();
            System.out.println("服务器端返回过来的是: " + ret);
            // 如接收到 "OK" 则断开连接
            if ("OK".equals(ret)) {
                System.out.println("客户端将关闭连接");
                Thread.sleep(500);
            }
            out.close();
            input.close();
        } catch (Exception e) {
            System.out.println("客户端异常:" + e.getMessage());
        }
    }
    public void testRunCmd(){
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            Process process = Runtime.getRuntime().exec("cmd.exe");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
            process.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testOriginXmlParse(){
        DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dbuilder = dfactory.newDocumentBuilder();
            org.w3c.dom.Document document = dbuilder.parse("NewFile.xml");
            Element element = document.getDocumentElement();
            System.out.println(element);
            NodeList nodelist = element.getChildNodes();

            for(int i = 0; i < nodelist.getLength(); i++)
            {
                Node node = nodelist.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    NodeList nodelist2 = node.getChildNodes();
                    for(int j = 0; j < nodelist2.getLength(); j++)
                    {
                        Node node2 = nodelist2.item(j);
                        if(node2.getNodeType() == Node.ELEMENT_NODE)
                        {
                            //以下两句只是为了显示当前节点的id值，不影响最中节点值的读取
                            //创建新的元素对象element2，用于存储node2节点对象
                            Element element2 = (Element)node2;
                            //通过element元素的方法getAttrubute()来获得element2也就是node2节点的id值
                            System.out.println("id:" + element2.getAttribute("id"));
                            //这里用于打印最内部节点对象里的值
                            //for循环里，Node g表示新建节点对象，从node2里的第一个元素开始接收
                            //在当前的NewFile代码中，第一个元素是没有内容但不为null的，只有这个节点里
                            //最后一个元素后没有元素的时候才是null
                            for(Node g = node2.getFirstChild(); g != null; g = g.getNextSibling())
                            {
                                //这里判断最里层元素是否为正常可使用的节点，是的话就打印
                                if(g.getNodeType() == Node.ELEMENT_NODE)
                                {
                                    //NodeNam即为标签名字，后面的取值方式虽然写法比较特殊，但只是取标签里的值而已
                                    System.out.println(g.getNodeName() + ":" + g.getFirstChild().getNodeValue());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 读取Properties文件
     * @return	封装参数的键值对集合
     */
    public void testReadProperties(){
        String conf = "";
        Map<String, String> m  = new Hashtable<>();
        String projectPath = "/project/path";
        FileInputStream fis;
        Properties prop;
        try{
            prop =  new Properties();
            projectPath = URLDecoder.decode(projectPath, StandardCharsets.UTF_8);
            File confFile = new File(projectPath + File.separator + conf);
            fis = new FileInputStream(confFile);
            prop.load(fis);
            for(Map.Entry<Object, Object> e : prop.entrySet()){
                m.put(e.getKey().toString(), e.getValue().toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
