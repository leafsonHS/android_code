package com.leafson.fuzhou.version;


import java.io.InputStream; 
import java.util.HashMap; 
 
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
 
import org.w3c.dom.Document; 
import org.w3c.dom.Element; 
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList; 
 
 
public class ParseTravelXmlService 
{ 
    public HashMap<String, String> parseXml(InputStream inStream) throws Exception 
    { 
        HashMap<String, String> hashMap = new HashMap<String, String>(); 
         
        // ʵ����һ���ĵ����������� 
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
        // ͨ���ĵ�������������ȡһ���ĵ������� 
        DocumentBuilder builder = factory.newDocumentBuilder(); 
        // ͨ���ĵ�ͨ���ĵ�����������һ���ĵ�ʵ�� 
        Document document = builder.parse(inStream); 
        //��ȡXML�ļ����ڵ� 
        Element root = document.getDocumentElement(); 
        //��������ӽڵ� 
        NodeList childNodes = root.getElementsByTagName("results");
        
        for (int j = 0; j < childNodes.getLength(); j++) 
        { 
            //�����ӽڵ� 
            Node childNode = (Node) childNodes.item(j); 
            
            if (childNode.getNodeType() == Node.ELEMENT_NODE) 
            {
            	
                NodeList itemNodes = childNode.getChildNodes();
                for (int i = 0; i < itemNodes.getLength(); i++) 
                { 
                	Node itemNode = (Node) itemNodes.item(j); 
	                Element childElement = (Element) itemNode; 
	                //�汾�� 
	                if ("name".equals(childElement.getNodeName())) 
	                { 
	                    hashMap.put("name",childElement.getFirstChild().getNodeValue()); 
	                } 
	                
	                
	                //������� 
	                else if (("address".equals(childElement.getNodeName()))) 
	                { 
	                    hashMap.put("address",childElement.getFirstChild().getNodeValue()); 
	                } 
	                //���ص�ַ 
	                else if (("telephone".equals(childElement.getNodeName()))) 
	                { 
	                    hashMap.put("telephone",childElement.getFirstChild().getNodeValue()); 
	                } 
	              //���ص�ַ 
	                else if (("tag".equals(childElement.getNodeName()))) 
	                { 
	                    hashMap.put("tag",childElement.getFirstChild().getNodeValue()); 
	                } 
                }
            } 
        } 
        return hashMap; 
    } 
}