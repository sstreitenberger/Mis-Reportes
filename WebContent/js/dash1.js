$('#_menu4').addClass('active');

function closeModal() {
	$('#group_reporte').hide()
	$('#group_rango').hide()
	$('#group_tiendas').hide()
	$('#group_formato').hide()
	$('#group_alias').hide()
	$('#btnEliminar').hide()
	$('#btnGuardar').hide()
}

function setDashTipo() {
	$("#dash_tipo").empty();
	$("<option/>").appendTo("#dash_tipo");
	$("<option/>", { value : "online", text : "Online" }).appendTo("#dash_tipo");
	$("<option/>", { value : "historial", text : "Historial" }).appendTo("#dash_tipo");
	$("<option/>", { value : "otros", text : "Otros" }).appendTo("#dash_tipo");
}

var dashmultiple;

function multipleTiendas() {
	var config = {
		nonSelectedListLabel : 'Disponible(s)',
		selectedListLabel : 'Seleccionado(s)',
		filterTextClear : 'Mostrar todos',
		filterPlaceHolder : 'Filtro',
		moveAllLabel : 'Mover todos',
		removeAllLabel : 'Borrar todos',
		infoText : 'Mostrando {0} reportes',
		infoTextEmpty : 'Lista vacia',
		infoTextFiltered : '<span class="label label-warning">Filtrando</span> {0} de {1}'
	}
	// $('#dash_tiendas').destroy();
	dashmultiple = $('#dash_tiendas').bootstrapDualListbox(config);
}

function setDashRango() {
	$('#dash_rango').empty();
	$('#dash_rango').attr("required", "required");
	$("<option/>").appendTo("#dash_rango");

	$.ajax({
		url : "DashGetRangos",
		dataType : "text",
		statusCode : {
			200 : function(res) {
				$(JSON.parse(res)).each(function(i, r) {
					$("<option/>", {
						value : r.id,
						text : r.nombre
					}).appendTo("#dash_rango");
				})
			},
			401 : function(res) {
				alert(res.responseText);
				redirectLogin();
			},
			500 : function(res) {
				alert(res.responseText);
			}
		}
	});
}

function getdashtiendas() {
	$('#dash_tiendas').empty();
	$.ajax({
		url : "DashGetTiendas",
		dataType : "text",
		statusCode : {
			200 : function(res) {
				var response = JSON.parse(res);
				$(response).each(function(i, v) {
					$(v).each(function(idx, t) {
						$("<option/>", {
							value : t.ip,
							text : '(' + t.grupo + ') ' + t.nombre
						}).appendTo("#dash_tiendas");
					});
				});
				if ($("#dash_tiendas option").length==1){
					$("#dash_tiendas option").attr("selected","selected");
					$("#group_tiendas").addClass("hide");
				} else {
					dashmultiple.bootstrapDualListbox('refresh', true);
				} 
					
			},
			401 : function(res) {
				alert(res.responseText);
				redirectLogin();
			},
			500 : function(res) {
				alert(res.responseText);
			}
		}
	});
}

function getdashreportes() {
	$('#dash_reporte').empty();
	$("<option/>").appendTo("#dash_reporte");
	$.ajax({
		url : "DashGetReportes",
		dataType : "text",
		data : {
			"dash_tipo" : $("#dash_tipo").val()
		},
		statusCode : {
			200 : function(res) {
				$(JSON.parse(res)).each(function(i, r) {
					$("<option/>", {
						"data-graf" : r.aplic_graf,
						"value" : r.id,
						"text" : "(" + r.marca + ") " + r.titulo
					}).appendTo("#dash_reporte");
				})
			},
			401 : function(res) {
				alert(res.responseText);
				redirectLogin();
			},
			500 : function(res) {
				alert(res.responseText);
			}
		}
	});
}

