package com.example.apiparser;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(v -> jsonParse());
    }

    private void jsonParse() {

        String url = "https://s3.amazonaws.com/open-to-cors/assignment.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject jsonObject = response.getJSONObject("products");

                        for (int i = 0; i < jsonObject.length(); i++) {
                            JSONObject products = jsonObject.getJSONObject(String.valueOf(i));

                            String subcategory = products.getString("subcategory");
                            String title = products.getString("title");
                            int price = products.getInt("price");
                            int popularity = products.getInt("popularity");

                            mTextViewResult.append(subcategory + ", "+ title + ", " + price + ", " + popularity + "\n\n");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        mQueue.add(request);
    }
}