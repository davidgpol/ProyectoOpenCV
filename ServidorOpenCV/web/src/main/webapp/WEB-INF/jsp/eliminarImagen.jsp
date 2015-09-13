<%@ include file="init.jsp" %>
<div id = "VentanaEliminarImagen" class = "ventanaAnadirImagen">
	<form:form action="eliminarImagenes" method="post" commandName = "formulario" style = "margin-left: 1.5em;">
		<label class = "textoFormulario"> Id imagen: </label> <br/>
		<form:input  path="idImagen" class = "formularioAnadirImagen" name="idImagen" type="text"></form:input> <br/>			
		<label class = "textoFormulario"> Id grupo: </label> <br/>
		<form:input  path="idGrupo" class = "formularioAnadirImagen" name="idGrupo" type="text"></form:input> <br/>
		<label class = "textoFormulario"> Nombre imagen: </label> <br/>
		<form:input  path="nombreImagen" class = "formularioAnadirImagen" name="nombreImagen" type="text"></form:input>
		<form:errors path="nombreImagen" class = "errorFormulario" name="nombreImagen"></form:errors> <br>
		<button type="submit" class = "botonEnviarFormulario">Submit</button>
		<form:errors path="imagenes" class = "errorFormulario" name="camposVacios"></form:errors> <br>
	</form:form>					
</div>	