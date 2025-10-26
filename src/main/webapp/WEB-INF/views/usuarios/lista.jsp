<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@page import="java.util.List"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="com.tarefas.models.Usuario"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/template/header.jsp" />
</head>
<body>
	<c:import url="/WEB-INF/views/template/navbar.jsp" />
	<div class="container mt-4">
		<h2>Lista de usuários</h2>
		
		<c:if test="${not empty erro}">
		    <div class="alert alert-danger">${erro}</div>
		</c:if>
		<c:if test="${not empty sucesso}">
		    <div class="alert alert-success">${sucesso}</div>
		</c:if>
		
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Nome</th>
					<th>E-mail</th>
					<th>Perfil</th>
					<th>Cadastrado em</th>
					<th>Atualizado em</th>
					<th>Ativo</th>
					<th colspan="2"></th>
				</tr>
			</thead>
			<tbody>
				<%
					List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					for (Usuario u : usuarios) {
				%>
				<tr style="font-size:smaller;">
					<td><%=u.getNome()%></td>
					<td><%=u.getEmail()%></td>
					<td><%=u.getPerfil().getDescricao()%></td>
					<td><%=u.getDataCadastro().format(formatter)%></td>
					<td><%=u.getDataAtualizacao() != null ? u.getDataAtualizacao().format(formatter) : ""%></td>
					<td><%=u.isAtivo() ==  true ? "Sim" : "Não"%></td>
					<td><a href="usuario?acao=editar&id=<%=u.getId()%>" class="btn btn-outline-primary btn-sm">Editar</a></td>
					<td><a href="usuario?acao=excluir&id=<%=u.getId()%>" class="btn btn-outline-danger btn-sm"
						onclick="return confirm('Deseja realmente excluir esse registro?')">Excluir</a></td>
				</tr>
				
				<%
				}
				%>
			</tbody>
		</table>
	</div>
	<c:import url="/WEB-INF/views/template/footer.jsp" />
</body>
</html>