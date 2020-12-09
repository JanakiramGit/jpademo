package com.jk.jpa.hibernatedemo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class TestConnection {

	private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
			.createEntityManagerFactory("jpademo");

	public static void main(String[] args) {
		
		//addCustomer(3, "Hibernate User2");
		
		getAllCustomers();
		//getCustomer(3);
		
		ENTITY_MANAGER_FACTORY.close();

	}
	
	public static void addCustomer(int id, String name) {
		EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
		
		EntityTransaction et = null;
		
		try {
			et = em.getTransaction();
			et.begin();
			
			Student st = new Student();
			st.setId(id);
			st.setName(name);
			
			em.persist(st);
			et.commit();
		}catch (Exception e) {
			if(et !=null) {
				et.rollback();
			}
			System.out.println(e.getMessage());
		}finally {
			em.close();
		}
	}
	
	public static void getCustomer(int id) {
		EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
		
		String query = "select s from Student s where s.id = :id";
		
		TypedQuery<Student> tq = em.createQuery(query, Student.class);
		tq.setParameter("id", id);
		
		Student st = null;
		

		try {
			st = tq.getSingleResult();
			System.out.println(st.getId());
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public static void getAllCustomers() {
		EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
		
		String query = "select s from Student s where s.id is not null";
		
		TypedQuery<Student> tq = em.createQuery(query, Student.class);
		
		
		List<Student> stList = null;
		

		try {
			stList = tq.getResultList();
			stList.forEach(st -> System.out.println(st.getName()));
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}
