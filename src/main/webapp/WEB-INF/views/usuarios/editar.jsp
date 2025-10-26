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
		<h2>Atualizar usuário</h2>

		<form action="usuario" method="post" class="p-4 bg-white shadow rounded">
			<input type="hidden" name="id" value="${usuario.id}" />
			<c:if test="${not empty erro}">
				<div class="alert alert-danger">${erro}</div>
			</c:if>
			
			<h3 class="mb-3">Dados do usuário</h3>
			<div class="row">
				<div class="form-group col-md-6">
					<label>Nome</label>
					<input type="text" class="form-control" value="${usuario.nome}" name="nome" autofocus />
				</div>
				<div class="form-group col-md-6">
					<label>Descrição</label>
					<input type="text" class="form-control" value="${usuario.email}" name="email" />
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-6">
					<label>Perfil</label>
					<select name="perfil" class="form-select col" required>
						<option value="0">-- Selecione um perfil --</option>
						<c:forEach var="p" items="${perfis}">
							<option value="${p}" 
								<c:if test="${p.descricao == usuario.perfil.descricao}">selected="selected"</c:if>>
						        ${p.descricao}
						    </option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group col-md-6 checkbox" style="margin-top:35px">
					<label>
				      <input type="checkbox" name="ativo" value="true"
				      	<c:if test="${usuario.ativo.equals(true)}">checked="checked"</c:if>> Ativo
				    </label>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-6">
					<button class="btn btn-success w-50">Atualizar</button>
				</div>
			</div>
		</form>
	</div>
	<c:import url="../template/footer.jsp" />
</body>
</html>