package br.com.envolvedesenvolve.cscolecaodeskins.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import br.com.envolvedesenvolve.cscolecaodeskins.view.TableFragment;

public class MyViewPageAdapter extends FragmentStateAdapter {

    public MyViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String type = "";
        switch (position) {
            case 0:
                type = "Adesivo";
                break;
            case 1:
                type = "Faca";
                break;
            case 2:
                type = "Rifle";
                break;
            case 3:
                type = "Luvas";
                break;
            case 4:
                type = "Recipiente";
                break;
            case 5:
                type = "Pistola";
                break;
            case 6:
                type = "Escopeta";
                break;
            case 7:
                type = "submetralhadora";
                break;
        }
        return new TableFragment(type);
    }

    @Override
    public int getItemCount() {
        return 8;
    }
}
