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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.*;

@WebServlet(name = "StartServlet", urlPatterns = {"/"}, loadOnStartup = 1)
public class StartServlet extends HttpServlet {

    // logging
    private static  Logger log = Logger.getLogger("controller.StartServlet");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

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
            // check if a user successfully logged in
            if (user != null) {
                log.info("Successfully logged in " + user.toString());
                session.setAttribute("user", user);
                response.sendRedirect("/notes/index");
            }

            out.println(loginView.getloginPage());
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

        // log file config
        try {
            // set file where to pu logs data
            Handler fh = new FileHandler(getServletContext().getRealPath("/logs/app.log"));
            // create object of formatter
            fh.setFormatter(new SimpleFormatter());
            // assign formatter to logger class
            Logger.getLogger("").addHandler(fh);
            // add copy output to console
            Logger.getLogger("").addHandler(new ConsoleHandler());
            // all levels will be outputted
            Logger.getLogger("").setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
