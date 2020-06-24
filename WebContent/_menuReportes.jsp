<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="leftsidenav" class="leftsidenav">
	<h4>
		Reportes <a class="pull-right" onclick="closeLeftNav()"> <i
			style="color: white; cursor: pointer"
			class="fa fa-fw fa-lg fa-times-circle text-primary"></i></a>
	</h4>
	<div data-toggle="buttons" id="listReportes" class="sieve">
		<c:forEach var="rep" items="${menulist }">
			<c:if test="${corterep != rep.getMarca()}">
				<c:set var="corterep" scope="session" value="${rep.getMarca() }" />
				<h4>${rep.getMarca() }</h4>
			</c:if>
			<label class="btn btn-info1 btn-block"> <input type="radio"
				autocomplete="off" name="reporte-selected" onchange="clickreporte()"
				id="${rep.getMarca() }_${rep.getId() }"> ${rep.getTitulo() } <i
				class="fa fa-fw fa-table"></i> <c:if test="${rep.getAplic_graf() }">
					<i class="fa fa-fw fa-bar-chart"></i>
				</c:if>
			</label>
		</c:forEach>
	</div>
	<h4 class="text-right">
		<a onclick="openMod()" class="btn btn-success"> <i
			class="fa fa-fw fa-lg fa-edit"></i> Editar reportes
		</a>
	</h4>
</div>
<div class="sticky_left" onclick="openLeftNav()">
	<span><i class="fa fa-fw fa-bar-chart"></i> </span>
</div>