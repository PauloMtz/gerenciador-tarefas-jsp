package com.tarefas.controllers;

import java.io.IOException;
import java.util.List;

import com.tarefas.dao.TarefaDao;
import com.tarefas.models.Tarefa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private TarefaDao tarefaDao;
	
	public IndexServlet() {
		EntityManager em = Persistence.createEntityManagerFactory("tarefas").createEntityManager();
		this.tarefaDao = new TarefaDao(em);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        String busca = request.getParameter("busca");
	        int pagina = 1;
	        int registrosPorPagina = 5;

	        if (request.getParameter("pagina") != null) {
	            pagina = Integer.parseInt(request.getParameter("pagina"));
	        }

	        int offset = (pagina - 1) * registrosPorPagina;
	        List<Tarefa> tarefas;
	        long totalRegistros;

	        if (busca != null && !busca.trim().isEmpty()) {
	            tarefas = tarefaDao.listarPorNomeUsuarioPaginado(busca, offset, registrosPorPagina);
	            totalRegistros = tarefaDao.contarPorNomeUsuario(busca);
	            request.setAttribute("busca", busca);
	        } else {
	            tarefas = tarefaDao.listarNaoConcluidasPaginado(offset, registrosPorPagina);
	            totalRegistros = tarefaDao.contarNaoConcluidas();
	        }

	        int totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);

	        request.setAttribute("tarefas", tarefas);
	        request.setAttribute("paginaAtual", pagina);
	        request.setAttribute("totalPaginas", totalPaginas);
	    } catch (Exception e) {
	        request.setAttribute("erro", "Erro ao carregar tarefas: " + e.getMessage());
	    }

	    request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
}
