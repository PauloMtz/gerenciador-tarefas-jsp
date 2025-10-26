package com.tarefas.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.tarefas.dao.UsuarioDao;
import com.tarefas.exceptions.RegraNegocioException;
import com.tarefas.models.Usuario;
import com.tarefas.models.enums.Perfil;
import com.tarefas.utils.AutorizacaoUtil;
import com.tarefas.utils.JpaUtil;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private UsuarioDao usuarioDao;

    public UsuarioServlet() {
    	this.usuarioDao = new UsuarioDao(JpaUtil.getEntityManager());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        String acao = request.getParameter("acao");
        HttpSession session = request.getSession(false);

        try {
            switch (acao) {
                case "cadastro":
            		
            		if (session != null && session.getAttribute("usuarioEmail") != null) {
                        response.sendRedirect(request.getContextPath() + "/home");
                        return;
                    }
            		
                    request.getRequestDispatcher("/WEB-INF/views/usuarios/cadastro.jsp").forward(request, response);
                    break;
                case "editar":
                    Integer id = Integer.parseInt(request.getParameter("id"));
                    Usuario usuario = usuarioDao.buscarUsuarioPorId(id);
                    request.setAttribute("usuario", usuario);
                    request.setAttribute("perfis", Perfil.values());
                    request.getRequestDispatcher("/WEB-INF/views/usuarios/editar.jsp").forward(request, response);
                    break;
                case "listar":
                	if (!AutorizacaoUtil.temPerfil(request, Perfil.ADMIN)) {
                	    response.sendRedirect(request.getContextPath() + "/home");
                	    return;
                	}
                	
                	String sucesso = (String) session.getAttribute("sucesso");
                	if (sucesso != null) {
                	    request.setAttribute("sucesso", sucesso);
                	    session.removeAttribute("sucesso");
                	}
                	
                    List<Usuario> usuarios = usuarioDao.listarUsuarios();
                    request.setAttribute("usuarios", usuarios);
                    request.getRequestDispatcher("/WEB-INF/views/usuarios/lista.jsp").forward(request, response);
                    break;
                case "excluir":
                	try {
                	    Integer idExcluir = Integer.parseInt(request.getParameter("id"));
                	    usuarioDao.excluirUsuario(idExcluir);
                	    request.getSession().setAttribute("sucesso", "Usuário excluído com sucesso");
                	    response.sendRedirect(request.getContextPath() + "/usuario?acao=listar");
                	    return;
                	} catch (RegraNegocioException e) {
                	    usuarios = usuarioDao.listarUsuarios();
                	    request.setAttribute("usuarios", usuarios);
                	    request.setAttribute("erro", e.getMessage());
                	    request.getRequestDispatcher("/WEB-INF/views/usuarios/lista.jsp").forward(request, response);
                	}
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/home");
            }
        } catch (RegraNegocioException | NumberFormatException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/erro.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        String idStr = request.getParameter("id");
        Usuario usuario = new Usuario();
        usuario.setNome(request.getParameter("nome"));
        usuario.setEmail(request.getParameter("email"));

        try {
            if (idStr == null || idStr.isBlank()) {
            	usuario.setSenha(request.getParameter("senha"));
                usuario.setDataCadastro(LocalDate.now());
                usuarioDao.salvarUsuario(usuario);
                request.getSession().setAttribute("sucesso", "Usuário cadastrado com sucesso");
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
            	Integer id = Integer.parseInt(idStr);
                Usuario usuarioExistente = usuarioDao.buscarUsuarioPorId(id);
                usuario.setId(id);
                usuario.setSenha(usuarioExistente.getSenha());
                usuario.setAtivo(Boolean.parseBoolean(request.getParameter("ativo")));
                usuario.setPerfil(Enum.valueOf(Perfil.class, request.getParameter("perfil")));
                usuario.setDataCadastro(usuarioExistente.getDataCadastro());
                usuario.setDataAtualizacao(LocalDate.now());
                usuarioDao.atualizarUsuario(usuario);
                request.getSession().setAttribute("sucesso", "Usuário atualizado com sucesso");
                response.sendRedirect(request.getContextPath() + "/usuario?acao=listar");
            }
        } catch (RegraNegocioException e) {
            request.setAttribute("erro", e.getMessage());
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
