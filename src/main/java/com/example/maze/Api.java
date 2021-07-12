package com.example.maze;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class Api {

    private static final String auth = "Authorization";
    private static final String code = "HTI Thanks You [415b]";
    private static final String BASEURL = "https://maze.hightechict.nl";
    private final String UrlAffix;
    private final String UrlVariable;
    private final String callType;
    private String data = "";
    private int status = 0;

    public Api(String api, String affix, String callType) {
        UrlAffix = api;
        UrlVariable = affix;
        this.callType = callType.toUpperCase(Locale.ROOT);
    }

    public String call() {
        try {
            String baseUrl = BASEURL + UrlAffix + UrlVariable;
            URL url = new URL(baseUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(callType);
            con.setRequestProperty("accept", "application/json");
            if (!callType.equalsIgnoreCase("get")){
                con.setChunkedStreamingMode(100);
                con.setDoOutput(true);
            }
            con.setRequestProperty(auth, code);
            status = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder data = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                data.append(inputLine);
            }
            in.close();
            con.disconnect();
            this.data = data.toString();
        } catch (Exception e) {
            System.out.println("Problem with call, no data returned! :" + e);
        }
        return data;
    }

    public int getStatus() {
        return status;
    }

}