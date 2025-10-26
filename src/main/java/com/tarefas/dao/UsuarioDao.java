package com.tarefas.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.tarefas.exceptions.RegraNegocioException;
import com.tarefas.models.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class UsuarioDao {

	EntityManager entityManager;
	private GenericDao<Usuario> dao;

	public UsuarioDao(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.dao = new GenericDao<>(this.entityManager, Usuario.class);
	}

	public void salvarUsuario(Usuario usuario) throws RegraNegocioException {
		if (buscarUsuarioPorEmail(usuario.getEmail()) != null) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail.");
		}
		
		if (usuario.getNome() == null || usuario.getNome().isBlank()) {
			throw new RegraNegocioException("O nome é obrigatório.");
		}
		if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
			throw new RegraNegocioException("O e-mail é obrigatório.");
		}
		if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
			throw new RegraNegocioException("A senha é obrigatória.");
		}
		
		this.dao.salvar(usuario);
	}

	public void atualizarUsuario(Usuario usuario) throws RegraNegocioException {
		this.dao.atualizar(usuario);
	}

	public void excluirUsuario(Integer id) throws RegraNegocioException {
		if (possuiTarefas(id)) {
	        throw new RegraNegocioException("Não é possível excluir o usuário. Existem tarefas associadas a ele.");
	    }
		
		Usuario usuario = this.buscarUsuarioPorId(id);
		this.dao.excluir(usuario);
	}

	public List<Usuario> listarUsuarios() throws RegraNegocioException {
		//return this.dao.listar();
		String jpql = "SELECT u FROM Usuario u ORDER BY u.nome ASC";
        TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class);
        return query.getResultList();
	}

	public Usuario buscarUsuarioPorId(Integer id) throws RegraNegocioException {
		return this.dao.buscarPorId(id);
	}

	public Usuario buscarUsuarioPorEmail(String email) throws RegraNegocioException {
		try {
			TypedQuery<Usuario> q = entityManager.createQuery("select u from Usuario u where u.email = :e",
					Usuario.class);
			q.setParameter("e", email);
			return q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		} catch (HibernateException he) {
			throw new RegraNegocioException("Erro: " + he.getMessage());
		}
	}
	
	public boolean possuiTarefas(Integer usuarioId) {
	    String jpql = "SELECT COUNT(t) FROM Tarefa t WHERE t.usuario.id = :usuarioId";
	    Long total = entityManager.createQuery(jpql, Long.class)
              .setParameter("usuarioId", usuarioId)
              .getSingleResult();
	    return total > 0;
	}

	public boolean confirmarUsuario(String email, String senha) throws RegraNegocioException {
		String hql = "select u from Usuario u where u.email = :email and u.ativo = true";

		try {
			TypedQuery<Usuario> query = entityManager.createQuery(hql, Usuario.class);
			query.setParameter("email", email);
			Usuario usuario = query.getSingleResult();

			if (usuario != null && usuario.verificarSenha(senha)) {
				return true;
			}

		} catch (NoResultException e) {
			return false;
		} catch (HibernateException e) {
			throw new RegraNegocioException("Erro ao buscar usuário:\n" + e.getMessage());
		}

		return false;
	}
}
