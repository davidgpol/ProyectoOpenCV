<%@ include file="init.jsp" %>
<div id = "VentanaAnadirImagen" class = "ventanaAnadirImagen">
	<form:form action="anadirImagenes" method="post" commandName = "formulario" enctype="multipart/form-data" style = "margin-left: 1.5em;">
		<label class = "textoFormulario"> Id grupo: </label> <br/>
		<form:input  path="idGrupo" class = "formularioAnadirImagen" name="idGrupo" type="text"></form:input>
		<form:errors path="idGrupo" class = "errorFormulario" name="idGrupo"></form:errors> <br>						
		<label class = "textoFormulario"> Nombre imagen: </label> <br/>
		<form:input  path="nombreImagen" class = "formularioAnadirImagen" name="nombreImagen" type="text"></form:input>
		<form:errors path="nombreImagen" class = "errorFormulario" name="nombreImagen"></form:errors> <br>
		<label class = "textoFormulario"> Imagen: </label> <br/>
		<form:input  path="imagenes" class = "formularioAnadirImagen" name="imagenes[0]" type = "file"></form:input>
		<form:errors path="imagenes" class = "errorFormulario" name="imagenes[0]"></form:errors> <br>
		<button type="submit" class = "botonEnviarFormulario">Submit</button>
	</form:form>					
</div>