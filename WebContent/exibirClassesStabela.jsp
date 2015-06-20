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
	Json = eval(<% out.print(request.getAttribute("jSon")); %>);
	eagora = false;
	pontuacao = 0;
	for(var i=0; i< Json.length; i++){
	    $("#classe"+i).html(Json[i].Classe)
	}

}
function retiraLetra(valor,id){
	$('#'+id).val(valor.replace(/\D/g, ''));
	
}

function validarFi(idFi, valor){
	if($.isNumeric(valor)){
		var pos = idFi.replace(/\D/g, '');
		if(valor === Json[pos].Fi){
			$('#FI'+pos).html(valor).css('color','green');
		}else{
			$('#'+idFi).css('color','red');
			pontuacao++;
		}
	}
}
function validarFit(posFi,posFit, valor){
	if($.isNumeric(valor)){
		if(valor === Json[posFi].Fit[0][posFit]){
			$('#FI'+posFi+"FIT"+posFit).html(valor).css('color','green');
		}else{
			$('#FI'+posFi+"FIT"+posFit+" input").css('color','red');
			$('#eAgora').css('display','block');
		}
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
			<div id="pontuacao"><input type="button" value="Quantidade de Erros" onclick=" alert('Até o momento você teve '+ pontuacao +' erros');"/></div>
			</div>
			<div id="topLeft">
			<%
			String srcImg = request.getAttribute("srcImg").toString();
			out.print("<img src="+srcImg+" />");
			%>
			
			</div>
			<div id="topRight">
				
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
							Integer qtdClasse = (Integer) request
									.getAttribute("qtdClasse");
							for (int i = 1; i <= qtdClasse; i++) {
								out.println("<th>FIT" + i + "</th>");
							}
						%>

					</tr>

					<%
						for (int i = 0; i < qtdClasse; i++) {
						out.println("<tr>");
							out.println("<td id='classe"+i+"'> </td>");
							out.println("<td id='FI"+i+"'><input type='text' id='fi"+ i +"'  size='2' maxlength='2' onblur='validarFi(this.id,this.value);' onkeyup='retiraLetra(this.value,this.id)'/>  </td>");
							for (int j = 1; j <= qtdClasse; j++) {	
								out.println("<td id='FI"+i+"FIT"+j+"'><input type='text' size='2' maxlength='2'  onblur='return validarFit("+i+","+j+",this.value);' onkeyup='retiraLetra(this.value,this.id)'/> </td>");
							}
						out.println("</tr>");
						}
					%>


				</table>
			</div>
		</div>
		<input type="button" id="eAgora" style="display: none;" value="E agora?" /> 
		
	</div>
</body>
</html>