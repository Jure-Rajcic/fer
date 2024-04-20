package hr.fer.zemris.java.tecaj_13.console;

import java.util.ArrayList;
import java.util.Date;
// import java.util.List;
import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Example1 {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");  

		List<BlogUser> blogUsers = new ArrayList<>();
		blogUsers.add(new BlogUser("Pero", "Perić", "perica", "pp@some.com", Util.calculate_hexEncode("perica")));
		blogUsers.add(new BlogUser("Jure", "Rajcic", "jure", "jr53605@fer.hr", Util.calculate_hexEncode("jure")));
		blogUsers.add(new BlogUser("Marko", "Cupic", "marko", "mc@fer.hr", Util.calculate_hexEncode("marko")));
		blogUsers.forEach(user -> dodajKorisnika(emf, user));

		List<BlogEntry> blogEntries = new ArrayList<>();
		blogEntries.add(new BlogEntry("Pero: prvi zapis", "Ovo je Perin prvi zapis.", blogUsers.get(0), new Date()));
		blogEntries.add(new BlogEntry("Pero: drugi zapis", "Ovo je Perin drugi zapis.", blogUsers.get(0), new Date()));
		blogEntries.add(new BlogEntry("Pero: treci zapis", "Ovo je Perin treci zapis.", blogUsers.get(0), new Date()));
		blogEntries.add(new BlogEntry("Jure prvi zapis", "Ovo je Jurin prvi zapis.", blogUsers.get(1), new Date()));
		blogEntries.add(new BlogEntry("Jure drugi zapis", "Ovo je Jurin drugi zapis.", blogUsers.get(1), new Date()));
		blogEntries.add(new BlogEntry("Marko prvi zapis", "Ovo je Markov prvi zapis.", blogUsers.get(2), new Date()));
		blogEntries.forEach(entry -> dodajZapis(emf, entry));

		// List<BlogComment> blogComments = new ArrayList<>();
		// int n = blogEntries.size() * 2;
		// for (int i = 0; i < n; i++) {
		// 	int blogEntryIndex = ((int) Math.random() * (n + 1)) % blogEntries.size();
		// 	BlogEntry blogEntry = blogEntries.get(blogEntryIndex);
		// 	String email = Util.calculate_hexEncode("" + i).substring(0, 10) + "@mail.com";
		// 	BlogComment blogComment = new BlogComment(blogEntry, email, "Ovo je komentar broj " + i, new Date());
		// 	blogEntry.getComments().add(blogComment);
		// 	blogComments.add(blogComment);
		// }

		// blogComments.forEach(comment -> dodajKomentar(emf, comment));
		
		

		// System.out.println("Primjer upita.");
		// primjerUpita(emf, blogEntryID);
		// System.out.println("Primjer upita - gotovo.");

		// try { Thread.sleep(1000); } catch(InterruptedException ex) {}
		
		// System.out.println("Primjer upita 2.");
		// primjerUpita2(emf, blogEntryID);
		// System.out.println("Primjer upita 2 - gotovo.");
		
		emf.close();
	}

	public static BlogUser dodajKorisnika(EntityManagerFactory emf, BlogUser blogUser) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(blogUser);
		em.getTransaction().commit();
		em.close();
		
		return blogUser;
		
	}

	public static BlogEntry dodajZapis(EntityManagerFactory emf, BlogEntry blogEntry) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(blogEntry);
		em.getTransaction().commit();
		em.close();
		return blogEntry;
	}


    public static void dodajKomentar(EntityManagerFactory emf, BlogComment blogComment) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		// Long blogEntryID
		// BlogEntry blogEntry = em.find(BlogEntry.class, blogEntryID);
		
		em.persist(blogComment);
		
		em.getTransaction().commit();
		em.close();
	}


    // /**
	//  * Uporaba jezika JPA-QL
	//  * @param emf
	//  * @param blogEntryID
	//  */
	// private static void primjerUpita(EntityManagerFactory emf, Long blogEntryID) {
	// 	EntityManager em = emf.createEntityManager();
	// 	em.getTransaction().begin();
		
	// 	BlogEntry blogEntry = em.find(BlogEntry.class, blogEntryID);

	// 	@SuppressWarnings("unchecked")
	// 	List<BlogComment> comments = 
	// 			(List<BlogComment>)em.createQuery("select b from BlogComment as b where b.blogEntry=:be")
	// 				.setParameter("be", blogEntry)
	// 				.getResultList();
		
	// 	for(BlogComment bc : comments) {
	// 		System.out.println("Blog ["+bc.getBlogEntry().getTitle()+"] ima komentar: ["+bc.getMessage()+"]");
	// 	}
		
	// 	em.getTransaction().commit();
	// 	em.close();
	// }

    // /**
	//  * Uporaba jezika JPA-QL
	//  * @param emf
	//  * @param blogEntryID
	//  */
	// private static void primjerUpita2(EntityManagerFactory emf, Long blogEntryID) {
	// 	EntityManager em = emf.createEntityManager();
	// 	em.getTransaction().begin();
		
	// 	BlogEntry blogEntry = em.find(BlogEntry.class, blogEntryID);

	// 	@SuppressWarnings("unchecked")
	// 	List<BlogComment> comments = 
	// 			(List<BlogComment>)em.createNamedQuery("BlogEntry.upit1")
	// 				.setParameter("be", blogEntry)
	// 				.setParameter("when", new Date(new Date().getTime() - 2000))
	// 				.getResultList();
		
	// 	for(BlogComment bc : comments) {
	// 		System.out.println("Blog ["+bc.getBlogEntry().getTitle()+"] ima komentar: ["+bc.getMessage()+"]");
	// 	}
		
	// 	em.getTransaction().commit();
	// 	em.close();
	// }
}
