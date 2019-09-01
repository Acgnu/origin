package com.acgnu.origin.notes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Part {
	public static void main(String[] args) {
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dbuilder = dfactory.newDocumentBuilder();
			Document document = dbuilder.parse("NewFile.xml");
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

}
