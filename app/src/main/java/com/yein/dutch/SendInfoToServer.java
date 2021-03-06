package com.yein.dutch;

import android.content.Context;
import android.icu.util.Output;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SendInfoToServer extends AsyncTask<String, Void, String>{
    // 새로운 스레드에서 수행됨
    @Override
    protected String doInBackground(String... strings) {

        String server = strings[0];
        String id = strings[1];
        String pwd = strings[2];

        try {
            String encId = URLEncoder.encode(id, "UTF-8");
            String encPwd = URLEncoder.encode(pwd, "UTF-8");
            String param = "id="+encId+"&pwd="+encPwd;

            URL url = new URL(server);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestMethod("POST");

            con.setDoInput(true);
            con.setDoOutput(true);

            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(param);
            osw.flush();
            osw.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String result = "";

            while ((result = br.readLine()) != null) {
                sb.append(result);
            }

            con.disconnect();
            br.close();

            return sb.toString();
        }catch (Exception e){
            Log.e("Error", "Exception" + e.getMessage() );
            return null;
        }
    }
}
