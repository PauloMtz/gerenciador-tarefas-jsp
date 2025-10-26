<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="com.tarefas.models.Tarefa"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.time.LocalDateTime"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/template/header.jsp" />
</head>
<body>
	<c:import url="/WEB-INF/views/template/navbar.jsp" />
		
	<div class="container mt-4" style="min-height:450px">
		<h2>Tarefas</h2>
		
		<form method="get" action="home" class="d-flex justify-content-end mb-3">
		    <input type="text" name="busca" class="form-control w-25 me-2" placeholder="Buscar por usuário" value="${param.busca}">
		    <button type="submit" class="btn btn-primary">Buscar</button>
		</form>

		<c:if test="${not empty erro}">
			<div class="alert alert-danger">${erro}</div>
		</c:if>

		<c:if test="${empty tarefas}">
			<div class="alert alert-warning">Nenhuma tarefa encontrada.</div>
		</c:if>

		<table class="table table-striped">
			<thead>
				<tr>
					<th>Título</th>
					<th>Categoria</th>
					<th>Usuário</th>
					<!-- <th>Finalizado</th> -->
					<th>Cadastrado em</th>
					<!--<th>Concluído em</th>-->
					<th></th>
				</tr>
			</thead>
			<tbody>
				<%
					List<Tarefa> tarefas = (List<Tarefa>) request.getAttribute("tarefas");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					for (Tarefa t : tarefas) {
				%>
				<tr style="font-size:smaller;">
					<td><%=t.getTitulo()%></td>
					<td><%=t.getCategoria().getDescricao()%></td>
					<td><%=t.getUsuario().getNome()%></td>
					<!-- <td><%=t.isConcluida() ==  true ? "Sim" : "Não"%></td> -->
					<td><%=t.getDataCriacao().format(formatter)%></td>
					<!--<td><%=t.getDataConclusao() != null ? t.getDataConclusao().format(formatter) : ""%></td>-->
					<td>
					    <button type="button" class="btn btn-default btn-sm" data-bs-toggle="modal" 
					    	data-bs-target="#modal<%=t.getId()%>">Detalhes
					    </button>
					</td>
				</tr>
				<!-- Modal -->
				<div class="modal fade" id="modal<%=t.getId()%>" tabindex="-1" aria-labelledby="modalLabel<%=t.getId()%>" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="modalLabel<%=t.getId()%>">Detalhes da Tarefa</h5>
				        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
				      </div>
				      <div class="modal-body">
				        <p><strong>Título:</strong> <%=t.getTitulo()%></p>
				        <p><strong>Descrição:</strong> <%=t.getDescricao()%></p>
				        <p><strong>Usuário:</strong> <%=t.getUsuario().getNome()%></p>
				        <p><strong>E-mail:</strong> <%=t.getUsuario().getEmail()%></p>
				        <p><strong>Finalizado:</strong> <%=t.isConcluida() ? "Sim" : "Não"%></p>
				        <p><strong>Cadastrado em:</strong> <%=t.getDataCriacao().format(formatter)%></p>
				        <p><strong>Concluído em:</strong> <%=t.getDataConclusao() != null ? t.getDataConclusao().format(formatter) : "—"%></p>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal">Fechar</button>
				        <c:if test="${sessionScope.usuarioPerfil == 'TECN'}">
					        <a href="concluir-tarefa?id=<%=t.getId()%>" class="btn btn-success btn-sm">Concluir</a>
					    </c:if>
				      </div>
				    </div>
				  </div>
				</div>
				<%
				}
				%>
			</tbody>
		</table>
		<c:import url="/WEB-INF/views/template/paginacao.jsp" />
	</div>
	<c:import url="/WEB-INF/views/template/footer.jsp" />
</body>
</html>
