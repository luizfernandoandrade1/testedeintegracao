<%@page import="java.util.ArrayList"%>
<%@page import="BO.LeitorDiretorio"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/style.css" rel="stylesheet" />
<script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.8.17.custom.min.js" type="text/javascript"></script>
<title>EasyIntegrationTesting Tool</title>
</head>
<body>
<br>
	<div id="page">
		<div id="top">
			<div class="title">EasyIntegrationTesting Tool</div>
		</div>
		<div id="center">
			<div id="formUplod">
				<fieldset>
					
					<form action="ServeletXml" method="post"
						 name="form">
					<!-- 	<label>Selecione um diagrama XML</label> <input type="file"
							title="Selecione o XML." name="arquivoXML" accept="text/xml" />
						<br> <label>Selecione a imagem do Diagrama</label> <input
							type="file" title="Selecione a imagem." name="arquivoImg"
							accept="text/xml" /> <br /> -->
						<label>Arquivos disponibilizados:</label><br>
						<div id="lstXML">
							<%
								ArrayList<String> lstXml = LeitorDiretorio.dirlist();
								if(lstXml.size() == 0){
									out.print("Não há arquivos cadastrados");
								}else{
									out.print("<input type='radio' name=\"arquivo\" value='"+lstXml.get(0)+"' CHECKED/> <label>" + lstXml.get(0) + "</label><BR />");
									for (int i = 1; i < lstXml.size(); i++) {
										out.print("<input type='radio' name=\"arquivo\" value='"+lstXml.get(i)+"'/> <label>" + lstXml.get(i) + "</label><BR />");
									}
								}
							%>
							
						</div>
						<div id = "usarTabela">
							<input type="radio" name="tabela" checked="checked" Value="N">COM tabela Preenchida<br>
							<input type="radio" name="tabela"  value="S">SEM Tabela Preenchida
						</div> <br>
						<input id="ok" type="submit" name="Ok" value="Ok" />
					</form>
				</fieldset>
			</div>
		</div>
		<hr />
		<div id="footer">
		
		</div>
	</div>
	
</body>
</html>