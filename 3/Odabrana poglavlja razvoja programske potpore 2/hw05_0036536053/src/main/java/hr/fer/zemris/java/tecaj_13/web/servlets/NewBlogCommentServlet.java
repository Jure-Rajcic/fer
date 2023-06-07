package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

@WebServlet("/servleti/new_blog_comment")
public class NewBlogCommentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String nick = (String) req.getSession().getAttribute("current.user.nick");
        if (nick == null) {
            resp.sendRedirect(req.getContextPath() + "/servleti/main");
            return;
        }

        String comment = req.getParameter("comment");
        if (comment == null || comment.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick);
            return;
        }

        BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
        if (user == null) {
            String message = "User with nickname " + nick + " does not exist in database.";
            sendErrorPage(req, resp, message);
            return;
        }

        Long entryID = Long.parseLong(req.getParameter("entryID"));
        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);
        if (entry == null) {
            String message = "Blog entry with id " + entryID + " does not exist in database.";
            sendErrorPage(req, resp, message);
            return;
        }

        BlogComment blogComment = new BlogComment();
        blogComment.setBlogEntry(entry);
        blogComment.setMessage(comment);
        blogComment.setPostedOn(new Date());
        blogComment.setUsersEMail(user.getEmail());

        DAOProvider.getDAO().addBlogComment(blogComment);
        resp.sendRedirect(req.getContextPath() + "/servleti/author/" + nick + "/" + entryID);
    }

    // sends client to error page with given message
    private void sendErrorPage(HttpServletRequest req, HttpServletResponse resp, String message) throws ServletException, IOException {
        req.setAttribute("message", message);
        req.getRequestDispatcher( "/public/pages/error.jsp").forward(req, resp);
        // resp.sendRedirect(req.getContextPath() + "/public/pages/error.jsp");
    }

	
}