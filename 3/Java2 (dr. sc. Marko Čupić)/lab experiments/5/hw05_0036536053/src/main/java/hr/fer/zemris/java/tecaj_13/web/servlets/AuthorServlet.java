package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo(); 
        if (pathInfo == null || pathInfo.trim().equals("/")) {
            handleNoPathInfo(req, resp);
            return;
        }

        String[] parts = pathInfo.substring(1).split("/");

        switch (parts.length) {
            case 1: 
                handleNick(req, resp, parts[0]);
                return;
            case 2:
                switch (parts[1]) {
                    case "new":
                        handleNew(req, resp, parts[0]);
                        return;
                    case "edit":
                        handleEdit(req, resp, parts[0]);
                        return;
                    default:
                        handleId(req, resp, parts[0], parts[1]);
                        return;
                }
            default:
                handleNoPathInfo(req, resp);
                return;
        }
	}


    // sends client to error page with given message
    private void sendErrorPage(HttpServletRequest req, HttpServletResponse resp, String message) throws ServletException, IOException {
        System.out.println("Sending error page with message: " + message);
        req.setAttribute("message", message);
        req.getRequestDispatcher( "/public/pages/error.jsp").forward(req, resp);
        // resp.sendRedirect(req.getContextPath() + "/public/pages/error.jsp");
    }

    // handle requests to /servleti/author/
    private void handleNoPathInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        System.out.println("Trying to get all authors...");
        List<BlogUser> authors = DAOProvider.getDAO().getBlogUsers();
        System.out.println("Got all authors.");
        if (authors.isEmpty()) {
            String message = "No authors in database.";
            sendErrorPage(req, resp, message);
            return;
        }
        req.setAttribute("authors", authors);
        req.getRequestDispatcher("/public/pages/authors.jsp").forward(req, resp);
    }

    // handle requests to /servleti/author/{nick}
    private void handleNick(HttpServletRequest req, HttpServletResponse resp, String nick) throws ServletException, IOException {
        BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
        if (user == null) {
            String message = "User with nickname " + nick + " does not exist in database.";
            sendErrorPage(req, resp, message);
            return;
        }

        List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(user);
        req.setAttribute("entries", entries);
        req.setAttribute("nick", nick);
        req.setAttribute("authorLoggedIn", nick.equals(req.getSession().getAttribute("current.user.nick")));
        req.getRequestDispatcher("/public/pages/author_entries.jsp").forward(req, resp);
    }
    

    // handle requests to /servleti/author/{nick}/{id}
    private void handleNew(HttpServletRequest req, HttpServletResponse resp, String nick) throws ServletException, IOException {
        if (req.getSession().getAttribute("current.user.nick") == null) {
            req.setAttribute("message", "Please login to create new blog entries");
            resp.sendRedirect(req.getContextPath() + "/servleti/main");
            return;
        }

        if (!nick.equals(req.getSession().getAttribute("current.user.nick"))) {
            String message = "You are not allowed to create blog entries for other users.";
            sendErrorPage(req, resp, message);
            return;
        }
        
        req.getRequestDispatcher("/servleti/new_blog_entry").forward(req, resp);
    }

     // handle requests to /servleti/author/{nick}/edit
     private void handleEdit(HttpServletRequest req, HttpServletResponse resp, String nick) throws ServletException, IOException {
        if (req.getSession().getAttribute("current.user.nick") == null) {
            req.setAttribute("message", "Please login to create new blog entries");
            resp.sendRedirect(req.getContextPath() + "/servleti/main");
            return;
        }

        if (!nick.equals(req.getSession().getAttribute("current.user.nick"))) {
            String message = "You are not allowed to create blog entries for other users.";
            sendErrorPage(req, resp, message);
            return;
        }
        
        req.getRequestDispatcher("/servleti/edit_blog_entry").forward(req, resp);
    }


    // handle requests to /servleti/author/{nick}/{id}
    private void handleId(HttpServletRequest req, HttpServletResponse resp, String nick, String id) throws ServletException, IOException{
        BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
        if (user == null) {
            String message = "User with nickname " + nick + " does not exist in database.";
            sendErrorPage(req, resp, message);
            return;
        }
        
        Long entryId = null;
        try {
            entryId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            String message = "Invalid entry id.";
            sendErrorPage(req, resp, message);
            return;
        }

        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
        if (entry == null) {
            String message = "Entry with id " + entryId + " does not exist in database.";
            sendErrorPage(req, resp, message);
            return;
        }

        // System.out.println("Session attributes:");
        // java.util.Iterator<String> it = req.getSession().getAttributeNames().asIterator();
        // while (it.hasNext()) {
        //     String name = it.next();
        //     System.out.println(name + " = " + req.getSession().getAttribute(name));
        // }

        req.setAttribute("entry", entry);
        req.setAttribute("nick", nick);
        req.setAttribute("authorLoggedIn", req.getSession().getAttribute("current.user.nick") != null);
        req.getRequestDispatcher("/public/pages/entry.jsp").forward(req, resp);
    }


}