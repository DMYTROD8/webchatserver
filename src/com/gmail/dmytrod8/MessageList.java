package com.gmail.dmytrod8;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class MessageList {
    private static final MessageList msgList = new MessageList();

    private final Gson gson;
    private final List<Message> listGeneral = new LinkedList<>();
    private final List<Message> listDating = new LinkedList<>();
    private final List<Message> listFlood = new LinkedList<>();
    private final List<Message> listAdult = new LinkedList<>();
    private final Map<Integer, LinkedList<Message>> listPrivateInbox = new HashMap<>();
    private final Map<Integer, LinkedList<Message>> listPrivateOutbox = new HashMap<>();

    public static MessageList getInstance() {
        return msgList;
    }

    private MessageList() {
        gson = new GsonBuilder().create();
    }

    private synchronized Object getListByFilterChat(String chatType) {

        if (chatType.equals("general")) {
            return listGeneral;
        }
        if (chatType.equals("dating")) {
            return listDating;
        }
        if (chatType.equals("flood")) {
            return listFlood;
        }
        if (chatType.equals("adult")) {
            return listAdult;
        }
        if (chatType.equals("privateInbox")) {
            return listPrivateInbox;
        }
        if (chatType.equals("privateOutbox")) {
            return listPrivateOutbox;
        }

        return null;
    }

    public synchronized void add(Message m, String chatType) {
        List<Message> temp = (List<Message>) getListByFilterChat(chatType);
        if (temp != null) {
            temp.add(m);
        }
    }

    public synchronized void add(Message m, int idSender, int idReceiver) {

        Map<Integer, List<Message>> tempInbox = (Map<Integer, List<Message>>) getListByFilterChat("privateInbox");
        Map<Integer, List<Message>> tempOutbox = (Map<Integer, List<Message>>) getListByFilterChat("privateOutbox");


        if (tempInbox != null && tempOutbox != null) {

            List<Message> listR = tempInbox.get(idReceiver);
            if (listR == null) {
                listR = new ArrayList<>();
                tempInbox.put(idReceiver, listR);
            }
            listR.add(m);
            tempInbox.put(idReceiver, listR);

            List<Message> listS = tempOutbox.get(idSender);
            if (listS == null) {
                listS = new ArrayList<>();
                tempOutbox.put(idSender, listS);
            }
            listS.add(m);
            tempOutbox.put(idSender, listS);


        } else {

        }

    }

    public synchronized JsonElement toJSON(int n, String chatType, int userId) {

        if (chatType.equals("privateOutbox") || chatType.equals("privateInbox")) {
            Map<Integer, List<Message>> tempBox = (Map<Integer, List<Message>>) getListByFilterChat(chatType);
            if (tempBox != null) {
                if (tempBox.containsKey(userId)) {
                    List<Message> list = new ArrayList<>(tempBox.get(userId));
                    if (n >= list.size()) return null;
                    return (JsonElement) new Gson().toJsonTree(new JsonMessages(list, n));
                } else {
                    return null;
                }
            }

        } else {
            List<Message> list = (List<Message>) getListByFilterChat(chatType);
            if (n >= list.size()) return null;
            return (JsonElement) new Gson().toJsonTree(new JsonMessages(list, n));
        }

        return null;
    }

}

