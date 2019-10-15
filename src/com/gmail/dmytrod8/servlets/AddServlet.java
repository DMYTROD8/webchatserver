package com.gmail.dmytrod8.servlets;

import com.gmail.dmytrod8.*;
import com.gmail.dmytrod8.dao.UserDAO;
import com.google.gson.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.util.Objects.nonNull;

public class AddServlet extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();
    private ServerResponse srvResp = ServerResponse.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        @SuppressWarnings("unchecked") final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("dao");
        final HttpSession session = req.getSession();

        int idReceiver = 0;
        String loginReceiver = "";

        final String loginSender = (String) session.getAttribute("login");
        final int idSender = (int) session.getAttribute("id");

        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);

        JsonElement jelement = new JsonParser().parse(bufStr);
        JsonObject jobject = jelement.getAsJsonObject();

        String chat = jobject.get("chat").getAsString();
        String msgChat = jobject.get("msg").getAsString();


        if (chat.equals("privateOutbox")) {
            JsonElement jelement2 = new JsonParser().parse(msgChat);
            JsonObject jobject2 = jelement2.getAsJsonObject();

            loginReceiver = jobject2.get("toLogin").getAsString();
            idReceiver = dao.get().getIdByLogin(loginReceiver);
            if (idReceiver == -1) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        Message msg = Message.fromJSON(msgChat);

        if (msg != null) {
            msg.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")));
            msg.setFrom(loginSender);
            msg.setFromId(idSender);
            msg.setToLogin(loginReceiver);

            if (chat.equals("privateOutbox")) {
                msg.setToId(idReceiver);
                msgList.add(msg, idSender, idReceiver);
            } else {
                msgList.add(msg, chat);
            }


            srvResp.sendResponse(resp, "response:0");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        }

    }

    private byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
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
