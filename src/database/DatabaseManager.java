package database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseManager {
	
	private static DatabaseManager dpm;
	private EntityManagerFactory emf;
//	private ObservableList<DecisionProcessType> dptList;
	
	private DatabaseManager(){
		
//		dptList = FXCollections.observableArrayList();
	}
	
	public static DatabaseManager getInstance(){
		
		if(dpm == null){
			
			dpm = new DatabaseManager();
		}
		return dpm;
	}
	
	public EntityManagerFactory getEmf() {
		
		if(emf == null){
			
			System.out.println("ausführen Datenbank write");
			emf = Persistence.createEntityManagerFactory("database/projectDB.odb");
		}
		return emf;
	}
	
	public void merge(Object o) {
		
		EntityManager em = getEmf().createEntityManager();
		em.getTransaction().begin();
		em.merge(o);
		em.getTransaction().commit();
		em.close();
	}
	
	public void persistDpt(Object o, EntityManager em) {
		
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
		em.close();
	}
	
	public void persistType(Object o) {
		
		EntityManager em = getEmf().createEntityManager();
		em.getTransaction().begin();
		em.merge(o);
		em.getTransaction().commit();
		em.close();
	}
	
	public void remove(Object o) {
		
		EntityManager em = getEmf().createEntityManager();
		em.getTransaction().begin();
		em.remove(o);
		em.getTransaction().commit();
		em.close();
	}
//	public ObservableList<DecisionProcessType> getDptList() {
//		
//		return dptList;
//	}
//
//	public void setDptList(ObservableList<DecisionProcessType> dptList) {
//	
//		this.dptList = dptList;
//	}
}
