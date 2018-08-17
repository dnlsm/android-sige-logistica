package br.com.cpqd.instrumentacao.sige.login.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class ConnectionManager {
    public String postToServer(String url, String postData) throws UnknownHostException, IOException {

        byte[] postDataBytes = postData.getBytes("UTF-8");

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

        connection.setDoOutput(true);

        connection.getOutputStream().write(postDataBytes);

        connection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null)
            sb.append(line);

        br.close();
        connection.disconnect();

        return sb.toString();

    }
}
