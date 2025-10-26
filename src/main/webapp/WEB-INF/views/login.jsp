<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<c:import url="template/header.jsp" />
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
          <a class="navbar-brand" href="index.jsp">
          	<img src="resources/img/navbar-icon.png">
            GERENCIADOR DE TAREFAS
          </a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" 
            aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item active">
                  <a class="nav-link" aria-current="page" href="home">Home</a>
                </li>
              </ul>
          </div>
        </div>
      </nav>

      <div class="container">
        <h2>Login</h2>
        
		<c:if test="${not empty erro}">
		    <div class="alert alert-danger">${erro}</div>
		</c:if>
		<c:if test="${not empty sucesso}">
		    <div class="alert alert-success">${sucesso}</div>
		</c:if>
		
        <form action="login" method="POST">
            <div class="row">
                <div class="form-group col-md-6">
                    <label>E-mail</label><!-- ESTE CAMPO DEVE SER USERNAME -->
                    <input type="text" class="form-control" placeholder="E-mail" name="email" />
                </div>
            </div>

            <div class="row">
                <div class="form-group col-md-6">
                    <label>Senha</label>
                    <input type="password" class="form-control" placeholder="Senha" name="senha" id="senha" />
                    <div style="margin-top:10px">
                      <input class="form-check-input" type="checkbox" onclick="showPass()"> Mostrar Senha
                    </div>
                </div>
            </div>

            <div class="row">
              <div class="form-group col-md-12">
                <input type="submit" name="enviar" value="Entrar"class="btn btn-outline-primary" />
              </div>
            </div>

            <div class="row">
              <div class="form-group col-md-12">
                <span>Não tem cadastro? <a href="usuario?acao=cadastro"> Cadastre-se </a></span>
              </div>
            </div>
        </form>
      </div>

      <c:import url="template/footer.jsp" />

      <script type="text/javascript">
        function showPass() {
          var campo = document.getElementById("senha");
          if (campo.type === "password") {
            campo.type = "text";
          } else {
            campo.type = "password";
          }
        } 
      </script>
</body>
</html>