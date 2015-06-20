package BO;

import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.ClasseIntegracao;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LeitorXml {
	private ArrayList<ClasseIntegracao> lstClasses = new ArrayList<ClasseIntegracao>();
	private String arquivo;
	
	public LeitorXml() {
		this.arquivo = LeitorDiretorio.getCaminhoXml() + "TesteApostila.xml";
	}

	public LeitorXml(String arquivo, String caminho) {
		this.arquivo = caminho + arquivo;
	}

	public LeitorXml(ArrayList<ClasseIntegracao> lstClasse) {
		this.lstClasses = lstClasse;
	}

	public ArrayList<ClasseIntegracao> lerXML() {
		// public Boolean leXML() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(arquivo);
			doc.getDocumentElement().normalize();
			NodeList tagDependencia;
			Node no = null;

			// Nós UML:Class
			NodeList tagClasse = doc.getElementsByTagName("UML:Class");
			for (int i = 0; i < tagClasse.getLength(); i++) {
				no = tagClasse.item(i);
				lstClasses.add(new ClasseIntegracao(no.getAttributes()
						.getNamedItem("name").getNodeValue().toString(), no
						.getAttributes().getNamedItem("xmi.id").getNodeValue()
						.toString()));
			}

			// Nós UML:Generalization
			tagDependencia = doc.getElementsByTagName("UML:Generalization");
			setLstClasse("child", "parent", tagDependencia);

			// Nós UML:Dependency
			tagDependencia = doc.getElementsByTagName("UML:Dependency");
			setLstClasse("client", "supplier", tagDependencia);

			// Nós UML:AssociationEnd
			NodeList tagDependency = doc
					.getElementsByTagName("UML:AssociationEnd");
			setLstClasse(tagDependency);

			atualizaFi();
			atualizaFit();

			return lstClasses;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void atualizaFi() {
		for (int i = 0; i < lstClasses.size(); i++) {
			lstClasses.get(i).setFi();
		}
	}

	public boolean atualizaFit() {
		try {
			if (isClasseAtiva()) {
				for (int i = 0; i < lstClasses.size(); i++) {
					lstClasses.get(i).setFit();
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	public boolean isClasseAtiva(){
		for (int i = 0; i < lstClasses.size(); i++) {
			if(lstClasses.get(i).getAtivo())
				return true;
		}
		return false;
	}

	private void setLstClasse(String paramClasseUsa, String paramClasseUsada,
			NodeList lstTag) {
		int posClasseUsa = 0;
		int posClasseUsada = 0;
		String nomeClasseUsa = null;
		String nomeClasseUsada = null;
		Node no = null;

		for (int i = 0; i < lstTag.getLength(); i++) {
			no = lstTag.item(i);
			nomeClasseUsa = no.getAttributes().getNamedItem(paramClasseUsa)
					.getNodeValue().toString();
			nomeClasseUsada = no.getAttributes().getNamedItem(paramClasseUsada)
					.getNodeValue().toString();

			for (int j = 0; j < lstClasses.size(); j++) {
				if (lstClasses.get(j).getId().equals(nomeClasseUsa)) {
					posClasseUsa = j;
				} else if (lstClasses.get(j).getId().equals(nomeClasseUsada)) {
					posClasseUsada = j;
				}
			}
			// inserir nas dependencias
			lstClasses.get(posClasseUsa).setLstDependencias(
					lstClasses.get(posClasseUsada));

			// inserir nos dependentes
			lstClasses.get(posClasseUsada).setLstDosDependentes(
					lstClasses.get(posClasseUsa));
		}
	}

	private void setLstClasse(NodeList lstTag) {
		int posClasseUsa = 0;
		int posClasseUsada = 0;
		String nomeClasseUsa = null;
		String nomeClasseUsada = null;
		Node no = null;
		Boolean achou;
		String Associacao = "";
		for (int i = 0; i < lstTag.getLength(); i++) {
			no = lstTag.item(i);
			if (no.getAttributes().getNamedItem("aggregation").getNodeValue()
					.toString().equals("none")) {
				nomeClasseUsada = no.getAttributes().getNamedItem("type")
						.getNodeValue().toString();
			}
			Associacao = no.getAttributes().getNamedItem("association")
					.getNodeValue().toString();
			achou = false;
			while (!achou) {
				i++;
				no = lstTag.item(i);
				if (no.getAttributes().getNamedItem("association")
						.getNodeValue().toString().equals(Associacao)) {
					// Acontece quando é uma associação direcional
					if (no.getAttributes().getNamedItem("aggregation")
							.getNodeValue().toString().equals("none")) {
						nomeClasseUsa = nomeClasseUsada;
						nomeClasseUsada = no.getAttributes()
								.getNamedItem("type").getNodeValue().toString();

					} else {
						nomeClasseUsa = no.getAttributes().getNamedItem("type")
								.getNodeValue().toString();
					}

					achou = true;
				}
			}
			for (int j = 0; j < lstClasses.size(); j++) {
				if (lstClasses.get(j).getId().equals(nomeClasseUsa)) {
					posClasseUsa = j;
				} else if (lstClasses.get(j).getId().equals(nomeClasseUsada)) {
					posClasseUsada = j;
				}
			}
			// inserir nas dependencias
			lstClasses.get(posClasseUsa).setLstDependencias(
					lstClasses.get(posClasseUsada));

			// inserir nos dependentes
			lstClasses.get(posClasseUsada).setLstDosDependentes(
					lstClasses.get(posClasseUsa));

		}
	}


	public Integer removerClasse(String nomeClasse) {
		int posClasse = -1;
		for (int i = 0; i < lstClasses.size(); i++) {
			if (lstClasses.get(i).getNome().equals(nomeClasse)) {
				if (lstClasses.get(i).getAtivo()) {
					if (lstClasses.get(i).ultimoFit() <= minFit()) {
						posClasse = i;
						lstClasses.get(posClasse).setAtivo(false);
					}
				}
				break;
			}
		}
		return posClasse;

	}

	public ArrayList<ClasseIntegracao> getLstClasses() {
		return this.lstClasses;
	}

	public int minFit() {
		ArrayList<Integer> fit = new ArrayList<Integer>();
		int sizeFit = lstClasses.get(0).getFit().size() - 1;
		for (int i = 0; i < lstClasses.size(); i++) {
			if (lstClasses.get(i).getFit().get(sizeFit) > -1)
				fit.add(lstClasses.get(i).getFit().get(sizeFit));
		}

		return Collections.min(fit);
	}
	public String classeMinFit() {
		int minFit = minFit();
		int posMaiorFit = lstClasses.get(0).getFit().size()-1;
		String nomeClasse = null;
		for (int i = 0; i < lstClasses.size(); i++) {
			if(lstClasses.get(i).getFit().get(posMaiorFit) == minFit && lstClasses.get(i).getAtivo()){
				nomeClasse =  lstClasses.get(i).getNome();
				break;
			}
		}
		return nomeClasse;
	}
	public String getJson() {
		String jSon="";
		for (int i = 0; i < lstClasses.size(); i++) {
			jSon = jSon + "{Classe: '" + lstClasses.get(i).getNome()+"', ";
			jSon = jSon + "Fi: '" + lstClasses.get(i).getFi()+"', ";
			jSon = jSon + "Fit:[{";
			for (int x = 0; x < lstClasses.get(i).getFit().size(); x++) {
				jSon = jSon + (x+1) +": '" + lstClasses.get(i).getFit().get(x)+"', ";
				
			}
			jSon = jSon.substring(0,jSon.length()-2);
			jSon = jSon + "}]},";
		}
		jSon = jSon.substring(0,jSon.length()-1);
		return "["+ jSon + "]";
		
		//{CLASSE: 'FDFD' , FI: 'FFF' , BLBL: 'AAA'}
	}
	
}
