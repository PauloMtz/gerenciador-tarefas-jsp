package com.tarefas.utils;

import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;

import com.tarefas.models.Tarefa;
import com.tarefas.models.Usuario;
import com.tarefas.models.enums.Categoria;

import jakarta.persistence.EntityManager;

public class DadosTeste {

	/*
	 * não roda no servidor
	 * para rodar - clique com botão direito - Run As - Java Application
	 */
	
	public static void main(String[] args) {
		
		try {
			new JpaUtil();
			EntityManager manager = JpaUtil.getEntityManager();
			manager.getTransaction().begin();
			
			System.out.println("\n>>> Gerando as tabelas...");
			
			Usuario usuario = new Usuario("José Teste", "jose.teste@email.com", BCrypt.hashpw("123", BCrypt.gensalt()));
			manager.persist(usuario);
			
			Tarefa tarefa = new Tarefa("Tarefa teste", "Descrição da tarefa", Categoria.LIMPEZA, LocalDateTime.now(), usuario);
			manager.persist(tarefa);
			
			System.out.println("\n>>> Gravou registros...");
			
			manager.getTransaction().commit();
			manager.close();
			
			System.out.println("\n>>> Finalizando!\n");
			
			System.exit(0);
			manager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("\n>>> Erro ao gerar tabela:\n" + e.getMessage() + "\n");
		}
	}
}
