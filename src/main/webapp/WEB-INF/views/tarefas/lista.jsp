<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <c:import url="../template/header.jsp" />
</head>
<body>
    <c:import url="../template/navbar.jsp" />

    <div class="container mt-4">
        <h2>Minhas tarefas</h2>

        <div class="alinhamento-direito">
            <a class="btn btn-outline-success btn-sm" href="nova-tarefa">Adicionar Tarefa</a>
        </div>

        <c:forEach var="t" items="${tarefas}">
            <div class="card bg-light mb-3 shadow border-light">
                <div class="card-header" style="font-weight: bold">${t.titulo}</div>
                <div class="card-body">
                    <p class="card-text">${t.descricao}</p>
                    <small>${t.categoria.descricao} | Expira em: ${t.dataFormatada}</small>

                    <c:choose>
                        <c:when test="${not t.concluida}">
                            <div class="mt-2">
                                <a href="editar-tarefa?id=${t.id}" class="btn btn-outline-warning btn-sm">Editar</a>
                                <a href="excluir-tarefa?id=${t.id}" class="btn btn-outline-danger btn-sm"
                                   onclick="return confirm('Deseja realmente excluir esse registro?')">Excluir</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${t.dataFormatada != null}">
                                <div class="mt-2 text-success">
                                    Tarefa concluída em: ${t.dataFormatada}
                                </div>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:import url="../template/footer.jsp" />
</body>
</html>