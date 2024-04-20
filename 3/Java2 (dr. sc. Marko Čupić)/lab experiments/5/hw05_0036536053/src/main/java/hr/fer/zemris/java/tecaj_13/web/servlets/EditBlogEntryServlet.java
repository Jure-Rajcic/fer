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

@WebServlet("/servleti/edit_blog_entry")
public class EditBlogEntryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EditServlet doGet");
        // sesija istekla?

        String nick = req.getParameter("nick");
        if (nick == null || req.getSession().getAttribute("current.user.id") == null ) {
            resp.sendRedirect(req.getContextPath() + "/servleti/main");
            return;
        }

        if (!nick.equals(req.getSession().getAttribute("current.user.nick"))) {
            sendErrorPage(req, resp, "You are not logged in as " + nick + ".");
        }

        Long blogEntryId = Long.parseLong(req.getParameter("blogEntryId"));
        req.setAttribute("nick", nick);
        req.setAttribute("blogEntryId", blogEntryId);
        req.getRequestDispatcher("/public/pages/edit_blog_entry.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("EditServlet doPost");
        req.setCharacterEncoding("UTF-8");

        String nick = req.getParameter("nick");
        System.out.println("nick: " + nick);
        if (nick == null || !req.getParameter("nick").equals(req.getSession().getAttribute("current.user.nick"))) {
            resp.sendRedirect(req.getContextPath() + "/servleti/main");
            return;
        }

        BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
        if (user == null) {
            String message = "User with nickname " + nick + " does not exist in database.";
            sendErrorPage(req, resp, message);
            return;
        }

        Long blogEntryId = Long.parseLong(req.getParameter("blogEntryId"));
        BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(blogEntryId);

        if (blogEntry == null) {
            String message = "Blog entry with id " + blogEntryId + " does not exist in database.";
            sendErrorPage(req, resp, message);
            return;
        }

        String title = req.getParameter("title");
        String text = req.getParameter("text");
        if (!(title == null && text == null)) {
            if (title != null) blogEntry.setTitle(title);
            if (text != null) blogEntry.setText(text);
            blogEntry.setLastModifiedAt(new Date());
            DAOProvider.getDAO().updateBlogEntry(blogEntry);
        }
        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick);
    }

    // sends client to error page with given message
    private void sendErrorPage(HttpServletRequest req, HttpServletResponse resp, String message) throws ServletException, IOException {
        req.setAttribute("message", message);
        req.getRequestDispatcher( "/public/pages/error.jsp").forward(req, resp);
        // resp.sendRedirect(req.getContextPath() + "/public/pages/error.jsp");
    }

	
}