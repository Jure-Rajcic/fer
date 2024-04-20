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

import java.util.Date;

@WebServlet("/servleti/new_blog_entry")
public class NewBlogEntryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("NewServlet doGet");
        // sesija istekla?
        if (req.getSession().getAttribute("current.user.id") == null) {
            resp.sendRedirect(req.getContextPath() + "/servleti/main");
            return;
        }

        req.getRequestDispatcher("/public/pages/new_blog_entry.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String title = req.getParameter("title");
        String text = req.getParameter("text");
        String nick = (String) req.getSession().getAttribute("current.user.nick");

        if (nick == null) {
            resp.sendRedirect(req.getContextPath() + "/servleti/main");
            return;
        }

        BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
        if (user == null) {
            String message = "User with nickname " + nick + " does not exist in database.";
            sendErrorPage(req, resp, message);
            return;
        }

        if (title == null || title.trim().isEmpty()) {
            String message = "Title must not be empty.";
            sendErrorPage(req, resp, message);
            return;
        }

        if (text == null || text.trim().isEmpty()) {
            String message = "Text must not be empty.";
            sendErrorPage(req, resp, message);
            return;
        }

        BlogEntry entry = new BlogEntry();
        entry.setTitle(title);
        entry.setText(text);
        entry.setCreator(user);
        entry.setCreatedAt(new Date());
        entry.setLastModifiedAt(new Date());
        DAOProvider.getDAO().addBlogEntry(entry);
        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick);
    }

    // sends client to error page with given message
    private void sendErrorPage(HttpServletRequest req, HttpServletResponse resp, String message) throws ServletException, IOException {
        req.setAttribute("message", message);
        req.getRequestDispatcher( "/public/pages/error.jsp").forward(req, resp);
        // resp.sendRedirect(req.getContextPath() + "/public/pages/error.jsp");
    }

	
}