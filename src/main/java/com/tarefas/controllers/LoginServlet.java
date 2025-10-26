package com.tarefas.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.tarefas.dao.UsuarioDao;
import com.tarefas.exceptions.RegraNegocioException;
import com.tarefas.models.Usuario;
import com.tarefas.utils.JpaUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UsuarioDao usuarioDao;
	
	public LoginServlet() {
		this.usuarioDao = new UsuarioDao(JpaUtil.getEntityManager());
    }
  
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if (session != null && session.getAttribute("usuarioEmail") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
		
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {
            if (usuarioDao.confirmarUsuario(email, senha)) {
            	Usuario usuario = usuarioDao.buscarUsuarioPorEmail(email);
                HttpSession session = request.getSession();
                session.setAttribute("usuarioEmail", email);
                session.setAttribute("usuarioPerfil", usuario.getPerfil());
                session.setAttribute("usuarioId", usuario.getId());
                response.sendRedirect(request.getContextPath() + "/minhas-tarefas");
            } else {
            	request.setAttribute("erro", "E-mail ou senha inválidos.");
            	request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        } catch (RegraNegocioException e) {
            throw new ServletException(e);
        }
    }
}
