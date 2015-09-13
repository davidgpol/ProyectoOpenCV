<%@ include file="init.jsp" %>
<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Panel de control</title>
		
		<link href="<c:url value="/resources/PanelControl.css" />" rel="stylesheet" type="text/css">
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<script type="text/javascript" src="<c:url value="/resources/PanelControl.js" />"> </script>
	</head>
	
<body>

<input type="hidden" id="NumeroFilas" value='${numeroFilas}'/>

	<div id = "ContenedorPrincipal" class = "contenedorPrincipal">
		<nav id = "Titulo" class = "titulo">
			<span id = "BarraUsuario" class = "barraUsuario">
				Panel de control del servidor
			</span>	
		</nav>
		<div id = "Contenido" class = "contenido">
			<nav id = "MenuBBDD" class = "menuImagenes">
				<ul class = "menulistaImagenes_filas">
					<li class = "menuListaImagenes_columnas">
						<a id ="consultarImagen"> Consultar imágenes </a>
					</li>				
					<li class = "menuListaImagenes_columnas">
						<a id ="anadirImagen"> Añadir imagen </a>
					</li>
					<li class = "menuListaImagenes_columnas">
						<a id ="modificacionPedido">Modificar imagen</a>
					</li>
					<li class = "menuListaImagenes_ultima_columna menuListaImagenes_columnas">
						<a id ="eliminarImagen">Eliminar imagen</a>
					</li>
				</ul>
			</nav>
			<jsp:include page="consultarImagen.jsp"/>
			<jsp:include page="anadirImagen.jsp"/>
			<jsp:include page="eliminarImagen.jsp"/>
			<div id = "menuConexiones" class = "menuConexiones">
			Prueba
			</div>
		</div>
	</div>
</body>
</html>