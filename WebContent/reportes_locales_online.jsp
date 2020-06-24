<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
 	<jsp:include page="_header.jsp"></jsp:include>
</head>
<body>

	<jsp:include page="_menuReportes.jsp"></jsp:include>
	<jsp:include page="_navbar.jsp"></jsp:include>

	<script>
		$('#_menu1').addClass('active');
	</script>

		<label class="hidden"> <input type="checkbox"
			class="tiendainfo" id="${tienda.ip }"> ${tienda.nombre }
		</label>

	<div class="container-fluid" >
		
		<div class="row">
			<div class="col-xs-12 col-lg-8 col-lg-offset-2">
				<div class="row text-center" id="instrutivo">
						<div class="col-xs-12" >
						<h3>Elija el reporte</h3>
						<span class="fa fa-area-chart" style="font-size: 15vw"
							aria-hidden="true"></span>
					</div>
				</div>
			</div>
		</div>
		
		
		<div id="title_repo"></div>
		<div class="alert alert-dismissable alert-info hide">
				El reporte a sido limitado a 1000 lineas para evitar la sobrecarga. 
				Se recomienda exportar el reporte haciendo 
				<strong><a style="cursor:pointer" onclick="getFullReporte()">click aqui</a></strong> 
				para obtener el reporte completo.</div>
		<div class="row">
			<div class="col-lg-12 " id="chart_div_to_hide">
				<div id="chart-div">
					<div id="container" class="chartContainer"></div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12 " id="table_div_to_hide"
				style="overflow-x: auto">
				<div id="table-div"></div>
				<div class="hidden" id="hide-table-div"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<div id="menu" class="hidden">
					<hr>
					<div class="col-lg-4">
						<select class="btn btn-default btn-block" name="selectChart"
							id="selectChart" onchange="changeChart();">
							<option>Cambiar gráfico...</option>
							<option value="line">Lineal</option>
							<option value="column">Columnas</option>
							<option value="bar">Barra</option>
							<option value="pie">Torta</option>
							<option value="area">Area</option>
							<option value="areaspline">Area Spline</option>
							<option value="scatter">Scatter</option>
							<option value="spline">Spline</option>
						</select> <br>
					</div>
					<div class="col-lg-4">
						<a id="btn123" class="btn btn-warning btn-block"
							onclick="hideChart(this);">Ocultar gráfico</a> <br>
					</div>
					<div class="col-lg-4">
						<a id="btn234" class="btn btn-warning btn-block"
							onclick="hideTable(this);">Ocultar tabla</a> <br>
					</div>

				</div>
			</div>
		</div>
	</div>

	<jsp:include page="_modal.jsp"></jsp:include>

</body>
</html>