package BO;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class Configuracao {
	public String caminhoXml(){
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse("xmlConfig.xml");
			doc.getDocumentElement().normalize();
			
				
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			
			return "sim";
		}
	
		
	}
}
