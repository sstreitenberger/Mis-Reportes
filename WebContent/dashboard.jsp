<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="_header.jsp"></jsp:include>
<style>
.panel-body {
	height: 34vh;
/* 	line-height: 24vh; */
	text-align: center;
	overflow: auto;
}

.fa-edit {
	cursor: pointer
}
</style>
</head>
<body>

	<jsp:include page="_navbar.jsp"></jsp:include>

	<script src="js/dash1.js"></script>
	<script src="js/dash2.js"></script>

	<table id="tablahide1" class="hidden"></table>
	<table id="tablahide2" class="hidden"></table>
	<table id="tablahide3" class="hidden"></table>
	<table id="tablahide4" class="hidden"></table>

	<div class="section">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-6" id="div1">
					<div class="panel panel-default">
						<div class="panel-heading" id="titulo1">&nbsp;</div>
						<div class="panel-body" id="container1">
						</div>
					</div>
				</div>
				<div class="col-md-6" id="div2">
					<div class="panel panel-default">
						<div class="panel-heading" id="titulo2">&nbsp;</div>
						<div class="panel-body" id="container2">
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6" id="div3">
					<div class="panel panel-default">
						<div class="panel-heading" id="titulo3">&nbsp;</div>
						<div class="panel-body" id="container3">
						</div>
					</div>
				</div>
				<div class="col-md-6" id="div4">
					<div class="panel panel-default">
						<div class="panel-heading" id="titulo4">&nbsp;</div>
						<div class="panel-body" id="container4">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="dashModal">
		<form class="form-horizontal" >
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="dash_title">[modal-title]</h4>
						<input type="hidden" name="dash_orden" id="dash_orden" required>
						<input type="hidden" name="dash_id" id="dash_id" >
					</div>
					<div class="modal-body">
						<div class="form-group">
							<div class="col-sm-2">
								<label class="control-label">Tipo</label>
							</div>
							<div class="col-sm-10">
								<select name="dash_tipo" id="dash_tipo" class="form-control"
									required>

								</select>
							</div>
						</div>
						<div class="form-group" id="group_reporte">
							<div class="col-sm-2">
								<label class="control-label">Reporte</label>
							</div>
							<div class="col-sm-10">
								<select name="dash_reporte" id="dash_reporte"
									class="form-control" required>
									<option></option>
								</select>
							</div>
						</div>
						<div class="form-group" id="group_rango">
							<div class="col-sm-2">
								<label class="control-label">Rango</label>
							</div>
							<div class="col-sm-10">
								<select name="dash_rango" id="dash_rango" class="form-control">
								</select>
							</div>
						</div>
						<div class="form-group" id="group_tiendas">
							<div class="col-sm-2">
								<label class="control-label">Tiendas</label>
							</div>
							<div class="col-sm-10">
								<select multiple size="5" id="dash_tiendas" name="dash_tiendas"
									required>
								</select>
							</div>
						</div>
						<div class="form-group" id="group_formato">
							<div class="col-sm-2">
								<label class="control-label">Formato</label>
							</div>
							<div class="col-sm-10">
								<select name="dash_formato" id="dash_formato" class="form-control" required>
									<option value="tabla">Tabla</option>
									<option value="line" class="formato_to_hide">Lineal</option>
									<option value="column" class="formato_to_hide">Columnas</option>
									<option value="bar" class="formato_to_hide">Barra</option>
									<option value="pie" class="formato_to_hide">Torta</option>
									<option value="area" class="formato_to_hide">Area</option>
									<option value="areaspline" class="formato_to_hide">Area Spline</option>
									<option value="scatter" class="formato_to_hide">Scatter</option>
									<option value="spline" class="formato_to_hide">Spline</option>
								</select>
							</div>
						</div>
						<div class="form-group" id="group_alias">
							<div class="col-sm-2">
								<label class="control-label">Alias</label>
							</div>
							<div class="col-sm-10">
								<input type="text" max="50" class="form-control"
									name="dash_alias" id="dash_alias" placeholder="Alias (Opcional)">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<button type="button" class="btn btn-danger" id="btnEliminar">Eliminar</button>
						<button type="submit" class="btn btn-primary" id="btnGuardar">Guardar</button>
					</div>
				</div>
			</div>
		</form>
	</div>

	<jsp:include page="_modal.jsp"></jsp:include>
</body>
</html>