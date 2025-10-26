package com.tarefas.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.HibernateException;

import com.tarefas.exceptions.RegraNegocioException;
import com.tarefas.models.Tarefa;
import com.tarefas.models.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class TarefaDao {

	private EntityManager entityManager;
    private GenericDao<Tarefa> dao;

    public TarefaDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.dao = new GenericDao<>(this.entityManager, Tarefa.class);
    }
    
    public void salvarTarefa(Tarefa tarefa) throws RegraNegocioException {
        this.dao.salvar(tarefa);
    }

    public void atualizarTarefa(Tarefa tarefa) throws RegraNegocioException {
        this.dao.atualizar(tarefa);
    }

    public void excluirTarefa(Integer id) throws RegraNegocioException {
    	Tarefa tarefa = this.buscarTarefa(id);
        this.dao.excluir(tarefa);
    }

    public List<Tarefa> listarTarefas() throws RegraNegocioException {
        return this.dao.listar();
    }
    
    // Lista todas as tarefas junto com o respectivo usuário
    public List<Tarefa> listarComUsuarios() {
        String jpql = "SELECT t FROM Tarefa t LEFT JOIN FETCH t.usuario";
        TypedQuery<Tarefa> query = entityManager.createQuery(jpql, Tarefa.class);
        return query.getResultList();
    }

    public Tarefa buscarTarefa(Integer id) throws RegraNegocioException {
        return this.dao.buscarPorId(id);
    }
    
    public List<Tarefa> listarPorUsuario(Usuario usuario) throws RegraNegocioException {
        try {
            String hql = "SELECT t FROM Tarefa t WHERE t.usuario = :usuario ORDER BY t.dataCriacao DESC";
            TypedQuery<Tarefa> query = entityManager.createQuery(hql, Tarefa.class);
            query.setParameter("usuario", usuario);
            return query.getResultList();
        } catch (HibernateException e) {
            throw new RegraNegocioException("Erro ao listar tarefas do usuário: " + e.getMessage());
        }
    }

    public void concluirTarefa(Integer id) throws RegraNegocioException {
        try {
            entityManager.getTransaction().begin();
            Tarefa tarefa = entityManager.find(Tarefa.class, id);
            if (tarefa != null) {
                tarefa.setConcluida(true);
                tarefa.setDataConclusao(LocalDateTime.now());
                entityManager.merge(tarefa);
            }
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RegraNegocioException("Erro ao concluir tarefa: " + e.getMessage());
        }
    }
    
    // métodos para lista e pesquisa com paginação
    public List<Tarefa> listarNaoConcluidasPaginado(int offset, int limit) {
        String jpql = "SELECT t FROM Tarefa t LEFT JOIN FETCH t.usuario WHERE t.concluida = false ORDER BY t.dataCriacao DESC";
        return entityManager.createQuery(jpql, Tarefa.class)
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
    }

    public List<Tarefa> listarPorNomeUsuarioPaginado(String nome, int offset, int limit) {
        String jpql = "SELECT t FROM Tarefa t LEFT JOIN FETCH t.usuario u WHERE LOWER(u.nome) LIKE :nome ORDER BY t.dataCriacao DESC";
        return entityManager.createQuery(jpql, Tarefa.class)
            .setParameter("nome", "%" + nome.toLowerCase() + "%")
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
    }

    public long contarNaoConcluidas() {
        String jpql = "SELECT COUNT(t) FROM Tarefa t WHERE t.concluida = false";
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }

    public long contarPorNomeUsuario(String nome) {
        String jpql = "SELECT COUNT(t) FROM Tarefa t WHERE LOWER(t.usuario.nome) LIKE :nome";
        return entityManager.createQuery(jpql, Long.class)
            .setParameter("nome", "%" + nome.toLowerCase() + "%")
            .getSingleResult();
    }
}
