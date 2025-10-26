<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="../template/header.jsp" />
</head>
<body>
	<c:import url="../template/navbar.jsp" />

	<div class="container">
		<h2>Cadastro de usuários</h2>

		<form action="usuario" method="post" class="p-4 bg-white shadow rounded">
			<c:if test="${not empty erro}">
				<div class="alert alert-danger">${erro}</div>
			</c:if>
			
			<h3 class="mb-3">Dados do usuário</h3>
			<input type="text" name="nome" class="form-control mb-2" placeholder="Nome"> 
			<input type="email" name="email" class="form-control mb-2" placeholder="E-mail"> 
			<input type="password" name="senha" class="form-control mb-3" placeholder="Senha">
			<button class="btn btn-success w-100">Cadastrar</button>
		</form>
	</div>
	<c:import url="../template/footer.jsp" />
</body>
</html>