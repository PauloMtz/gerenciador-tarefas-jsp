<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<c:import url="template/header.jsp" />
</head>
<body>
	<c:import url="template/navbar.jsp" />

      <div class="container">
        <h2>Página não encontrada</h2>
	    <p><a href="${pageContext.request.contextPath}/home">Voltar para a página inicial</a></p>
      </div>

      <c:import url="template/footer.jsp" />
</body>
</html>