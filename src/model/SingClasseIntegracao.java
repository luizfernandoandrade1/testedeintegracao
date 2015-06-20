package model;

import java.util.ArrayList;

import org.apache.catalina.tribes.membership.StaticMember;

public class SingClasseIntegracao {
	private static SingClasseIntegracao instance = new SingClasseIntegracao();
	private static ArrayList<ClasseIntegracao> lstClasseIntegracao = new ArrayList<ClasseIntegracao>();
	private static ArrayList<ClasseIntegracao> lstClassesParaIntegrar = new ArrayList<ClasseIntegracao>();
	private static ArrayList<ClasseIntegracao> lstClasseIntegradas = new ArrayList<ClasseIntegracao>();
	private static String srcImg = null;
	
	public static ArrayList<ClasseIntegracao> getLstClassesParaIntegrar() {
		return lstClassesParaIntegrar;
	}
	public static void setLstClassesParaIntegrar(
			ArrayList<ClasseIntegracao> lstClassesParaIntegrar) {
		SingClasseIntegracao.lstClassesParaIntegrar = lstClassesParaIntegrar;
	}
	public static ArrayList<ClasseIntegracao> getLstClasseIntegradas() {
		return lstClasseIntegradas;
	}
	public static void setLstClasseIntegradas(
			ClasseIntegracao lstClasseIntegradas) {
		SingClasseIntegracao.lstClasseIntegradas.add(lstClasseIntegradas);
	}
	
	
	public static ArrayList<ClasseIntegracao> getLstClasseIntegracao() {
		return lstClasseIntegracao;
	}
	public static void setLstClasseIntegracao(
			ArrayList<ClasseIntegracao> lstClasseIntegracao) {
		SingClasseIntegracao.lstClasseIntegracao = lstClasseIntegracao;
	}
	public static void setSrcImg(String src){
		srcImg = src;
	}
	public static String getSrcImg(){
		return srcImg;
	}
	public static void removeLstClasseIntegradas(int posClasse){
		lstClasseIntegradas.remove(posClasse);
	}
	public static void removeLstClasseParaIntegrar(int posClasse){
		lstClassesParaIntegrar.remove(posClasse);
	}
	
	public static void inicializar(){
		lstClasseIntegracao = new ArrayList<ClasseIntegracao>();
		lstClassesParaIntegrar = new ArrayList<ClasseIntegracao>();
		lstClasseIntegradas = new ArrayList<ClasseIntegracao>();
	}
}
