$(function() {
	traerDash('1');
	traerDash('2');
	traerDash('3');
	traerDash('4');
});

function traerDash(orden) {
	$('#container'+orden).html('<i class="fa fa-5x fa-fw fa-spinner fa-spin text-primary"></i>');
	
	$.ajax({
		url : "DashGetReportePorOrden",
		dataType : "json",
		data : {
			orden : orden
		},
		statusCode : {
			200 : function(res) {
				var titulo = res[0].titulo;
				
				setTitulo(orden, titulo, '#titulo'+orden)
				if(res[0].data.length>0){
					var data = res[0].data;
					var formato = res[0].formato;
			
					
					if (formato == 'tabla')
						
						createTable(data,'#container'+orden);
					else
						mostrarReporte(data, formato,'#tablahide'+orden,'container'+orden);
				} else {
					
					reporteSinDatos( "Reporte sin datos", '#container'+orden)
				}
			},
			201 : function(res) {
				$('#container'+orden).html('<a class="btn btn-primary" onclick="nuevoReporte(\''+orden+'\')">Crear reporte</a>')
			},
			401 : function(res) {
				alert(res.responseText);
				redirectLogin();
			},
			500 : function(res) {
				$('#titulo'+orden).html('Error <i title="Editar" onclick="editarReporte(\''+orden+'\')" class="fa fa-edit fa-lg pull-right"></i>')
				$('#container'+orden).html('<span style="color:red">Error: ' + res.responseText + '</span>');
			}
		}
	});
	
}

function setTitulo(orden, titulo, campo){
	var editar = '</i><i title="Minimizar" id="minimizar'+orden+'" style="cursor: pointer" onclick="colapsar(\''+orden+'\')" class="fa fa-compress fa-lg pull-right hidden"></i><i title="Maximizar" id="maximizar'+orden+'" style="cursor: pointer" onclick="expandir(\''+orden+'\')" class="fa fa-expand fa-lg pull-right"></i><i title="Editar" style="color:orange" onclick="editarReporte(\''+orden+'\')" class="fa fa-edit fa-lg pull-right">';
	$(campo).html(titulo + editar);
}

var chartx;
function crearChart(tipo,tabla,render) {
	options = {
		data : {
			table : tabla
		},
		chart : {
			type : tipo,
			renderTo : render
		},
		title : {
			text : null
		},
		yAxis : {
			allowDecimals : false,
			title : {
				text : null
			}
		}
	};
	chartx = new Highcharts.Chart(options);
}

function reporteSinDatos (texto,donde){
	$(donde).html('<span style="color:blue"> ' + texto + '</span>');
}

function mostrarReporte(json, formato, temp, render) {	
	var tablatemp = $(temp)[0];
	doCSV(json, tablatemp, false);
	crearChart(formato, tablatemp, render);
}

