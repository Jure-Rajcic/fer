package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public class JPADAOImpl implements DAO {


	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogEntry blogEntry = em.find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getBlogUser(String nickname) throws DAOException {
		EntityManager em  = JPAEMProvider.getEntityManager();
		try {
			return (BlogUser) em.createQuery("select bu from BlogUser as bu where bu.nick=:nickname")
						.setParameter("nickname", nickname)
						.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public boolean isNickNameTaken(String nick) throws DAOException {
		EntityManager em  = JPAEMProvider.getEntityManager();
		try {
			em.createQuery("select bu from BlogUser as bu where bu.nick=:nickname")
						.setParameter("nickname", nick)
						.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public void addBlogUser(BlogUser user) throws DAOException {
		EntityManager em  = JPAEMProvider.getEntityManager();
		em.persist(user);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException {
		EntityManager em  = JPAEMProvider.getEntityManager();
		try {
			return (List<BlogEntry>) em.createQuery("select be from BlogEntry as be where be.creator=:user")
						.setParameter("user", user)
						.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<>(0);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BlogUser> getBlogUsers() throws DAOException {
		EntityManager em  = JPAEMProvider.getEntityManager();
		try {
			return (List<BlogUser>) em.createQuery("select bu from BlogUser as bu")
						.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<>(0);
		}
	}
	

	@Override
	public void addBlogEntry(BlogEntry entry) {
		EntityManager em  = JPAEMProvider.getEntityManager();
		em.persist(entry);
	}

	@Override
	public void updateBlogEntry(BlogEntry blogEntry) throws DAOException {
		EntityManager em  = JPAEMProvider.getEntityManager();
		em.merge(blogEntry);
	}

	@Override
	public void addBlogComment(BlogComment blogComment) {
		EntityManager em  = JPAEMProvider.getEntityManager();
		em.persist(blogComment);
	}


}