function nuevoReporte(e) {
	$('#dash_alias').text("")
	$('#dashModal form').attr("action","javascript:guardarDash()")
	$('#dash_title').empty().html('Nuevo reporte [' + e + ']');
	$('#dash_orden').val(e);
	setDashTipo();
	$('#dashModal').modal();
}

function editarReporte(e) {
	$('#dash_alias').text("")
	$('#dashModal form').attr("action","javascript:guardarEditado()")
	$('#dash_title').empty().html('Editar reporte [' + e + ']');
	$('#dash_orden').val(e);
	$('#btnEliminar').show();
	$.ajax({
		url : "DashEditarReporte",
		type : "GET",
		data : {
			orden : e
		},
		statusCode : {
			200 : function(res) {
				completarEditar(res);
			},
			201 : function(res) {
				alert(res.responseText);
			},
			401 : function(res) {
				alert(res.responseText);
				redirectLogin();
			},
			500 : function(res) {
				alert(res.responseText);
			}
		}
	});
}

function completarEditar(resp){
	// muestro modal-----------------------------------------
	$('#dashModal').modal();
	$('#dash_id').val(resp.id);
	// cargo los tipos de reporte-----------------------------------------
	setDashTipo();
	// marco la opcion actual-----------------------------------------
	$('#dash_tipo option[value="'+resp.tipo+'"]').attr('selected', 'selected');
	// muestro el campo reporte-----------------------------------------
	$('#group_reporte').show();
	// vacio el campo reportes-----------------------------------------
	$('#dash_reporte').empty();
	// traigo el listado de reportes por tipo-----------------------------------------
	$.ajax({
		url : "DashGetReportes",
		dataType : "text",
		data : {
			"dash_tipo" : $("#dash_tipo").val()
		},
		statusCode : {
			200 : function(res) {
				// cargo los reportes como opciones-----------------------------------------
				$(JSON.parse(res)).each(function(i, r) {
					$("<option/>", {
						"data-graf" : r.aplic_graf,
						"value" : r.id,
						"text" : "(" + r.marca + ") " + r.titulo
					}).appendTo("#dash_reporte");
				})
				// marco el reporte a editar -----------------------------------------
				$('#dash_reporte option[value="'+resp.reporte_id+'"]').attr('selected', 'selected');
				if ($('#dash_reporte option[value="'+resp.reporte_id+'"]').attr("data-graf") == "0" ){
					$('.formato_to_hide').hide();
				} else {
					$('.formato_to_hide').show();
				}
					
				// si es online no muestro el rango -----------------------------------------
				if ($('#dash_tipo').val() == "online") {
					$('#group_rango').hide();
					$('#dash_rango').removeAttr("required");
				} 
				// sino vacio rango y los traigo por ajax -----------------------------------------
				else {
					$('#dash_rango').empty();
					$('#dash_rango').attr("required", "required");
					$('#group_rango').show();
					$.ajax({
						url : "DashGetRangos",
						dataType : "text",
						statusCode : {
							200 : function(res) {
								// completo el select con los rangos -----------------------------------------
								$(JSON.parse(res)).each(function(i, r) {
									$("<option/>", {
										value : r.id,
										text : r.nombre
									}).appendTo("#dash_rango");
								})
								// marco el rango a editar -----------------------------------------
								$('#dash_rango option[value="'+resp.rango.id+'"]').attr('selected', 'selected');
							},
							401 : function(res) {
								alert(res.responseText);
								redirectLogin();
							},
							500 : function(res) {
								alert(res.responseText);
							}
						}
					});
				} 
				
				if ($('#dash_tipo').val() != "otros") {
					// muestro el grupo de tiendas -----------------------------------------
					$('#group_tiendas').show();
					// limpio el listado -----------------------------------------
					$('#dash_tiendas').empty();
					// traigo las tiendas por ajax -----------------------------------------
					$.ajax({
						url : "DashGetTiendas",
						dataType : "text",
						statusCode : {
							200 : function(res) {
								// completo el select de tiendas 
								$(JSON.parse(res)).each(function(i, v) {
									$(v).each(function(idx, t) {
										$("<option/>", {
											value : t.ip,
											text : '(' + t.grupo + ') ' + t.nombre
										}).appendTo("#dash_tiendas");
									});
								});
								// debo seleccionar las tiendas del reporte -------------------------------
								$(resp.tiendas).each(function(i,v){
									$('#dash_tiendas option[value="'+v.ip+'"]').attr('selected', 'selected');
								});
								// defino el duallist -----------------------------------------
								dashmultiple.bootstrapDualListbox('refresh', true);
							},
							401 : function(res) {
								alert(res.responseText);
								redirectLogin();
							},
							500 : function(res) {
								alert(res.responseText);
							}
						}
					});
				} 
				
				// muestro el grupo formato y alias. Y el boton guardar  --------------------------
				$('#group_formato').show();
				$('#group_alias').show();
				$('#btnGuardar').show();
				// marco las opciones para formato y alias si existe
				$('#dash_formato option[value="'+resp.formato+'"]').attr('selected', 'selected');
				$('#dash_alias').text( resp.alias )
			},
			401 : function(res) {
				alert(res.responseText);
				redirectLogin();
			},
			500 : function(res) {
				alert(res.responseText);
			}
		}
	});
}

