package target.samarthanam;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by z081942 on 4/9/16.
 */

public class CallAPI extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... params) {
        String urlString=params[0]; // URL to call
        String method = params [1];
        String resultToDisplay = "";
        InputStream in = null;
        JSONObject jsonObject = null;
        // HTTP Get
        try {

            URL url = new URL(urlString);

            if(method == "GET") {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                urlConnection.setRequestMethod(method);
                StringBuilder sb = new StringBuilder();
                String line = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                String result = sb.toString();
                JSONArray jsonResult = new JSONArray(result);
                return jsonResult.toString();
            } else if(method == "POST")
            {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(method);
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setConnectTimeout(5000);

                OutputStream os = urlConnection.getOutputStream();
                String json = params[2];
                os.write(json.getBytes("UTF-8"));
                os.close();

                in = new BufferedInputStream(urlConnection.getInputStream());
                // read the response
                StringBuilder sb = new StringBuilder();
                String line = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                String result = sb.toString();
                jsonObject = new JSONObject(result);
                in.close();
                urlConnection.disconnect();

                return jsonObject.toString();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return jsonObject.toString();

    }

} // end CallAPI


