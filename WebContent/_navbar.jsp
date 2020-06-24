<div class="navbar navbar-default navbar-static-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#navbar-ex-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand"><span><img src="img/reportes.png"
					style="width: 25px; height: auto"></span> MisReportes</a>
		</div>
		<div class="collapse navbar-collapse" id="navbar-ex-collapse">
			<ul class="nav navbar-left navbar-nav">
				<li id="_menu4"><a href="dashboard" data-container="body" data-placement="bottom" data-toggle="tooltip" title="Tablero de control - Puede fijar 4 reportes que sean frecuentemente consultados">Dashboard</a></li>
				<li id="_menu1"><a href="reportes_online" data-container="body" data-placement="bottom" data-toggle="tooltip" title="Reportes con los datos del día con un periodo de actualización de 30 minutos. No posee datos históricos. (MICROS)">Online</a></li>
				<li id="_menu2"><a href="reportes_historial" data-container="body" data-placement="bottom" data-toggle="tooltip" title="Reportes con un rango de fechas determinado, posee datos históricos (MICROS)">Historial</a></li>
				<li id="_menu3"><a href="reportes_otros" data-container="body" data-placement="bottom" data-toggle="tooltip" title="Reportes que no hagan referencia a MICROS, con un rango de fecha determinado">Otros</a></li>

			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="logout" class="fa fa-power-off fa-2x signout" ></a></li>
			</ul>
			<p class="header-right navbar-right navbar-text hidden-xs">
				<a class="navbar-link MisAplicacionesBlack"></a>
			</p>
			
		</div>
	</div>
</div>
<script>
	var uri = 'http://172.31.1.26:29086/misaplicaciones/Aplicaciones';
	$(function() {
		$('[data-toggle="tooltip"]').tooltip()
	});
	
</script>