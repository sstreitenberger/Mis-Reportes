<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MisReportes</title>
<link rel=icon href="img/reportes.png" type="image/png" />

<script src="js/jquery-1.12.3.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="js/bootstrap.min.js"></script>

</head>

<body>

	<div class="container-fluid">
		<div class="pull-right login MisAplicacionesBlack"></div>
		<div class="row">
			<br>
			<div
				class="col-xs-12 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4">
				<form action="login" method="post">
					<div class="form-group text-center">
						<span><img src="img/reportes.png"
							class="img-responsive center-block" style="height: 200px"
							alt="Alsea"></span>
						<h2>MisReportes</h2>
					</div>
					<div class="form-group">
						<input type="text" id="username" class="form-control"
							name="username" placeholder="Usuário" required autofocus>
					</div>
					<div class="form-group">
						<input type="password" id="userpass" class="form-control"
							name="userpass" placeholder="Contraseña" required>
					</div>
					<div class="form-group">
						${invalida }
						<button class="btn btn-info btn-block" type="submit">Ingresar</button>
					</div>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-md-4 col-md-offset-4">
				<div class="row">
					<div class="col-xs-12">
						<img src="img/alsea.png" class="img-responsive center-block"
							alt="Alsea">
					</div>
				</div>
				<br> <br>
				<div class="row">
					<div class="col-xs-3">
					</div>
					<div class="col-xs-3">
						<img src="img/bk.png" class="img-responsive center-block"
							alt="Burger King">
					</div>
					<div class="col-xs-3">
						<img src="img/sb.png" class="img-responsive center-block"
							alt="Starbucks">
					</div>
					<div class="col-xs-3">
					</div>
					
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="loading" data-backdrop="static"
		data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true"
		style="padding-top: 15%; overflow-y: visible;">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-body">
					<div class="progress progress-striped active"
						style="margin-bottom: 0;">
						<div class="progress-bar" style="width: 100%"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		function showWait() {
			$('#loading').modal('show');
		}
		$('.MisAplicacionesBlack').load(
				'http://172.31.1.26:29086/misaplicaciones/Aplicaciones')
	</script>
</body>
</html>