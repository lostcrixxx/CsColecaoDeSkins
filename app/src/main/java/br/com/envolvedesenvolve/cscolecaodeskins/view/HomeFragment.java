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



        return view;
    }
}