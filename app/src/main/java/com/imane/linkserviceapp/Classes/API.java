package com.imane.linkserviceapp.Classes;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class API {

    private static String urlAPI = "https://linkserviceapi.herokuapp.com/";

    public static String sendRequest(final String jsonData, final String action) throws IOException, InterruptedException {
        final String[] data = {null};
        final CountDownLatch latch = new CountDownLatch(1);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
            try {
                URL url = new URL(urlAPI + action);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonData.toString());

                os.flush();
                os.close();

                System.out.println(conn.getInputStream());

                data[0] = readFullyAsString(conn.getInputStream(), "UTF-8");

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
            }

        });

        thread.start();
        latch.await();

        return data[0];
    }

    public static HashMap<String, TypeService> decodeResponseMultipleAsTypeService (String typesService){
        HashMap<String, TypeService> retMap = new Gson().fromJson(
                typesService, new TypeToken<HashMap<String, TypeService>>() {}.getType()
        );
        return retMap;
    }

    public static HashMap<String, Service> decodeResponseMultipleAsService (String services){
        HashMap<String, Service> retMap = new Gson().fromJson(
                services, new TypeToken<HashMap<String, Service>>() {}.getType()
        );
        return retMap;
    }

    public static HashMap<String, User> decodeResponseMultipleAsUser (String users){
        HashMap<String, User> retMap = new Gson().fromJson(
                users, new TypeToken<HashMap<String, User>>() {}.getType()
        );
        return retMap;
    }

    private static String readFullyAsString(InputStream inputStream, String encoding) throws IOException {
        return readFully(inputStream).toString(encoding);
    }

    private static ByteArrayOutputStream readFully(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos;
    }

    public static String passwordHash(String stringToHash) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(stringToHash.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

}
