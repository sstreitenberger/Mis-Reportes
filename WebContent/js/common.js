var options;
var chart;
var _tabla;
var searchTemplate = "<div class='form-group'><div class='input-group'><input type='text' oninput='hideGroup(this)' class='form-control' placeholder='Filtro'><span class='input-group-addon'><span class='fa fa-search' aria-hidden='true'></span></span></div>";
var flagchart = false;
var flagtable = false;
var flag_actualizar = false;

Highcharts.setOptions({
	lang : {
		contextButtonTitle : "Opciones",
		decimalPoint : ",",
		drillUpText : "Volver a {series.name}",
		months : [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
				"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
				"Diciembre" ],
		printChart : "Imprimir",
		shortMonths : [ "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago",
				"Sep", "Oct", "Nov", "Dic" ],
		thousandsSep : ".",
		weekdays : [ "Domingo", "Lunes", "Martes", "Miercoles", "Jueves",
				"Viernes", "Sabado" ]
	}
});

function openCal(e) {
	$("#" + e).datepicker("show");
}

function redirectLogin() {
	document.location.href = 'login';
}

function hideChart() {
	if (!flagtable) {
		flagchart = true;
		$('#btn123').text('Mostrar gráfico').removeClass('btn-warning')
				.addClass('btn-danger').attr("onclick",
						"javascript: showChart();");
		$('#chart_div_to_hide').addClass('hidden');
	} else {
		showTable();
		hideChart();
	}
}

function showChart() {
	flagchart = false;
	$('#btn123').text('Ocultar gráfico').removeClass('btn-danger').addClass(
			'btn-warning').attr("onclick", "javascript: hideChart();");
	$('#chart_div_to_hide').removeClass("hidden");
}

function hideTable() {
	if (!flagchart) {
		flagtable = true;
		$('#btn234').text('Mostrar tabla').removeClass('btn-warning').addClass(
				'btn-danger').attr("onclick", "javascript: showTable();");
		$('#table_div_to_hide').addClass("hidden");
	} else {
		showChart();
		hideTable();
	}
}

function showTable() {
	flagtable = false;
	$('#btn234').text('Ocultar tabla').removeClass('btn-danger').addClass(
			'btn-warning').attr("onclick", "javascript: hideTable();");
	$('#table_div_to_hide').removeClass("hidden");
}

function hideGroup(e) {
	var value = $(e).val();
	if (value != '') {
		$(".sievet .collapse").addClass('nocollapse').removeClass('collapse');
	} else {
		$(".sievet .nocollapse").addClass('collapse').removeClass('nocollapse');
	}
}

function selectall(btn, field, select) {
	$(btn).attr("onclick", "selectall(this,'" + field + "'," + !select + ")");

	if (select) {
		$(btn).find('span').removeClass("fa-square-o").addClass(
				"fa-check-square-o");
	} else {
		$(btn).find('span').removeClass("fa-check-square-o").addClass(
				"fa-square-o");
	}

	$("#" + field + " :input").each(function() {
		$(this).prop("checked", select);
		$(this).attr("data-cacheval", select);
	});

	$("#" + field + " label").each(function() {
		if (select) {
			$(this).addClass("active");
		} else {
			$(this).removeClass("active");
		}
	});
};

function selectallfull(btn, select) {
	$(btn).attr("onclick", "selectallfull(this," + !select + ")");

	if (select)
		$(btn).find('span').removeClass("fa-square-o").addClass(
				"fa-check-square-o");
	else
		$(btn).find('span').removeClass("fa-check-square-o").addClass(
				"fa-square-o");

	$("._saf").each(function() {
		$(this).click()
	});

};

function wrapTable() {
	jQuery('.dataTable').wrap('<div class="scrollStyle" />');
}

function changeChart() {
	setupChart($("#selectChart").find(":selected").val());
}