function guardarEditado(){
	$.ajax({
		url : "DashEditarReporte",
		type : "POST",
		data : $("#dashModal form").serialize(),
		statusCode : {
			200 : function(res) {
				//alert(res.responseText);$('#dashModal').toggle()
				location.reload();
			},
			401 : function(res) {
				alert(res.responseText);
				redirectLogin();
			},
			500 : function(res) {
				alert(res.responseText);
			}
		}
	});
}

function borrarReporte(){
	$.ajax({
		url : "DashEliminarReporte",
		type : "POST",
		data : $("#dashModal form").serialize(),
		statusCode : {
			200 : function(res) {
				//alert(res.responseText);$('#dashModal').toggle()
				location.reload();
			},
			401 : function(res) {
				alert(res.responseText);
				redirectLogin();
			},
			500 : function(res) {
				alert(res.responseText);
			}
		}
	});
}

function guardarDash() {
	$.ajax({
		url : "DashGuardarReporte",
		type : "POST",
		data : $("#dashModal form").serialize(),
		statusCode : {
			200 : function(res) {
				//alert(res.responseText);$('#dashModal').toggle()
				location.reload();
			},
			401 : function(res) {
				alert(res.responseText);
				redirectLogin();
			},
			500 : function(res) {
				alert(res.responseText);
			}
		}
	});
}

function mostraropciones() {
	if ($('#dash_tipo').val() != "otros") {
		$('#group_tiendas').show();
		if ($('#dash_tiendas option').length == 0) 
			getdashtiendas();
	}else {
		$('#group_tiendas').hide();
		$('#dash_tiendas').removeAttr("required");
	}
	$('#group_formato').show();
	$('#group_alias').show();
	$('#btnGuardar').show();
}

$(function() {

	closeModal();
	multipleTiendas();

	$('#btnEliminar').click(function() {
		borrarReporte()
	});

	$('#dash_tipo').change(function() {
		$('#group_reporte').show();
		getdashreportes();
		if ($('#dash_tipo').val() == "online") {
			$('#group_rango').hide();
			$('#dash_rango').removeAttr("required");
		}  
	});

	$('#dash_reporte').change(function() {
		if ($('#dash_reporte').find(":selected").attr("data-graf") == "0" ){
			$('.formato_to_hide').hide();
		} else {
			$('.formato_to_hide').show();
		}
		
		if ($('#dash_tipo').val() != "online") {
			setDashRango();
			$('#group_rango').show();
		} else {
			mostraropciones();
		}
	});

	$('#dash_rango').change(function() {
		mostraropciones();
	});

	$('#dashModal').on('hidden.bs.modal', function() {
		closeModal();
	});

});