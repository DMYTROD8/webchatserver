package com.gmail.dmytrod8;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ServerResponse {
    private static final ServerResponse srvResp = new ServerResponse();

    public ServerResponse() {
    }

    public static ServerResponse getInstance() {
        return srvResp;
    }


    private synchronized String buildJsonResponse(int success, JsonElement jsonElement) {
        jsonElement.getAsJsonObject().addProperty("response", success);
        return new Gson().toJson(jsonElement);
    }

    private synchronized String buildJsonResponse(String[] array) {
        JsonObject jsonObj = new JsonObject();

        if (array.length > 0) {

            for (int i = 0; i < array.length; i++) {
                try {
                    String[] arr = array[i].split(":");
                    jsonObj.addProperty(arr[0], arr[1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }

        } else {
            //
        }

        return new Gson().toJson(jsonObj);
    }

    public synchronized void sendResponse(HttpServletResponse resp, int response, JsonElement jsonElement)
            throws IOException {
        String json = buildJsonResponse(response, jsonElement);

        OutputStream os = resp.getOutputStream();
        byte[] buf = json.getBytes(StandardCharsets.UTF_8);
        os.write(buf);
    }

    public synchronized void sendResponse(HttpServletResponse resp, String... array) throws IOException {
        String json = buildJsonResponse(array);

        OutputStream os = resp.getOutputStream();
        byte[] buf = json.getBytes(StandardCharsets.UTF_8);
        os.write(buf);
    }

}
