function gestionOperaciones(operacion) {
	
	$( "#ListaImagenes" ).hide();
	
	if('A' === operacion) {
		$( "#VentanaConsultarImagen" ).hide();
		$( "#VentanaEliminarImagen" ).hide();			
		$( "#VentanaAnadirImagen" ).show();
	}
	if('E' === operacion) {
		$( "#VentanaConsultarImagen" ).hide();
		$( "#VentanaAnadirImagen" ).hide();
		$( "#VentanaEliminarImagen" ).show();
	}
	if('C' === operacion) {			
		$( "#VentanaAnadirImagen" ).hide();
		$( "#VentanaEliminarImagen" ).hide();
		if('CO' !== sessionStorage.getItem("consultado"))
			$( "#VentanaConsultarImagen" ).show();
		else {
			$( "#VentanaConsultarImagen" ).hide();
			sessionStorage.setItem("consultado", '');
			$( "#ListaImagenes" ).show();
		}		
	}
	if('D' === operacion) {
		$( "#VentanaAnadirImagen" ).hide();
		$( "#VentanaEliminarImagen" ).hide();
		$( "#VentanaConsultarImagen" ).show();		
		$( "#ListaImagenes" ).hide();
	}
	
	sessionStorage.setItem("numeroFilas", $('#NumeroFilas').val());
	sessionStorage.setItem("operacionSeleccionada", operacion);
}

$(document).ready(function(){

	numeroFilas 			= sessionStorage.getItem("numeroFilas");
	operacionSeleccionada	= sessionStorage.getItem("operacionSeleccionada");
	
	if (sessionStorage.length == 0) {
		gestionOperaciones('D');
	} else if(((null === numeroFilas) || (undefined === numeroFilas) || ("" === numeroFilas)) 
				&& ("" === operacionSeleccionada || ("null" ===  operacionSeleccionada) || ("D" === operacionSeleccionada)))
					gestionOperaciones('D');
		   else
			   		gestionOperaciones(operacionSeleccionada);

    $("#consultarImagen").click(function() {
		gestionOperaciones('C');
    });

    $("#BotonConsultarImagenes").click(function() {    	
    	sessionStorage.setItem("operacionSeleccionada", 'C');
    	sessionStorage.setItem("consultado", 'CO');
    });

    $("#anadirImagen").click(function() {
    	gestionOperaciones('A');
    });
    
    $("#eliminarImagen").click(function() {
    	gestionOperaciones('E');
    });

});