package br.com.envolvedesenvolve.cscolecaodeskins.view;

import static br.com.envolvedesenvolve.cscolecaodeskins.Configuration.KEY_DATA;
import static br.com.envolvedesenvolve.cscolecaodeskins.Configuration.KEY_IMAGE;
import static br.com.envolvedesenvolve.cscolecaodeskins.Configuration.KEY_IMAGE_LARGE;
import static br.com.envolvedesenvolve.cscolecaodeskins.Configuration.KEY_NAME;
import static br.com.envolvedesenvolve.cscolecaodeskins.Configuration.KEY_TYPE;
import static br.com.envolvedesenvolve.cscolecaodeskins.Configuration.KEY_WEAR;
import static br.com.envolvedesenvolve.cscolecaodeskins.Configuration.LIST_URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.envolvedesenvolve.cscolecaodeskins.ListSingleton;
import br.com.envolvedesenvolve.cscolecaodeskins.MainActivity;
import br.com.envolvedesenvolve.cscolecaodeskins.R;
import br.com.envolvedesenvolve.cscolecaodeskins.model.Skin;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getName();
    private static final int SPLASH_DURATION = 10000; // Splash screen duration in milliseconds

    private ProgressBar progressBar;
    private TextView progressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delayed start of the main activity
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, SPLASH_DURATION);

        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        // Simule um processo assíncrono que leva algum tempo para ser concluído
//        simulateLongRunningProcess();
//        progressBar();

        getWebData();
    }

    int value = 100;

    public void progressBar() {
        Thread t = new Thread() {
            public void run() {
                int oldProgress = 10;
                while (progressBar.getProgress() < 100) {
                    ((Activity) getApplicationContext()).runOnUiThread(new Runnable() {
                        public void run() {
                            value -= oldProgress;
                            progressBar.setProgress(value);
                            progressText.setText(value + " %");
                        }
                    });

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private void simulateLongRunningProcess() {
        // Exiba o ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        // Simule um processo assíncrono usando um Handler e postDelayed
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Oculte o ProgressBar quando o processo estiver concluído
                progressBar.setVisibility(View.GONE);
            }
        }, 3000); // Tempo de simulação de 3 segundos (3000 milissegundos)
    }

    private void getWebData() {
        StringRequest stringRequest = new StringRequest(LIST_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "sendRequest() onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray(KEY_DATA);
                            List<Skin> skinList = new ArrayList<>();

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jo = data.getJSONObject(i);
                                Skin skin = new Skin();

                                skin.setName(jo.getString(KEY_NAME));
                                if (!jo.getString(KEY_IMAGE).equals(""))
                                    skin.setImage(jo.getString(KEY_IMAGE));
                                if (!jo.getString(KEY_IMAGE_LARGE).equals(""))
                                    skin.setImageLarge(jo.getString(KEY_IMAGE_LARGE));
                                skin.setWear(jo.getString(KEY_WEAR));
                                skin.setType(jo.getString(KEY_TYPE));
                                skinList.add(skin);
//                                txtName.setText(jo.getString(KEY_NAME));
//                                if(!jo.getString(KEY_IMAGE).equals(""))
//                                urlSkin = jo.getString(KEY_IMAGE);
                            }
                            ListSingleton.getInstance().setSkinList(skinList);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "ERRO stringRequest " + error.toString());
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}