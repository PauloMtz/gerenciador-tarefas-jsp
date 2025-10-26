<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container-fluid">
		<a class="navbar-brand" href="home"> 
			<img src="resources/img/navbar-icon.png"> GERENCIADOR DE TAREFAS
		</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarSupportedContent" aria-label="Toggle navigation"
			aria-controls="navbarSupportedContent" aria-expanded="false">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item active">
					<a class="nav-link" aria-current="page" href="home">Home</a>
				</li>
				<li class="nav-item active">
					<c:if test="${usuarioPerfil == null}">
					    <a class="nav-link" aria-current="page" href="usuario?acao=cadastro">Cadastre-se</a>
					</c:if>
				</li>
				<li class="nav-item active">
					<c:if test="${sessionScope.usuarioPerfil == 'USER'}">
					    <a class="nav-link" aria-current="page" href="minhas-tarefas">Tarefas</a>
					</c:if>
				</li>
				<li class="nav-item active">
					<c:if test="${sessionScope.usuarioPerfil == 'ADMIN'}">
					    <a class="nav-link" aria-current="page" href="usuario?acao=listar">Usuários</a>
					</c:if>
				</li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="nav-item active">
				  <c:choose>
				    <c:when test="${usuarioPerfil == null}">
				      <a class="nav-link" aria-current="page" href="login">Entrar</a>
				    </c:when>
				    <c:otherwise>
				      <a class="nav-link" aria-current="page" href="logout">Sair</a>
				    </c:otherwise>
				  </c:choose>
				</li>
			</ul>
		</div>
	</div>
</nav>