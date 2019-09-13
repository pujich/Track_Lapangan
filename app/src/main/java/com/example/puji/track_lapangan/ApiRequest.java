package com.example.puji.track_lapangan;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApiRequest {
private static String link = "http://192.168.43.125/E-Tracker/Api/";
    public static String sendPOST_login(String Username, String Pass) throws IOException, JSONException {
        URL obj = new URL(link+"login");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        System.out.println("User:"+Username);
        System.out.println("Pass:"+Pass);
        System.out.println(link+"login");
        String data = URLEncoder.encode("username", "UTF-8")
                + "=" + URLEncoder.encode(Username, "UTF-8");

        data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                + URLEncoder.encode(Pass, "UTF-8");
        // For POST only - START
        con.setDoInput(true);
        con.setDoOutput(true);
        OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
        os.write(data);
        os.flush();
        os.close();

        // For POST only - END

        int responseCode = con.getResponseCode();


        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
//
//
//            if (responseCode==404){
//                System.out.println("Login failed");
//                System.out.println(response.toString());
//                return response.toString();}
//
//            else {
////                JSONObject jsonResponse = new JSONObject(response.toString());
////                JSONArray jsonMainNode = jsonResponse.optJSONArray("ID_USER");
////                JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);
////                String id_user = jsonChildNode.optString("ID_USER");
                String id_user = response.toString();

                return id_user;


        } else if (responseCode==404){
            System.out.println("Login failed");
//            System.out.println(.toString());
            return "Wrong";}
           else {
            System.out.println("Something is wrong with the connection");
            System.out.println(data);
            System.out.println(responseCode);

            return "Failed";
        }

    }
    public static String sendPOST_AP(String mac_address) throws IOException {
        URL obj = new URL(link+"AP");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        String data = URLEncoder.encode("mac_address", "UTF-8")
                + "=" + URLEncoder.encode(mac_address, "UTF-8");

        con.setDoInput(true);
        con.setDoOutput(true);
        OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
        os.write(data);
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            if (response.toString().isEmpty()){
                System.out.println("Post failed");
                System.out.println(response.toString());
                return null;}

            else {
                return response.toString();}
        }


        else {System.out.println("Something is wrong with the connection");
            System.out.println(data);
            System.out.println(responseCode);
            return null;}

    }

    public static String sendUPDATE_AP(String... changes) throws IOException {
        URL obj = new URL(link+"Update");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        String data = URLEncoder.encode("tipe", "UTF-8")
                + "=" + URLEncoder.encode(changes[0], "UTF-8");

        data += "&" + URLEncoder.encode("status", "UTF-8") + "="
                + URLEncoder.encode(changes[1], "UTF-8");

        data += "&" + URLEncoder.encode("alamat", "UTF-8") + "="
                + URLEncoder.encode(changes[2], "UTF-8");

        data += "&" + URLEncoder.encode("alamat2", "UTF-8") + "="
                + URLEncoder.encode(changes[3], "UTF-8");

        data += "&" + URLEncoder.encode("alamat3", "UTF-8") + "="
                + URLEncoder.encode(changes[4], "UTF-8");

        data += "&" + URLEncoder.encode("alamat4", "UTF-8") + "="
                + URLEncoder.encode(changes[5], "UTF-8");

        data += "&" + URLEncoder.encode("alasan", "UTF-8") + "="
                + URLEncoder.encode(changes[6], "UTF-8");

        data += "&" + URLEncoder.encode("keterangan", "UTF-8") + "="
                + URLEncoder.encode(changes[7], "UTF-8");

        data += "&" + URLEncoder.encode("perubah", "UTF-8") + "="
                + URLEncoder.encode(changes[8], "UTF-8");

        data += "&" + URLEncoder.encode("mac", "UTF-8") + "="
                + URLEncoder.encode(changes[9], "UTF-8");

        con.setDoInput(true);
        con.setDoOutput(true);
        OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
        os.write(data);
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            if (response.toString().isEmpty()){
                System.out.println("String response empty");
                System.out.println(response.toString());
                return null;}

            else { System.out.println("Success: "+response.toString());
                return response.toString();}
        }

        else {System.out.println("Something is wrong with the connection");
            System.out.println(data);
            System.out.println(responseCode);
            return null;}

    }

    private static void sendGET() throws IOException {
        URL obj = new URL("http://example.com/Api/AP");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

    }

}
