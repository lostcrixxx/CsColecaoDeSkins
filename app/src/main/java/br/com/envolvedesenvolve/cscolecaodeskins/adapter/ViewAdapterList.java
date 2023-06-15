package br.com.envolvedesenvolve.cscolecaodeskins.adapter;

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

public class ViewAdapterList extends RecyclerView.Adapter<ViewAdapterList.ViewHolder> {

    private static final String TAG = ViewAdapterList.class.getName();
    private static final String URL_IMAGE = "https://community.cloudflare.steamstatic.com/economy/image/";

    private List<Skin> listaItens;

    public ViewAdapterList(List<Skin> listaItens) {
        this.listaItens = listaItens;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_skin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        String image = listaItens.get(position).getImage();
//        Log.e(TAG, "onBindViewHolder image: " + image);
        holder.itemTextView.setText(listaItens.get(position).getName());
        Picasso.get().load(URL_IMAGE+listaItens.get(position).getImage()).error(R.mipmap.ic_launcher).into(holder.itemImageView);
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
}

