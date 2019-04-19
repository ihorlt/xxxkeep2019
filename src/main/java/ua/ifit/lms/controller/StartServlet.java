package ua.ifit.lms.controller;

import ua.ifit.lms.dao.entity.User;
import ua.ifit.lms.dao.repository.UserRepository;
import ua.ifit.lms.view.IndexSingletonView;
import ua.ifit.lms.view.LoginView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "StartServlet", urlPatterns = {"/"}, loadOnStartup = 1)
public class StartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        IndexSingletonView indexSingletonView = IndexSingletonView.getInstance();
        out.println(indexSingletonView.getIndexHtml());

        // get user credentials
         LoginView loginView = new LoginView();
        if (request.getParameter("email") != null &&
                request.getParameter("password") != null) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // test repository
            UserRepository userRepository = new UserRepository();
            User user = userRepository.getUserByEmailByPassword(email, password);
            out.println(loginView.welcomUserPage(user));
        } else {
            out.println(loginView.getloginPage());
        }

    }

    @Override
    public void init() throws ServletException {
        super.init();
        String path = getServletContext().getRealPath("html/");
        IndexSingletonView indexSingletonView = IndexSingletonView.getInstance();
        indexSingletonView.setPath(path);
    }
}
