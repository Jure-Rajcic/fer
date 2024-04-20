package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Dohvaća usera sa zadanim <code>nickname</code>-om i <code>password</code>-om. Ako takav user ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param nickname nickname usera
	 * @param password password usera
	 * 
	 * @return user ili <code>null</code> ako user ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
    public BlogUser getBlogUser(String nickname) throws DAOException;

	/**
	 * Metoda provjera postoji li user sa zadanim <code>nickname</code>-om u bazi.
	 * 
	 * @param nick nickname usera
	 * 
	 * @return <code>true</code> ako postoji, <code>false</code> inače
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */

    public boolean isNickNameTaken(String nick) throws DAOException;

	/**
	 * Metoda dodaje novog usera u bazu.
	 * 
	 * @param user user kojeg treba dodati u bazu
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public void addBlogUser(BlogUser user) throws DAOException;

	/**
	 * Metoda dohvaća sve blogove usera. Ako user nema niti jedan blog, vraća se prazna lista.
	 * 
	 * @param user user čije blogove treba dohvatiti
	 * @return lista blogova usera
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
    public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException;


	/**
	 * Metoda dohvaća sve blogove usera. Ako user nema niti jedan blog, vraća se prazna lista.
	 * 
	 * @return lista blogova usera
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
    public List<BlogUser> getBlogUsers() throws DAOException;


	/**
	 * Metoda dodaje novi blog u bazu.
	 * 	
	 * @param entry blog kojeg treba dodati u bazu
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
    public void addBlogEntry(BlogEntry entry);


	/**
	 * Metoda ažurira blog u bazi.
	 * 
	 * @param blogEntry blog kojeg treba ažurirati u bazi
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
    public void updateBlogEntry(BlogEntry blogEntry) throws DAOException;


	/**
	 * Metoda dodaje novi komentar u bazu.
	 * 
	 * @param blogComment komentar kojeg treba dodati u bazu
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
    public void addBlogComment(BlogComment blogComment);
	
}