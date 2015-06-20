package BO;

import java.io.File;
import java.util.ArrayList;

public class LeitorDiretorio {

	private static LeitorDiretorio instance = new LeitorDiretorio();
	
	
	private static String caminhoXML = "D:\\Projetos\\tccgraduacao\\WebContent\\xml\\"; 
	
	 
	public static ArrayList<String> dirlist() {
		ArrayList<String> nomeArquivo = new ArrayList<String>();
		File dir = new File(caminhoXML);
		String[] arq = dir.list();
		if (arq != null){ 
			for (int i = 0; i < arq.length; i++) {
				if (arq[i].endsWith(".xml")) {
					String fileName = arq[i];
					nomeArquivo.add(fileName);
				}
			}
		}
		return nomeArquivo;
	}
	public static String getCaminhoXml(){
		return caminhoXML;
	}

}
