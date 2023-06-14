package br.com.envolvedesenvolve.cscolecaodeskins.view;

import static br.com.envolvedesenvolve.cscolecaodeskins.Configuration.KEY_DATA;
import static br.com.envolvedesenvolve.cscolecaodeskins.Configuration.KEY_IMAGE;
import static br.com.envolvedesenvolve.cscolecaodeskins.Configuration.KEY_NAME;
import static br.com.envolvedesenvolve.cscolecaodeskins.Configuration.LIST_URL;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import br.com.envolvedesenvolve.cscolecaodeskins.R;
import br.com.envolvedesenvolve.cscolecaodeskins.ViewAdapterList;
import br.com.envolvedesenvolve.cscolecaodeskins.model.Skin;

public class HomeFragment extends Fragment {

    private final String TAG = HomeFragment.class.getName();

    private View view;
    private RecyclerView recyclerView;
    private ViewAdapterList adapterList;

    private List<Skin> listString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        StringRequest stringRequest = new StringRequest(LIST_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "sendRequest() onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray(KEY_DATA);
                            listString = new ArrayList<>();

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jo = data.getJSONObject(i);
                                Skin skin = new Skin();

                                if (!jo.getString(KEY_IMAGE).equals(""))
                                    skin.setImage(jo.getString(KEY_IMAGE));
                                skin.setName(jo.getString(KEY_NAME));
                                listString.add(skin);
//                                txtName.setText(jo.getString(KEY_NAME));
//                                if(!jo.getString(KEY_IMAGE).equals(""))
//                                urlSkin = jo.getString(KEY_IMAGE);
                            }

                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapterList = new ViewAdapterList(listString);
                            recyclerView.setAdapter(adapterList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "ERRO stringRequest " + error.toString());
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        return view;
    }
}