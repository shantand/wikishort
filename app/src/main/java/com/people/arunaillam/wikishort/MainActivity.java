package com.people.arunaillam.wikishort;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttprequestTask().execute();
    }

    //Private class to define processes running in background thread
    //UI will not freeze because of this
    // https://spring.io/guides/gs/consuming-rest-android/


    private class HttprequestTask extends AsyncTask<Void, Void, InfoBox> {
        @Override
        protected InfoBox doInBackground(Void... params) {
            try {
                final String url = "http://rest-service.guides.spring.io/greeting";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                InfoBox ib = restTemplate.getForObject(url, InfoBox.class);
                return ib;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(InfoBox ib) {
            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            greetingIdText.setText(String.valueOf(ib.getId()));
            greetingContentText.setText(ib.getContent());
        }
    }


}
