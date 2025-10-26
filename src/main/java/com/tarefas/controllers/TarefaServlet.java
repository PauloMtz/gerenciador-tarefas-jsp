package com.tarefas.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.tarefas.dao.TarefaDao;
import com.tarefas.dao.UsuarioDao;
import com.tarefas.exceptions.RegraNegocioException;
import com.tarefas.models.Tarefa;
import com.tarefas.models.Usuario;
import com.tarefas.models.enums.Categoria;
import com.tarefas.models.enums.Perfil;
import com.tarefas.utils.JpaUtil;

@WebServlet({ "/minhas-tarefas", "/nova-tarefa", "/editar-tarefa", "/concluir-tarefa", "/excluir-tarefa" })
public class TarefaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private TarefaDao tarefaDao;
	private UsuarioDao usuarioDao;

	public TarefaServlet() {
		this.tarefaDao = new TarefaDao(JpaUtil.getEntityManager());
		this.usuarioDao = new UsuarioDao(JpaUtil.getEntityManager());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		HttpSession session = request.getSession(false);
		String email = (String) (session != null ? session.getAttribute("usuarioEmail") : null);

		if (email == null || session == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			Usuario usuario = usuarioDao.buscarUsuarioPorEmail(email);
			Perfil perfil = usuario.getPerfil();

			switch (path) {
				case "/minhas-tarefas":
					if (perfil != Perfil.USER) {
						response.sendRedirect(request.getContextPath() + "/home");
						return;
					}
					List<Tarefa> tarefas = tarefaDao.listarPorUsuario(usuario);
					
					// Formata dataConclusao como String
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					for (Tarefa t : tarefas) {
						if (t.getDataConclusao() != null) {
					        t.setDataFormatada(t.getDataConclusao().format(formatter));
					    } else {
					        t.setDataFormatada(t.getDataExpiracao().format(formatter));
					    }
					}
					
					request.setAttribute("tarefas", tarefas);
					request.setAttribute("categorias", Categoria.values());
					request.getRequestDispatcher("/WEB-INF/views/tarefas/lista.jsp").forward(request, response);
					break;
	
				case "/editar-tarefa":
					if (perfil != Perfil.USER) {
						response.sendRedirect(request.getContextPath() + "/home");
						return;
					}
					Integer idEditar = Integer.parseInt(request.getParameter("id"));
					Tarefa tarefaEditar = tarefaDao.buscarTarefa(idEditar);
					if (!tarefaEditar.getUsuario().getId().equals(usuario.getId())) {
						response.sendRedirect(request.getContextPath() + "/home");
						return;
					}
					request.setAttribute("tarefa", tarefaEditar);
					request.setAttribute("categorias", Categoria.values());
					request.getRequestDispatcher("/WEB-INF/views/tarefas/cadastro.jsp").forward(request, response);
					break;
	
				case "/concluir-tarefa":
					if (perfil != Perfil.TECN) {
						response.sendRedirect(request.getContextPath() + "/home");
						return;
					}
					Integer idConcluir = Integer.parseInt(request.getParameter("id"));
					tarefaDao.concluirTarefa(idConcluir);
					response.sendRedirect(request.getContextPath() + "/minhas-tarefas");
					break;
	
				case "/excluir-tarefa":
					if (perfil != Perfil.USER) {
						response.sendRedirect(request.getContextPath() + "/home");
						return;
					}
					Integer idExcluir = Integer.parseInt(request.getParameter("id"));
					Tarefa tarefa = tarefaDao.buscarTarefa(idExcluir);
					if (!tarefa.getUsuario().getId().equals(usuario.getId())) {
						response.sendRedirect(request.getContextPath() + "/home");
						return;
					}
					tarefaDao.excluirTarefa(idExcluir);
					response.sendRedirect(request.getContextPath() + "/minhas-tarefas");
					break;
	
				case "/nova-tarefa":
					if (perfil != Perfil.USER) {
						response.sendRedirect(request.getContextPath() + "/home");
						return;
					}
					request.setAttribute("categorias", Categoria.values());
					request.getRequestDispatcher("/WEB-INF/views/tarefas/cadastro.jsp").forward(request, response);
					break;
	
				default:
					response.sendRedirect(request.getContextPath() + "/home");
			}
		} catch (RegraNegocioException e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		HttpSession session = request.getSession(false);
		String email = (String) (session != null ? session.getAttribute("usuarioEmail") : null);
		if (email == null || session == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			Usuario usuario = usuarioDao.buscarUsuarioPorEmail(email);
			if (usuario.getPerfil() != Perfil.USER) {
				response.sendRedirect(request.getContextPath() + "/home");
				return;
			}
			
			if (idStr != null && !idStr.isBlank()) {
			    // Edição de tarefa
			    Integer id = Integer.parseInt(idStr);
			    Tarefa tarefaExistente = tarefaDao.buscarTarefa(id);

			    if (!tarefaExistente.getUsuario().getId().equals(usuario.getId())) {
			        response.sendRedirect(request.getContextPath() + "/home");
			        return;
			    }

			    tarefaExistente.setTitulo(request.getParameter("titulo"));
			    tarefaExistente.setDescricao(request.getParameter("descricao"));
			    tarefaExistente.setCategoria(Enum.valueOf(Categoria.class, request.getParameter("categoria")));
			    tarefaExistente.setDataExpiracao(LocalDateTime.parse(request.getParameter("dataExpiracao")));

			    tarefaDao.atualizarTarefa(tarefaExistente);
			} else {
			    // Cadastro de nova tarefa
			    Tarefa novaTarefa = new Tarefa();
			    novaTarefa.setTitulo(request.getParameter("titulo"));
			    novaTarefa.setDescricao(request.getParameter("descricao"));
			    novaTarefa.setCategoria(Enum.valueOf(Categoria.class, request.getParameter("categoria")));
			    novaTarefa.setDataExpiracao(LocalDateTime.parse(request.getParameter("dataExpiracao")));
			    novaTarefa.setUsuario(usuario);

			    tarefaDao.salvarTarefa(novaTarefa);
			}

			response.sendRedirect(request.getContextPath() + "/minhas-tarefas");
		} catch (RegraNegocioException e) {
			throw new ServletException(e);
		}
	}
}