function createTable(data,container){

	var tabla = $("<table></table>").addClass("table table-sm table-striped table-bordered");
//	var tabla = "<div class='col-lg-12 ' id='table_div_to_hide' style='overflow-x: auto'><div id='table-div'><div id='datatable_wrapper' class='dataTables_wrapper form-inline dt-bootstrap no-footer'><div class='row'><div class='col-sm-6'><div class='dt-buttons btn-group'><a class='btn btn-default buttons-csv buttons-html5' tabindex='0' aria-controls='datatable' href='#'><span>CSV</span></a><a class='btn btn-default buttons-excel buttons-html5' tabindex='0' aria-controls='datatable' href='#'><span>Excel</span></a></div></div><div class='col-sm-6'><div id='datatable_filter' class='dataTables_filter'><label>Search:<input type='search' class='form-control input-sm' placeholder='' aria-controls='datatable'></label></div></div></div><div class='row'><div class='col-sm-12'><table id='datatable' class='table table-striped table-bordered dataTable no-footer' cellspacing='0' width='100%' role='grid' aria-describedby='datatable_info' style='width: 100%;'><thead><tr role='row'><th class='sorting_asc' tabindex='0' aria-controls='datatable' rowspan='1' colspan='1' aria-sort='ascending' aria-label='FECHA: activate to sort column descending' style='width: 153px;'>FECHA</th><th class='sorting' tabindex='0' aria-controls='datatable' rowspan='1' colspan='1' aria-label='DESCUENTO: activate to sort column ascending' style='width: 114px;'>DESCUENTO</th><th class='sorting' tabindex='0' aria-controls='datatable' rowspan='1' colspan='1' aria-label='DESCRIPCION : activate to sort column ascending' style='width: 127px;'>DESCRIPCION </th><th class='sorting' tabindex='0' aria-controls='datatable' rowspan='1' colspan='1' aria-label='NUMERO DE CHEQUE : activate to sort column ascending' style='width: 190px;'>NUMERO DE CHEQUE </th><th class='sorting' tabindex='0' aria-controls='datatable' rowspan='1' colspan='1' aria-label='VALOR DEL ITEM : activate to sort column ascending' style='width: 151px;'>VALOR DEL ITEM </th><th class='sorting' tabindex='0' aria-controls='datatable' rowspan='1' colspan='1' aria-label='VENTA BRUTA: activate to sort column ascending' style='width: 128px;'>VENTA BRUTA</th><th class='sorting' tabindex='0' aria-controls='datatable' rowspan='1' colspan='1' aria-label='DESCUENTO BRUTO : activate to sort column ascending' style='width: 182px;'>DESCUENTO BRUTO </th></tr></thead><tbody><tr role='row' class='odd'><td title='2017-07-01 07:59:59.0' class='sorting_1'>2017-07-01 07:59:59.0</td><td title='PampaCliePRE'>PampaCliePRE</td><td title='Cappuccino'>Cappuccino</td><td title='4914'>4914</td><td title='68.00'>68.00</td><td title='245.01'>245.01</td><td title='-245.00'>-245.00</td></tr><tr role='row' class='even'><td title='2017-07-01 07:59:59.0' class='sorting_1'>2017-07-01 07:59:59.0</td><td title='PampaCliePRE'>PampaCliePRE</td><td title='Jugo Naranja'>Jugo Naranja</td><td title='4914'>4914</td><td title='50.00'>50.00</td><td title='245.01'>245.01</td><td title='-245.00'>-245.00</td></tr><tr role='row' class='odd'><td title='2017-07-01 07:59:59.0' class='sorting_1'>2017-07-01 07:59:59.0</td><td title='PampaCliePRE'>PampaCliePRE</td><td title='Tostado JQ'>Tostado JQ</td><td title='4914'>4914</td><td title='95.00'>95.00</td><td title='245.01'>245.01</td><td title='-245.00'>-245.00</td></tr><tr role='row' class='even'><td title='2017-07-01 07:59:59.0' class='sorting_1'>2017-07-01 07:59:59.0</td><td title='PampaCliePRE'>PampaCliePRE</td><td title='Barra Alm SBX'>Barra Alm SBX</td><td title='4914'>4914</td><td title='32.00'>32.00</td><td title='245.01'>245.01</td><td title='-245.00'>-245.00</td></tr><tr role='row' class='odd'><td title='2017-07-01 08:02:53.0' class='sorting_1'>2017-07-01 08:02:53.0</td><td title='PampaCliePRE'>PampaCliePRE</td><td title='Tostado JQ'>Tostado JQ</td><td title='4916'>4916</td><td title='95.00'>95.00</td><td title='245.01'>245.01</td><td title='-245.00'>-245.00</td></tr><tr role='row' class='even'><td title='2017-07-01 08:02:53.0' class='sorting_1'>2017-07-01 08:02:53.0</td><td title='PampaCliePRE'>PampaCliePRE</td><td title='Latte'>Latte</td><td title='4916'>4916</td><td title='68.00'>68.00</td><td title='245.01'>245.01</td><td title='-245.00'>-245.00</td></tr><tr role='row' class='odd'><td title='2017-07-01 08:02:53.0' class='sorting_1'>2017-07-01 08:02:53.0</td><td title='PampaCliePRE'>PampaCliePRE</td><td title='Jugo Naranja'>Jugo Naranja</td><td title='4916'>4916</td><td title='50.00'>50.00</td><td title='245.01'>245.01</td><td title='-245.00'>-245.00</td></tr><tr role='row' class='even'><td title='2017-07-01 08:02:53.0' class='sorting_1'>2017-07-01 08:02:53.0</td><td title='PampaCliePRE'>PampaCliePRE</td><td title='Barra Alm SBX'>Barra Alm SBX</td><td title='4916'>4916</td><td title='32.00'>32.00</td><td title='245.01'>245.01</td><td title='-245.00'>-245.00</td></tr><tr role='row' class='odd'><td title='2017-07-01 08:05:30.0' class='sorting_1'>2017-07-01 08:05:30.0</td><td title='PampaCliePRE'>PampaCliePRE</td><td title='Latte'>Latte</td><td title='4918'>4918</td><td title='68.00'>68.00</td><td title='245.01'>245.01</td><td title='-245.00'>-245.00</td></tr><tr role='row' class='even'><td title='2017-07-01 08:05:30.0' class='sorting_1'>2017-07-01 08:05:30.0</td><td title='PampaCliePRE'>PampaCliePRE</td><td title='Tostado JQ'>Tostado JQ</td><td title='4918'>4918</td><td title='95.00'>95.00</td><td title='245.01'>245.01</td><td title='-245.00'>-245.00</td></tr></tbody></table></div></div><div class='row'><div class='col-sm-5'><div class='dataTables_info' id='datatable_info' role='status' aria-live='polite'>Showing 1 to 10 of 860 entries</div></div><div class='col-sm-7'><div class='dataTables_paginate paging_simple_numbers' id='datatable_paginate'><ul class='pagination'><li class='paginate_button previous disabled' id='datatable_previous'><a href='#' aria-controls='datatable' data-dt-idx='0' tabindex='0'>Previous</a></li><li class='paginate_button active'><a href='#' aria-controls='datatable' data-dt-idx='1' tabindex='0'>1</a></li><li class='paginate_button '><a href='#' aria-controls='datatable' data-dt-idx='2' tabindex='0'>2</a></li><li class='paginate_button '><a href='#' aria-controls='datatable' data-dt-idx='3' tabindex='0'>3</a></li><li class='paginate_button '><a href='#' aria-controls='datatable' data-dt-idx='4' tabindex='0'>4</a></li><li class='paginate_button '><a href='#' aria-controls='datatable' data-dt-idx='5' tabindex='0'>5</a></li><li class='paginate_button disabled' id='datatable_ellipsis'><a href='#' aria-controls='datatable' data-dt-idx='6' tabindex='0'>…</a></li><li class='paginate_button '><a href='#' aria-controls='datatable' data-dt-idx='7' tabindex='0'>86</a></li><li class='paginate_button next' id='datatable_next'><a href='#' aria-controls='datatable' data-dt-idx='8' tabindex='0'>Next</a></li></ul></div></div></div></div></div><div class='hidden' id='hide-table-div'></div></div>"
	$(container).empty();
	doCSV(data, $(tabla)[0], false);
	$(container).append(tabla);

	var tablezzz = $(tabla).DataTable({
		buttons : [{extend: 'excelHtml5', text: 'Descargar' }],
		dom : "<'row'<'col-sm-3'B><'col-sm-3'l><'col-sm-6'f>><'row'<'col-sm-12'tr>><'row'<'col-sm-12'p><'col-sm-12'i>>"
	});
	//tablezzz.buttons().container().prependTo($(tabla).parent().parent());
	
}

function expandir(orden){
	 if (orden>0){
		
		 document.getElementById("container"+orden).style.height = "80vh";
		 
	
		 document.getElementById("div"+orden).classList.remove('col-md-6');
		 document.getElementById("div"+orden).classList.add('col-md-12');
		 document.getElementById("maximizar"+orden).classList.add('hidden');
		 document.getElementById("minimizar"+orden).classList.remove('hidden');
		 var contador=1;
		 while (contador<=4){
			 if(contador!=orden){
			 document.getElementById("div"+contador).classList.add('hidden');
			 }
			 contador++;
		 }
		 chartx.reflow();
		}
	
}


function colapsar(orden){
	 if (orden>0){
		
		 document.getElementById("container"+orden).style.height = "34vh";
	
		 document.getElementById("div"+orden).classList.add('col-md-6');
		 document.getElementById("div"+orden).classList.remove('col-md-12');
		 document.getElementById("maximizar"+orden).classList.remove('hidden');
		 document.getElementById("minimizar"+orden).classList.add('hidden');
		 var contador=1;
		 while (contador<=4){
			 if(contador!=orden){
			 document.getElementById("div"+contador).classList.remove('hidden');
			 }
			 contador++;
		 }
	chartx.reflow();
		}
}
