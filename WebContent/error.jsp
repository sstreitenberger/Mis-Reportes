<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="_header.jsp"></jsp:include>
</head>
<body>

	<jsp:include page="_navbar.jsp"></jsp:include>

	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12 col-md-offset-3 col-md-6">
				<div class="alert alert-danger" role="alert">
					${error }
				</div>
				<a href="login" class="btn btn-default btn-block">Volver</a>
			</div>
		</div>
	</div>

</body>
</html>