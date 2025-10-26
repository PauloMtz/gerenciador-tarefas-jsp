<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<c:import url="../template/header.jsp" />
</head>
<body>
	<c:import url="../template/navbar.jsp" />
	
	<div class="container mt-4">
		<h2>
			<c:choose>
                <c:when test="${not empty tarefa.id}">Atualizar Tarefa</c:when>
                <c:otherwise>Nova Tarefa</c:otherwise>
            </c:choose>
		</h2>

		<div class="alinhamento-direito">
			<a class="btn btn-outline-success btn-sm" href="minhas-tarefas">Minhas Tarefas</a>
		</div>

		<form action="nova-tarefa" method="post">
			<!-- <input type="hidden" name="acao" value="cadastro"> -->
			<input type="hidden" name="id" value="${tarefa.id}" />
			<div class="row">
				<div class="form-group col-md-6">
					<label>Título</label>
					<input type="text" class="form-control" value="${tarefa.titulo}" placeholder="Título da tarefa" name="titulo" autofocus />
				</div>
				<div class="form-group col-md-6">
					<label>Descrição</label>
					<input type="text" class="form-control" value="${tarefa.descricao}" placeholder="Descrição da tarefa" name="descricao" />
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-6">
					<label>Categoria</label>
					<select name="categoria" class="form-select col" required>
						<option value="0">-- Selecione uma categoria --</option>
						<c:forEach var="c" items="${categorias}">
							<option value="${c}"
								<c:if test="${c.descricao == tarefa.categoria.descricao}">selected="selected"</c:if>
								>${c.descricao}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group col-md-6">
					<label>Data de Expiração</label>
					<input type="datetime-local" name="dataExpiracao" value="${tarefa.dataExpiracao}" class="form-control col" required>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-8">
					<!-- <button class="btn btn-primary col">Adicionar</button> -->
					<button class="btn btn-outline-primary btn-sm">
		                <c:choose>
		                    <c:when test="${not empty tarefa.id}">Atualizar</c:when>
		                    <c:otherwise>Adicionar</c:otherwise>
		                </c:choose>
		            </button>
				</div>
			</div>
		</form>
	</div>
	
	<c:import url="../template/footer.jsp" />
</body>
</html>