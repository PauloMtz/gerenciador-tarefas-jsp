<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!--<c:if test="${totalPaginas > 1}">
	<nav>
		<ul class="pagination pagination-sm" style="float: right">
			<c:forEach var="i" begin="1" end="${totalPaginas}">
				<c:url var="linkPaginacao" value="home">
					<c:param name="pagina" value="${i}" />
					<c:if test="${not empty param.busca}">
						<c:param name="busca" value="${fn:escapeXml(param.busca)}" />
					</c:if>
				</c:url>
				<li class="page-item ${i == paginaAtual ? 'active' : ''}"><a
					class="page-link" href="${linkPaginacao}">${i}</a></li>
			</c:forEach>
		</ul>
	</nav>
</c:if>-->

<c:if test="${totalPaginas > 1}">
	<ul class="pagination pagination-sm" style="float: right">
		<c:choose>
			<c:when test="${totalPaginas <= 10}">
				<c:if test="${paginaAtual > 1}">
					<li class="page-item">
						<a class="page-link" href="home?pagina=${paginaAtual - 1}${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">
							<img src="resources/img/previous3.png">
						</a>
					</li>
				</c:if>
				<c:forEach var="i" begin="1" end="${totalPaginas}">
					<li class="page-item ${i == paginaAtual ? 'active' : ''}">
						<a class="page-link" href="home?pagina=${i}${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">${i}</a>
					</li>
				</c:forEach>
				<c:if test="${paginaAtual < totalPaginas}">
					<li class="page-item">
						<a class="page-link" href="home?pagina=${paginaAtual + 1}${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">
							<img src="resources/img/next3.png">
						</a>
					</li>
				</c:if>
			</c:when>

			<c:otherwise>
				<c:set var="adjacentes" value="2" />
				<c:if test="${paginaAtual > 1}">
					<li class="page-item">
						<a class="page-link" href="home?pagina=${paginaAtual - 1}${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">
							<img src="resources/img/previous3.png">
						</a>
					</li>
				</c:if>

				<c:choose>
					<c:when test="${paginaAtual <= 4}">
						<c:forEach var="i" begin="1" end="4">
							<li class="page-item ${i == paginaAtual ? 'active' : ''}">
								<a class="page-link" href="home?pagina=${i}${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">${i}</a>
							</li>
						</c:forEach>
						<li class="page-item"><a class="page-link">...</a></li>
						<li class="page-item"><a class="page-link" href="home?pagina=${totalPaginas - 1}${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">${totalPaginas - 1}</a></li>
						<li class="page-item"><a class="page-link" href="home?pagina=${totalPaginas}${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">${totalPaginas}</a></li>
					</c:when>

					<c:when test="${paginaAtual > 4 && paginaAtual < (totalPaginas - 4)}">
						<li class="page-item"><a class="page-link" href="home?pagina=1${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">1</a></li>
						<li class="page-item"><a class="page-link" href="home?pagina=2${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">2</a></li>
						<li class="page-item"><a class="page-link">...</a></li>
						<c:forEach var="i" begin="${paginaAtual - adjacentes}" end="${paginaAtual + adjacentes}">
							<li class="page-item ${i == paginaAtual ? 'active' : ''}">
								<a class="page-link" href="home?pagina=${i}${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">${i}</a>
							</li>
						</c:forEach>
						<li class="page-item"><a class="page-link">...</a></li>
						<li class="page-item"><a class="page-link" href="home?pagina=${totalPaginas - 1}${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">${totalPaginas - 1}</a></li>
						<li class="page-item"><a class="page-link" href="home?pagina=${totalPaginas}${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">${totalPaginas}</a></li>
					</c:when>

					<c:otherwise>
						<li class="page-item"><a class="page-link" href="home?pagina=1${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">1</a></li>
						<li class="page-item"><a class="page-link" href="home?pagina=2${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">2</a></li>
						<li class="page-item"><a class="page-link">...</a></li>
						<c:forEach var="i" begin="${totalPaginas - 5}" end="${totalPaginas}">
							<li class="page-item ${i == paginaAtual ? 'active' : ''}">
								<a class="page-link" href="home?pagina=${i}${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">${i}</a>
							</li>
						</c:forEach>
					</c:otherwise>
				</c:choose>

				<c:if test="${paginaAtual < totalPaginas}">
					<li class="page-item">
						<a class="page-link" href="home?pagina=${paginaAtual + 1}${not empty param.busca ? '&busca=' + fn:escapeXml(param.busca) : ''}">
							<img src="resources/img/next3.png">
						</a>
					</li>
				</c:if>
			</c:otherwise>
		</c:choose>
	</ul>
</c:if>
