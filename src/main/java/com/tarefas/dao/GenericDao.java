package com.tarefas.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.tarefas.exceptions.RegraNegocioException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;

public class GenericDao<T> {

	private final Class<T> classe;
	private EntityManager entityManager;
	
	public GenericDao(EntityManager entityManager, Class<T> classe) {
		this.classe = classe;
		this.entityManager = entityManager;
	}
	
	public void salvar(T tipo) throws RegraNegocioException {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(tipo);
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			
			throw new RegraNegocioException("Erro ao gravar no banco de dados: " + e.getMessage());
		}
	}
	
	public void atualizar(T tipo) throws RegraNegocioException {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(tipo);
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			
			throw new RegraNegocioException("Erro ao atualizar registro no banco de dados: " + e.getMessage());
		}
	}
	
	public void excluir(T tipo) throws RegraNegocioException {
		try {
			entityManager.getTransaction().begin();
			//T tipo = this.buscarPorId(id);
			entityManager.remove(entityManager.merge(tipo));
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			
			throw new RegraNegocioException("Erro ao excluir registro no banco de dados: " + e.getMessage());
		}
	}
	
	public List<T> listar() throws RegraNegocioException {
		List<T> lista = null;
		
		try {
			entityManager.getTransaction().begin();
			CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(classe);
			query.select(query.from(classe));
			lista = entityManager.createQuery(query).getResultList();
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			
			throw new RegraNegocioException("Erro ao listar registros: " + e.getMessage());
		}
		
		return lista;
	}
	
	public T buscarPorId(Integer id) throws RegraNegocioException {
		T tipo = null;
		
		try {
			entityManager.getTransaction().begin();
			tipo = entityManager.find(classe, id);
			entityManager.getTransaction().commit();
		} catch (HibernateException e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			
			throw new RegraNegocioException("Erro ao consultar registro no banco de dados: " + e.getMessage());
		}
		
		return tipo;
	}
}
