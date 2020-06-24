<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="rightsidenav" class="rightsidenav">
	<h4>
		Tiendas <a class="pull-right" onclick="closeRightNav()"> <i
			style="color: white; cursor: pointer"
			class="fa fa-fw fa-lg fa-times-circle text-primary"></i></a>
	</h4>
	<div class="sievet">

		<c:if test="${sessionScope.user.app_nivel == 20}">
			<label class="btn btn-info2 btn-block"
				onclick="selectallfull(this,true)"> <span
				class="fa fa-fw fa-square-o"></span> Seleccionar todos
			</label>
			<p></p>
		</c:if>
		
		<c:forEach items="${tiendas }" var="distrito">
			<c:forEach items="${distrito }" var="tienda" varStatus="count">
				<c:if test="${count.index == 0}">
					<a class="btn btn-primary btn-block" role="button"
						data-toggle="collapse" href="#list${tienda.getGrupo() }"
						aria-expanded="false" aria-controls="list${tienda.getGrupo() }">
						${tienda.getGrupo() } </a>
					<div class="collapse list-group" data-toggle="buttons" id="list${tienda.getGrupo() }">
						<label class="btn btn-info2 btn-block _saf"
							onclick="selectall(this,'list${tienda.getGrupo() }',true)"> <span
							class="fa fa-fw fa-square-o"></span> Todos
						</label>
				</c:if>
				<label class="btn btn-info2 btn-block"> <input
					type="checkbox" autocomplete="off"
					name="${tienda.getGrupo() }_${tienda.getIp() }"
					id="${tienda.getGrupo() }_${tienda.getIp() }"> ${tienda.getNombre() }
				</label>
				<c:if test="${count.last }">
					</div>
					<p></p>
				</c:if>
			</c:forEach>
		</c:forEach>
	</div>
</div>
<div class="sticky_right" onclick="openRightNav()">
	<span><i class="fa fa-fw fa-list-ul"></i> </span>
</div>