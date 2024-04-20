package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.Util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MainServlet doPost");
		req.setCharacterEncoding("UTF-8");
		String nickname = req.getParameter("nick");
		String password = req.getParameter("password");
		
		if(nickname==null || password==null) {
			req.setAttribute("message", "Please fill in the form.");
			setReqAttributes(req);
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().getBlogUser(nickname);
		if(user==null) {
			req.setAttribute("message", "Nickname does not exist.");
			setReqAttributes(req);
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}

		String passwordHash = Util.calculate_hexEncode(password);
		if(!user.getPasswordHash().equals(passwordHash)) {
			req.setAttribute("message", "Invalid password.");
			setReqAttributes(req);
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}

		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		req.getSession().setAttribute("current.user.email", user.getEmail());
		resp.sendRedirect(req.getContextPath() + "/servleti/author/");
	}

	private static void setReqAttributes(HttpServletRequest req) {
        req.setAttribute("nick", req.getParameter("nick"));
    }

	
	
}