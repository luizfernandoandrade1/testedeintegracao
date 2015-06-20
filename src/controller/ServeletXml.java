package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ClasseIntegracao;
import model.SingClasseIntegracao;
import BO.LeitorDiretorio;
import BO.LeitorXml;

//import BO.Upload;

/**
 * Servlet implementation class ServeletXml
 */
@WebServlet("/ServeletXml")
public class ServeletXml extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<ClasseIntegracao> lstClasses;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServeletXml() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pagina = null;
		LeitorXml xml = null;
		
		

		/*
		 * if ((request.getContentType() == null) &&
		 * (request.getContentType().indexOf("multipart/form-data") < 0)) {
		 * 
		 * } else if (Upload.SubirArquivo(request.getContentType(), request)) {
		 */
		
		if (request.getParameter("nomeClasseIntegrar") != null
				&& !SingClasseIntegracao.getLstClasseIntegracao().isEmpty()) {
			int posClasseRemovida = -1;
			ClasseIntegracao classeIntegrada = null;

			String nomeClasse = request.getParameter("nomeClasseIntegrar");
			// recupera a lista de integração
			lstClasses = SingClasseIntegracao.getLstClasseIntegracao();
			xml = new LeitorXml(lstClasses);

			posClasseRemovida = xml.removerClasse(nomeClasse);
			if (posClasseRemovida > -1) {
				if (xml.atualizaFit()) {
					lstClasses = xml.getLstClasses();
					SingClasseIntegracao.setLstClasseIntegracao(lstClasses);

					classeIntegrada = lstClasses.get(posClasseRemovida);

					SingClasseIntegracao
							.setLstClasseIntegradas(classeIntegrada);

					// carregando a lstClasses as classes para integrar. Em
					// seguinda removemos a classe a ser removida
					lstClasses = SingClasseIntegracao
							.getLstClassesParaIntegrar();
					SingClasseIntegracao.getLstClassesParaIntegrar()
							.get(posClasseRemovida).setAtivo(false);
				}
			}
		} else if (request.getParameter("arquivo") != null) {

			String nomeArquivo = request.getParameter("arquivo");
			String img = "img/" + nomeArquivo.replace(".xml", ".jpg");

			
			xml = new LeitorXml(nomeArquivo, LeitorDiretorio.getCaminhoXml());

			lstClasses = xml.lerXML();
			
			SingClasseIntegracao.inicializar();
			
			String usatabela = request.getParameter("tabela");
			if(usatabela.equals("S")){
				String nomeClasse = null;
				do {
					nomeClasse = xml.classeMinFit();
					if(nomeClasse != null){
						xml.removerClasse(nomeClasse);
						xml.atualizaFit();
					}
				} while (xml.classeMinFit() != null);
				
				
			}else{
				
				
				SingClasseIntegracao.setLstClasseIntegracao(lstClasses);
				SingClasseIntegracao.setLstClassesParaIntegrar(lstClasses);
			}

			SingClasseIntegracao.setSrcImg(img);
			
		} else {
			pagina = "index.jsp";
		}

		if(pagina == null && SingClasseIntegracao.getLstClasseIntegracao().size() == 0){
			request.setAttribute("qtdClasse", xml.getLstClasses().size());
			request.setAttribute("jSon", xml.getJson());
			request.setAttribute("srcImg", SingClasseIntegracao.getSrcImg());
			pagina = "/exibirClassesStabela.jsp";
		}
		else {
			request.setAttribute("lstParaIntegrar",
					SingClasseIntegracao.getLstClassesParaIntegrar());
			request.setAttribute("lstIntegradas",
					SingClasseIntegracao.getLstClasseIntegradas());
			request.setAttribute("lstClasses",
					SingClasseIntegracao.getLstClasseIntegracao());
			request.setAttribute("minFit", xml.minFit());
			request.setAttribute("srcImg", SingClasseIntegracao.getSrcImg());
			
			pagina = "/exibirClasses.jsp";
			
		}
		
		
		  
		RequestDispatcher rd = request.getRequestDispatcher(pagina);
		rd.forward(request, response);

	}
}
