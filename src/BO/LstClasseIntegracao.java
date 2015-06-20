package BO;

import java.util.ArrayList;

import model.ClasseIntegracao;

public class LstClasseIntegracao {

	private static LstClasseIntegracao instance = new LstClasseIntegracao();
	private ArrayList<ClasseIntegracao> lstClasse = new ArrayList<ClasseIntegracao>();
	
	private LstClasseIntegracao() {
	}

	public static LstClasseIntegracao getInstance() {
		return instance;
	}

	public ArrayList<ClasseIntegracao> getLstClasse(){
		return lstClasse;
	}
	public void setLstClasse(ArrayList<ClasseIntegracao> lstClasse){
		this.lstClasse = lstClasse;		
	}
}
