package com.gmail.dmytrod8.servlets;

import com.gmail.dmytrod8.MessageList;
import com.gmail.dmytrod8.ServerResponse;
import com.google.gson.JsonElement;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.util.Objects.nonNull;

public class GetListServlet extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();
    private ServerResponse srvResp = ServerResponse.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        final HttpSession session = req.getSession();
        if (!auth(session)) return;

        final String fromStr = req.getParameter("from");
        final String chatType = req.getParameter("chat");
        int userId = (int) req.getSession().getAttribute("id");

        final int from = checkIncomingValueFrom(fromStr);

        final JsonElement jelement = msgList.toJSON(from, chatType, userId);

        if (jelement != null) {
            srvResp.sendResponse(resp, 0, jelement);
        } else {
            srvResp.sendResponse(resp, "response:1", "error:0 messages");
        }

    }

    private boolean auth(HttpSession session) {
        if (nonNull(session) &&
                nonNull(session.getAttribute("login")) &&
                nonNull(session.getAttribute("password"))) {
            return true;
        }
        return false;
    }

    private int checkIncomingValueFrom(String fromStr){
        int from = 0;
        try {
            from = Integer.parseInt(fromStr);
            if (from < 0) from = 0;
        } catch (Exception ex) {
            return from;
        }
        return from;
    }
}