function setupChart(e) {
	$('#selectChart option').prop('selected', false)
			.filter('[value=' + e + ']').prop('selected', true);
	options = {
		data : {
			table : 'hidetable'
		},
		chart : {
			type : e,
			renderTo : 'container'
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
	chart = new Highcharts.Chart(options);
}

function doCSV(json, table, showttl) {
	var inArray = arrayFrom(json);
	var outArray = [];
	for ( var row in inArray) {
		outArray[outArray.length] = parse_object(inArray[row]);
	}
	renderCSV(outArray, table, showttl);
}

function renderCSV(objects, table, showttl) {
	var rows = $.csv.fromObjects(objects, {
		justArrays : true
	});
	if (rows.length < 1)
		return;

	$(table).html("");
	var thead = document.createElement("thead");
	var tr = document.createElement("tr");
	var header = rows[0];
	for (field in header) {
		var th = document.createElement("th");
		$(th).html(header[field])
		tr.appendChild(th);
	}
	thead.appendChild(tr);
	var tbody = document.createElement("tbody");
	for (var i = 1; i < rows.length; i++) {

		tr = document.createElement("tr");
		for (field in rows[i]) {
			var td = document.createElement("td");
			$(td).html(rows[i][field]).attr("title", rows[i][field]);
			tr.appendChild(td);
		}
		if (!showttl && rows[i][0] === 'TOTAL')
			console.log(rows[i]);
		else
			tbody.appendChild(tr);

	}
	table.appendChild(thead);
	table.appendChild(tbody);

}

function exportExcel(s) {
	var delimiter = ",";
	var firstRowHeader = true;
	var nobreaks = false;
	var A = null;
	var txt;

	if (s.trim() != "") {
		try {
			var inArray = [];
			inArray = arrayFrom(JSON.parse(s));
			var outArray = [];
			for ( var row in inArray) {
				outArray[outArray.length] = parse_object(inArray[row]);
			}
			var options = {};
			options.separator = delimiter;
			options.headers = firstRowHeader;
			options.noBreaks = nobreaks;
			var csv = $.csv.fromObjects(outArray, options);
			if (options.headers
					&& (csv.charAt(0) === '\n' || csv.charAt(0) === '\r'))
				csv = "Field1" + csv;
			txt = csv;
			CSV.isFirstRowHeader = firstRowHeader;
			CSV.delimiter = delimiter;
			CSV.autodetect = false;
			CSV.parse(txt);
			saveExcel(txt, false);
			return;
		} catch (e) {
			alert(e);
		}
	}
}

function success(data, graficar, contop) {
	try {
		
		var json = jsonFrom(data);
		//console.log(json)
		if (json.length == 0){
			throw "Reporte sin datos";
		}

		$('#instrutivo').hide();
		$("#table-div").empty();
		$("#table-div")
				.append(
						"<table id=\"datatable\" class=\"table table-striped table-bordered\" cellspacing=\"0\" width=\"100%\"></table>");

		if (contop){
			$(".alert-info").addClass("hide");
			if (json.length > 999){
				$(".alert-info").removeClass("hide");
			}
		} else {
			$(".alert-info").addClass("hide");
		}

		var table = $('#datatable')[0];
		doCSV(json, table, true);

		var xtitle = $("#listReportes :radio:checked").parent().text().trim()
				.replace(/ /g, "_");
		$('#title_repo').empty().append(
				'<h4 class="text-center" style="background-color: lightgrey; padding: 10px" >'
						+ $("#listReportes :radio:checked").parent().text()
								.trim() + '</h4>');
		var tablezzz = $('#datatable').DataTable({
			lengthChange : false,
			buttons : [ 'csvHtml5', 'excelHtml5', 'pdf' ],
			language : {
				decimal : ",",
			}
		});
		tablezzz.buttons().container().appendTo(
				'#datatable_wrapper .col-sm-6:eq(0)');

		if (graficar) {
			$("#hide-table-div").empty();
			$("#hide-table-div").append(
					"<table id=\"hidetable\" class=\"hidden\"></table>");
			var tablex = $('#hidetable')[0];

			doCSV(json, tablex, false);
			showChart();
			setupChart('line');
			$('.highcharts-container').css("width", "auto");
			$('#menu').removeClass('hidden');

		} else {
			hideChart();
			$('#menu').addClass('hidden');
		}

	} catch (err) {
		showError(err)
	}
}

function showError(txt) {
	$('#exampleModal').find('.modal-body').html('<p>' + txt + '</p>');
	$('#exampleModal').modal('show');
}

function validarMarca() {
	var marca = $("#listReportes :radio:checked").attr('id').split("_")[0];
	var valido = true;
	$(".sievet :checkbox:checked").each(function() {
		if (marca == 'BK' && $(this).attr('id').split(".")[1] != '20') {
			valido = false;
		}
		if (marca == 'SBX' && $(this).attr('id').split(".")[1] != '22') {
			valido = false;
		}
	});
	return valido;
}

function validarVacio() {
	if ($('#from').length > 0)
		if (!$('#from').val().length > 0)
			return;

	if ($('#to').length > 0)
		if (!$('#to').val().length > 0)
			return;

	if ($('#from_otros').length > 0)
		if (!$('#from_otros').val().length > 0)
			return;

	if ($('#to_otros').length > 0)
		if (!$('#to_otros').val().length > 0)
			return;

	if ($("#rightsidenav :checkbox").length > 0)
		if ($("#rightsidenav :checkbox:checked").length == 0)
			return;

	if ($("#leftsidenav :radio").length > 0)
		if ($("#leftsidenav :radio:checked").length == 0)
			return;

	if (!validarMarca()) {
		showError('Las tiendas seleccionadas deben coincidir con la marca del reporte seleccionado!');
		return;
	}

	actualizarReporte();
}


$(document).ready(function() {

	if ($("div.sieve").length > 0)
		$("div.sieve").sieve({
			searchTemplate : searchTemplate,
			itemSelector : "label"
		});

	if ($("div.sievet").length > 0)
		$("div.sievet").sieve({
			searchTemplate : searchTemplate,
			itemSelector : "label"
		});

	if ($("#from").length > 0)
		$("#from").datepicker({
			dateFormat : 'yy-mm-dd',
			maxDate : '-1d',
			numberOfMonths : 1,
			onClose : function(selectedDate) {
				$("#to").datepicker("option", "minDate", selectedDate);
			}
		}).attr('readonly', 'readonly').change(function(e) {
			flag_actualizar = true;
			validarVacio();
		});

	if ($("#to").length > 0)
		$("#to").datepicker({
			dateFormat : 'yy-mm-dd',
			maxDate : '-1d',
			numberOfMonths : 1,
			onClose : function(selectedDate) {
				$("#from").datepicker("option", "maxDate", selectedDate);
			}
		}).attr('readonly', 'readonly').change(function(e) {
			flag_actualizar = true;
			validarVacio();
		});

	if ($("#to_otros").length > 0)
		$("#to_otros").datepicker({
			dateFormat : 'yy-mm-dd',
			numberOfMonths : 1,
			onClose : function(selectedDate) {
				$("#from_otros").datepicker("option", "maxDate", selectedDate);
			}
		}).attr('readonly', 'readonly').change(function(e) {
			flag_actualizar = true;
			validarVacio();
		});

	if ($("#from_otros").length > 0)
		$("#from_otros").datepicker({
			dateFormat : 'yy-mm-dd',
			numberOfMonths : 1,
			onClose : function(selectedDate) {
				$("#to_otros").datepicker("option", "minDate", selectedDate);
			}
		}).attr('readonly', 'readonly').change(function(e) {
			flag_actualizar = true;
			validarVacio();
		});

	$(".container-fluid").click(function() {
		hideTabs()
	});

});

var $loading = $('#loading').hide();
$(document).ajaxStart(function() {
	$loading.show();
}).ajaxStop(function() {
	$loading.hide();
});

function openMod() {
	closeLeftNav();
	$('#multiple').empty();
	$.ajax({
		type : "GET",
		url : "AbmTraerReportes",
		data : {
			abmtipoRepo : $("#abmtipoRepo").val()
		},
		statusCode : {
			200 : function(data) {
				try {
					$.each(JSON.parse(data), function(i, v) {
						if (v.seleccionado == true)
							$('#multiple').append(
									'<option value="' + v.id
											+ '" selected="selected">('
											+ v.marca + ') ' + v.titulo
											+ '</option>')
						else
							$('#multiple').append(
									'<option value="' + v.id + '">(' + v.marca
											+ ') ' + v.titulo + '</option>')
					})
					$("#modalReportes").modal('show')
					setMultiple()
				} catch (err) {
					showError(err);
				}
			},
			500 : function(x) {
				showError(x.responseText);
			}
		}
	});
}

function setMultiple() {
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
	$('#multiple').bootstrapDualListbox(config)
}

function clickreporte() {
	flag_actualizar = true;
	closeLeftNav();
	validarVacio();
}

function hideTabs() {
	if ($("#leftsidenav").length > 0 && $("#leftsidenav").css("width") != '0px')
		closeLeftNav()
	if ($("#rightsidenav").length > 0
			&& $("#rightsidenav").css("width") != '0px')
		closeRightNav()
}

function openLeftNav() {
	$("#leftsidenav").show()
}

function closeLeftNav() {
	$("#leftsidenav").hide()
}

function openRightNav() {
	$("#rightsidenav").show()
}

function closeRightNav() {
	$("#rightsidenav").hide()
	validarVacio()
}

function arraysIdentical(a, b) {
	var i = a.length;
	if (i != b.length)
		return false;
	while (i--) {
		if (a[i] !== b[i])
			return false;
	}
	return true;
};

var _selected = [];

function actualizar(link, contop) {
	try {

		var _desde;
		var _hasta;
		var _reporte = $("#listReportes :radio:checked").attr('id').split("_")[1];
		var _tienda;
		var $tiendas = [];

		if ($(".tiendainfo").length > 0)
			_tienda = $(".tiendainfo").attr('id');

		if ($('#from').length > 0)
			_desde = $('#from').val();

		if ($('#from_otros').length > 0)
			_desde = $('#from_otros').val();

		if ($('#to').length > 0)
			_hasta = $('#to').val();

		if ($('#to_otros').length > 0)
			_hasta = $('#to_otros').val();

		if ($("#rightsidenav :checkbox").length > 0) {
			$("#rightsidenav :checkbox:checked").each(function() {
				$tiendas.push($(this).attr('id').split("_")[1]);
			});
		}

		if (contop){
			if (arraysIdentical($tiendas, _selected)) {
				if (!flag_actualizar) {
					return;
				} else {
					flag_actualizar = false;
				}
			} else {
				_selected = $tiendas;
			}
		} else {
			_selected = $tiendas;
		}
			
		
		
		var $data = {
			tiendas : _selected,
			reporte : _reporte,
			top : contop,
			desde : _desde,
			hasta : _hasta,
			tienda : _tienda
		}

		$.ajax({
			type : "POST",
			url : link,
			data : $data,
			dataType : "text",
			statusCode : {
				200 : function(data) {
					success(data, true, contop);
				},
				201 : function(data) {
					success(data, false, contop);
				},
				401 : function(res) {
					showError(res.responseText);
					redirectLogin();
				},
				500 : function(res) {
					showError(res.responseText);
				}
			}
		});
	} catch (err) {
		// hideWait();
		showError(err);
	}
};

function getFullReporte() {
	try {
		actualizar('GetReportes', false);
	} catch (err) {
		showError(err);
	}

};

function actualizarReporte() {
	try {
		actualizar('GetReportes', true);
	} catch (err) {
		showError(err);
	}

};

function AbmReportesGuardar() {
	$.ajax({
		type : "POST",
		url : "AbmReportesGuardar",
		data : $("#modalReportes form").serialize(),
		dataType : "json",
		statusCode : {
			200 : function(data) {
				location.reload();
			},
			401 : function(res) {
				showError(res.responseText);
				redirectLogin();
			},
			500 : function(res) {
				showError(res.responseText);
			}
		}
	});
}