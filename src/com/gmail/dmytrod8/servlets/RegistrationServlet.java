package com.gmail.dmytrod8.servlets;

import com.gmail.dmytrod8.*;
import com.gmail.dmytrod8.dao.UserDAO;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static com.gmail.dmytrod8.User.STATUS.*;


public class RegistrationServlet extends HttpServlet {

    private ServerResponse srvResp = ServerResponse.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws javax.servlet.ServletException, IOException {
        resp.setContentType("application/json");
        @SuppressWarnings("unchecked")
        final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("dao");

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if(login.length() > 10){
            srvResp.sendResponse(resp, "response:1", "action:register", "error:maxSymbols");
            return;
        }

        if (dao.get().userIsExist(login)) {
            srvResp.sendResponse(resp, "response:2", "action:register", "error:userIsExist");

        } else {
            int freeId = dao.get().getFreeId();

            if (dao.get().add(new User(freeId, login, password, Available))) {
                srvResp.sendResponse(resp, "response:0" ,"action:register");
            } else {
                srvResp.sendResponse(resp, "response:3", "error:somethingWentWrong");
            }

        }

    }

}

