package com.gmail.dmytrod8.servlets;

import com.gmail.dmytrod8.ServerResponse;
import com.gmail.dmytrod8.User;
import com.gmail.dmytrod8.dao.UserDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;


public class AuthServlet extends HttpServlet {
    private ServerResponse srvResp = ServerResponse.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws javax.servlet.ServletException, IOException {
        resp.setContentType("application/json");
        @SuppressWarnings("unchecked") final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("dao");

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");


        if (dao.get().userIsExist(login)) {
            final int userId = dao.get().userIsExist(login, password);

            if (userId != -1) {

                req.getSession().setAttribute("password", password);
                req.getSession().setAttribute("login", login);
                req.getSession().setAttribute("id", userId);

                srvResp.sendResponse(resp, "response:0", "action:login", "id:" + userId, "un:" + login);
                
                return;
            }
            srvResp.sendResponse(resp, "response:1", "action:login");//invalidPassword
        } else {
            srvResp.sendResponse(resp, "response:2", "action:login");//userDoesNotExist
        }

    }

}
