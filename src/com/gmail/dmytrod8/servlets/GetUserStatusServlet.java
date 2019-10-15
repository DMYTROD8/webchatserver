package com.gmail.dmytrod8.servlets;

import com.gmail.dmytrod8.ServerResponse;
import com.gmail.dmytrod8.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;

public class GetUserStatusServlet extends HttpServlet {
    private ServerResponse srvResp = ServerResponse.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("dao");

        final HttpSession session = req.getSession();
        final int userId = (int) session.getAttribute("id");
        if (!auth(session)) return;

        final String changeStatus = req.getParameter("change");
        if(!changeStatus.equals("")){
            dao.get().changeUserStatusById(userId, changeStatus);
            srvResp.sendResponse(resp, "response:0", "OK");
            return;
        }

        final String loginInfo = req.getParameter("login");
        final String status = dao.get().getUserStatusByLogin(loginInfo);

        if (status != null) {
            srvResp.sendResponse(resp, "response:0", "status:" + status);
            return;
        }
        srvResp.sendResponse(resp, "response:2");
    }

    private boolean auth(HttpSession session) {
        if (nonNull(session) &&
                nonNull(session.getAttribute("login")) &&
                nonNull(session.getAttribute("password"))) {
            return true;
        }
        return false;
    }

}

