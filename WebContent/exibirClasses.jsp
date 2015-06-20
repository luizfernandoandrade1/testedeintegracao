<%@page import="model.SingClasseIntegracao"%>
<%@page import="model.ClasseIntegracao"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EasyIntegrationTesting Tool</title>
<link href="css/style.css" rel="stylesheet" />
<script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.8.17.custom.min.js" type="text/javascript"></script>

<script>
function load() {
	
	$('#classeIntegrar li').click(function() {
		var nomeClasse = $(this).text().toString().trim();
		
		if(busca(nomeClasse)){			
			$("#nomeClasse").val(nomeClasse);
			$("#frm").submit();
		}else{
			alert("A classe '" + nomeClasse + "' não é a melhor opção nesse momento.");
		}
	});

	$('#tblClasses td').click(function(){
		var nomeClasse = $(this).attr('id');
		
		if(busca(nomeClasse)){			
			$("#nomeClasse").val(nomeClasse);
			$("#frm").submit();
		}else{
			alert("A classe '" + nomeClasse + "' não é a melhor opção nesse momento.");
		}
		
	})

	$('#melhorOpcao').click(function() {
		var ultimaColuna = $('#tblClasses tr').find('td:last').not($('.excluiClasse'));
		var primeiraColuna = $('#tblClasses tr').find('td:first').not($('.excluiClasse'));
		var valorMenorColuna = Number(ultimaColuna.eq(0).text());
		var valColuna;
		for (var i=0; i< ultimaColuna.length; i++) {
		    valColuna = Number(ultimaColuna.eq(i).text());

		    if (valColuna < valorMenorColuna) {
		        nomeClasse = primeiraColuna.eq(i).text().trim();
		        valorMenorColuna = valColuna;
		    }
		}
		$("#"+nomeClasse).css("background-color","#fff");
	});

		if($('#tblClasses tr').find('td:last').not($('.excluiClasse')).length <= 1){
			$("#melhorOpcao").css("display","none");
		}
		
	function busca(nome){
		var contLinha = $('#tblClasses tr').length -1;
		var contColuna = ($('#tblClasses td').length)/contLinha;
		var posicao = 0;
		for(var i = 0; i < contLinha; i++){
			var teste = $('#tblClasses td')[posicao].innerHTML;
			if($.trim(teste) === $.trim(nome)){
				if($.trim($('#tblClasses td')[posicao+(contColuna-1)].innerHTML) <= $("#minFit").val()){
					 return true;
				}
				else{
					return false;
				}
			}
			posicao += contColuna;
		}
		return false;
	}
	if(	$("#classeIntegrar li").length <=1){
		$("#melhorOpcao").css("display","none");
		
	}
	
}
</script>
</head>
<body onload="load();">
	<br>
	<div id="page">
		<div id="top">
			<div class="title">
			<div id="voltar"><input type="button" value="Novo Teste" onclick="window.location='index.jsp'"/></div>
			EasyIntegrationTesting Tool
			<!--  div id="pontuacao"><input type="button" value="Pontuacao" /></div> -->
			</div>
			<div id="topLeft">
			<%
			String srcImg = request.getAttribute("srcImg").toString();
			out.print("<img src="+srcImg+" />");
			%>
			
			</div>
			<div id="topRight">
				<div id="listaPai">
					<span>Classes a integrar</span>
					<ul id="classeIntegrar">
						<%
							Integer minFit = (Integer) request.getAttribute("minFit");
							ArrayList<ClasseIntegracao> lstParaIntegrar = (ArrayList) request
									.getAttribute("lstParaIntegrar");
							Integer sizeLstParaIntegrar = lstParaIntegrar.size();
							
							
								for (int i = 0; i < sizeLstParaIntegrar; i++) {
									if (lstParaIntegrar.get(i).getAtivo()) {
						%>
						<li id="<%out.print(lstParaIntegrar.get(i).getNome());%>"><label>
								<%
									out.print(lstParaIntegrar.get(i).getNome());
								%> </label>
						</li>
						<%
									}
								}
							
						%>
					</ul>
					<form action="ServeletXml" method="post" id="frm">
						<input type="hidden" name="nomeClasseIntegrar" id="nomeClasse" />
						<input type="hidden" name="minFit" value="<%out.print(minFit);%>"
							id="minFit" />

					</form>
				</div>
				<div id="listaFilha">
					<span>Classes já integradas</span>
					<ol id="classeIntegrada">
						<%
							ArrayList<ClasseIntegracao> lstIntegradas = (ArrayList) request
									.getAttribute("lstIntegradas");
							for (int i = 0; i < lstIntegradas.size(); i++) {
						%>
						<%
							out.print("<li id=" + lstIntegradas.get(i).getNome()
										+ "><label>");
								out.print(lstIntegradas.get(i).getNome());
								out.print("</label></li>");
						%>
						<%
							}
							
						%>
					</ol>
					<form method="post" id="frm">
						<input type="hidden" name="nomeClasseIntegrada" id="nomeClasse" />
					</form>
				</div>
			</div>
		</div>
		<div id="center">
			<div id="esconderTabelaClasse">
				<span>Tabela de Classe</span> <span onclick="esconde();">+</span>
			</div>
			<div id="tabelaClasses">
				<table id="tblClasses">
					<tr>
						<th>Classe</th>
						<th>FI</th>
						<%
							ArrayList<ClasseIntegracao> lstClasses = (ArrayList) request
									.getAttribute("lstClasses");
							int qtdFit = lstClasses.get(0).getFit().size();
							for (int i = 1; i <= qtdFit; i++) {
								out.println("<th>FIT" + i + "</th>");
							}
						%>

					</tr>

					<%
						for (int i = 0; i < lstClasses.size(); i++) {
							String style = "";
							if (!lstClasses.get(i).getAtivo()) {
								style = "class='excluiClasse'";

							}
							out.println("<tr> <td id='" + lstClasses.get(i).getNome() + "'"
									+ style + "> " + lstClasses.get(i).getNome() + " </td>");
							out.println("<td id='" + lstClasses.get(i).getNome() + "'"
									+ style + "> " + lstClasses.get(i).getFi() + " </td>");
							for (int j = 0; j < qtdFit; j++) {
								String fitAtual;
								if (lstClasses.get(i).getFit().get(j) == -1) {
									fitAtual = "-";
								} else {
									fitAtual = lstClasses.get(i).getFit().get(j).toString();
								}
								out.println("<td id='" + lstClasses.get(i).getNome() + "' "
										+ style + "> " + fitAtual + " </td>");
							}
							out.println("</tr>");
						}
					
						
					%>


				</table>
			</div>
		</div>
		<input type="button" id="melhorOpcao" value="E agora?" />
		
	</div>
</body>
</html>