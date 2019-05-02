package ua.ifit.lms.controller;

import ua.ifit.lms.view.IndexSingletonView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "NoteServlet", urlPatterns = {"/notes/*"})
public class NoteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        IndexSingletonView indexSingletonView = IndexSingletonView.getInstance();
        out.println(indexSingletonView.getIndexHtml()
                .replace("<!--### insert html here ### -->", "<h1>Hello notes</h1>"));
    }
}
