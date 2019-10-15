package com.gmail.dmytrod8.servlets;

import com.gmail.dmytrod8.JsonUsers;
import com.gmail.dmytrod8.ServerResponse;
import com.gmail.dmytrod8.dao.UserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;


public class GetUsersListServlet extends HttpServlet {

    private ServerResponse srvResp = ServerResponse.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @SuppressWarnings("unchecked")
        final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("dao");

        final HttpSession session = req.getSession();
        if (!auth(session)) return;

        final ArrayList<String> usersList = dao.get().getListAllUsers();

        if (usersList.size() > 0) {
            final JsonElement jelement = toJSON(usersList);
            srvResp.sendResponse(resp, 0, jelement);
        } else {
            srvResp.sendResponse(resp, "response:2");
        }

    }

    private JsonElement toJSON(ArrayList<String> usersList) {
        return (JsonElement) new Gson().toJsonTree(new JsonUsers(usersList));

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
