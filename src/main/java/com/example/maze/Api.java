package com.example.maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Locale;

public class Api {

    private static final String auth = "Authorization";
    private static final String code = "HTI Thanks You [415b]";
    private static final String BASEURL = "https://maze.hightechict.nl";
    private final String UrlAffix;
    private final String UrlVariable;
    private final String callType;

    public Api(String api, String variable, String callType) {
        UrlAffix = api;
        UrlVariable = variable;
        this.callType = callType.toUpperCase(Locale.ROOT);
    }

    public String call() {
        URL url = null;

        try {
            url = new URL(BASEURL + UrlAffix + UrlVariable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection con = null;
        try {
            assert url != null;
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            assert con != null;
            con.setRequestMethod(callType);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setRequestProperty("accept", "application/json");
        con.setChunkedStreamingMode(100);
        con.setDoOutput(true);
        con.setRequestProperty(auth, code);
        //int status = con.getResponseCode();
        //System.out.println(status);

        // We now read the data as string
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String inputLine = null;
        StringBuilder data = new StringBuilder();
        while (true) {
            try {
                assert in != null;
                if ((inputLine = in.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            data.append(inputLine);
        }
        // ToDo: use finally/withResources here
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        con.disconnect();
        return data.toString();
    }
}
