package br.com.envolvedesenvolve.cscolecaodeskins.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.envolvedesenvolve.cscolecaodeskins.R;
import br.com.envolvedesenvolve.cscolecaodeskins.model.Skin;
import br.com.envolvedesenvolve.cscolecaodeskins.view.DetailActivity;

public class ViewAdapterList extends RecyclerView.Adapter<ViewAdapterList.ViewHolder> {

    private static final String TAG = ViewAdapterList.class.getName();
    private static final String URL_IMAGE = "https://community.cloudflare.steamstatic.com/economy/image/";

    private List<Skin> listaItens;
    private OnItemClickListener listener;
    private Activity activity;

    public ViewAdapterList(List<Skin> listaItens) {
//    public ViewAdapterList(List<Skin> listaItens, OnItemClickListener listener) {
        this.listaItens = listaItens;
//        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_skin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        String image = listaItens.get(position).getImage();
//        Log.e(TAG, "onBindViewHolder image: " + image);
        holder.itemTextView.setText(listaItens.get(position).getName());
        Picasso.get().load(URL_IMAGE + listaItens.get(position).getImage()).error(R.mipmap.ic_launcher).into(holder.itemImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onBindViewHolder type: " + listaItens.get(position).getType() + "position: " + listaItens.get(position).getName());

//                v.getContext().startActivity(intent);
                Intent myIntent = new Intent(v.getContext(), DetailActivity.class);

//                DetailActivity detailActivity = new DetailActivity();
                v.getContext().startActivity(myIntent);

//                    AppCompatActivity activity = (AppCompatActivity) holder.itemView.getContext();
//                    Fragment myFragment = new DetailFragment();
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaItens.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;
        ImageView itemImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.text_list_name);
            itemImageView = itemView.findViewById(R.id.img_list_icon);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

