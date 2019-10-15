package com.gmail.dmytrod8;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
    private String date;
    private int fromId;
    private String from;
    private int toId;
    private String toLogin;
    private String text;

    public Message(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public static Message fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, Message.class);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public String getToLogin() {
        return toLogin;
    }

    public void setToLogin(String toLogin) {
        this.toLogin = toLogin;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

	@Override
	public String toString() {
		return "Message{" +
				"date='" + date + '\'' +
				", fromId=" + fromId +
				", from='" + from + '\'' +
				", toId=" + toId +
				", toLogin='" + toLogin + '\'' +
				", text='" + text + '\'' +
				'}';
	}
}
