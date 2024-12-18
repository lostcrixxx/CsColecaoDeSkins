package br.com.envolvedesenvolve.cscolecaodeskins.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.envolvedesenvolve.cscolecaodeskins.ListSingleton;
import br.com.envolvedesenvolve.cscolecaodeskins.R;
import br.com.envolvedesenvolve.cscolecaodeskins.Utils;
import br.com.envolvedesenvolve.cscolecaodeskins.adapter.ViewAdapterList;

public class TableFragment extends Fragment {

    private final String TAG = TableFragment.class.getName();

    private String type;

    private View view;
    private RecyclerView recyclerView;
    private ViewAdapterList adapterList;

    public TableFragment(String type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_table, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterList = new ViewAdapterList(Utils.getInstance().filterList(ListSingleton.getInstance().getSkinList(), type));
        recyclerView.setAdapter(adapterList);

        return view;
    }
}