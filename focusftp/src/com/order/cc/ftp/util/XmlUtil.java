package com.order.cc.ftp.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Xml解析工具类
 * @author yxm
 * @date 2016-12-8
 *
 */
public class XmlUtil {
	
	private static Map<String,Map<String,String>> xmlMap = new HashMap<String,Map<String,String>>();
	/**
	 * 解析xml文件為map對象
	 * @return
	 */
	public static Map<String,Map<String,String>> parseXml(){
		//1.解析結果存放在map對象中
		Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
		//2.************dom4j解析文件************start
		try {
			//创建解析器
			SAXReader reader = new SAXReader();
			
			//通过解析器read方法，将我们要解析的xml文件读取到Document对象中
			Document document = reader.read("ftp.xml");
			
			//获取document对象的根节点
			Element root = document.getRootElement();
			
			//得到根節點下所有子節點
			List<Element> elementList = root.elements();
			
			//遍歷所有子節點
			for (Element e : elementList) {//FtpServer
				Map<String, String> temp = new HashMap<String,String>();
				List<Element> subElementList = e.elements();//获取所有子节点的子节点
				 for (Element sube : subElementList) {
					temp.put(sube.getName(), sube.getText());
				 }
				 map.put(temp.get("Depict"),temp);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
	 }
	return map;
} 
}
