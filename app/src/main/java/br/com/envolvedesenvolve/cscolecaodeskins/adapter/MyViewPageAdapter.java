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
                type = "Rifle";
                break;
            case 1:
                type = "SMG";
                break;
            case 2:
                type = "Shotgun";
                break;
            case 3:
                type = "Pistol";
                break;
            case 4:
                type = "Knife";
                break;
            case 5:
                type = "Gloves";
                break;
            case 6:
                type = "Sticker";
                break;
            case 7:
                type = "Container";
                break;
            case 8:
                type = "Agent";
                break;
        }
        return new TableFragment(type);
    }

    @Override
    public int getItemCount() {
        return 9;
    }
}
