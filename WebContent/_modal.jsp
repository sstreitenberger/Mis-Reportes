<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title">Ops.. algo falló</h4>
			</div>
			<div class="modal-body"></div>
			<div class="modal-footer">
				<a class="btn btn-default" data-dismiss="modal">Cerrar</a>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="modalReportes" role="dialog">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form method="post" action="javascript:AbmReportesGuardar()">
				<input type="hidden" id="abmtipoRepo" name="abmtipoRepo" value="${abmRepoTipo }">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">Adminstración de Reportes</h4>
				</div>
				<div class="modal-body">
					<select multiple size="10" id="multiple" name="multiple">
					</select>
				</div>
				<div class="modal-footer">
					<a class="btn btn-default" data-dismiss="modal">Cerrar</a> 
					<input type="submit" class="btn btn-primary" value="Guardar Modificaciones">
				</div>
			</form>
		</div>
	</div>
</div>

<div class="modal" id="loading"
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

<script src="js/common.js"></script>