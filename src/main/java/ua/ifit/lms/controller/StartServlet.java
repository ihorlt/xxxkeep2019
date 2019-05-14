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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

@WebServlet(name = "StartServlet", urlPatterns = {"/*"}, loadOnStartup = 1)
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
        LoginView loginView = new LoginView();
        UserRepository userRepository = new UserRepository();

        switch (request.getPathInfo()) {
            case "/":
                out.println(indexSingletonView.getIndexHtml());
                break;
            case "/login":
            case "/login/":
                if (request.getParameter("email") != null &&
                        request.getParameter("password") != null) {
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");

                    // test repository
                    User user = userRepository.getUserByEmailByPassword(email, password);
                    // check if a user successfully logged in
                    if (user != null) {
                        log.info("Successfully logged in " + user.toString());
                        session.setAttribute("user", user);
                        response.sendRedirect("/notes/index");
                    } else {
                        out.println(loginView.getloginPage(false));
                    }
                } else {
                    out.println(loginView.getloginPage(true));
                }
                break;
            case "/logout":
            case "/logout/":
                // TODO add logout
                out.println(indexSingletonView.getIndexHtml()
                        .replace("<!--### insert html here ### -->", "<h1>logout</h1>"));
                break;
            case "/register":
            case "/register/":
                out.println(loginView.getRegisterPage());
                if (request.getParameter("email") != null &&
                        request.getParameter("password") != null &&
                        request.getParameter("name") != null) {
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String name = request.getParameter("name");

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();

                    User user = new User(0, email, password, name, dtf.format(now), dtf.format(now));
                    userRepository.saveUser(user);
                    user = userRepository.getUserByEmailByPassword(email, password);
                    if (user != null) {
                        log.info("Successfully logged in " + user.toString());
                        session.setAttribute("user", user);
                        response.sendRedirect("/notes/index");
                    }
                    log.info(user.toString());
                }
                break;
            default:
                out.println(indexSingletonView.getIndexHtml());
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
