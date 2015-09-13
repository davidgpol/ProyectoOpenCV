<%@ include file="init.jsp" %>
<div id = "VentanaConsultarImagen" class = "ventanaAnadirImagen">
	<form:form action="consultarImagenes" method="post" commandName = "formulario" style = "margin-left: 1.5em;">
		<label class = "textoFormulario"> Id imagen: </label> <br/>
		<form:input  path="idImagen" class = "formularioAnadirImagen" name="idImagen" type="text"></form:input> <br/>			
		<label class = "textoFormulario"> Id grupo: </label> <br/>
		<form:input  path="idGrupo" class = "formularioAnadirImagen" name="idGrupo" type="text"></form:input> <br/>
		<label class = "textoFormulario"> Nombre imagen: </label> <br/>
		<form:input  path="nombreImagen" class = "formularioAnadirImagen" name="nombreImagen" type="text"></form:input>
		<form:errors path="nombreImagen" class = "errorFormulario" name="nombreImagen"></form:errors> <br>
		<button id="BotonConsultarImagenes" type="submit" class = "botonEnviarFormulario">Submit</button>
		<form:errors path="imagenes" class = "errorFormulario" name="camposVacios"></form:errors> <br>
	</form:form>			
</div>

<div id = "ListaImagenes" class = "listaImagenes">
	<table class = "tablaImagenes">
			<tr class = "listaImagenes_filas">
				<td class = "listaImagenes_columnas listaImagenes_cabecera">Id Imagen</td>
				<td class = "listaImagenes_columnas listaImagenes_cabecera">Grupo Imagen</td>
				<td class = "listaImagenes_columnas listaImagenes_cabecera">Nombre Imagen</td>					
				<td class = "listaImagenes_columnas listaImagenes_cabecera">Imagen</td>
			</tr>	
		<c:forEach var="imagen" items="${listaImagenes}"> 
			<tr class = "listaImagenes_filas">
				<td class = "listaImagenes_columnas"><c:out value="${imagen.idImagen}"/></td>
				<td class = "listaImagenes_columnas"><c:out value="${imagen.grupoImagen}"/></td>
				<td class = "listaImagenes_columnas"><c:out value="${imagen.nombreImagen}"/></td>					
				<td class = "listaImagenes_columnas"><img class = "tamanoImagen" src="data:image/jpeg;base64,${imagen.base64Imagen}" /></td>
			</tr>
		</c:forEach>					
	</table>
</div>	