package br.com.envolvedesenvolve.cscolecaodeskins.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import br.com.envolvedesenvolve.cscolecaodeskins.R;

public class Home8Fragment extends Fragment {

    private static final String TAG = Home8Fragment.class.getName();

    private Context context;
    private View view;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();

        recyclerView = view.findViewById(R.id.recycler_view);

//        RetrofitClient.getInstance().getData(context, "all", recyclerView, progressBar, progressText);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
