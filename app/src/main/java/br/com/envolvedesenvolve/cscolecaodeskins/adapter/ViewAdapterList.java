package br.com.envolvedesenvolve.cscolecaodeskins.adapter;

import static br.com.envolvedesenvolve.cscolecaodeskins.Utils.URL_IMAGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.envolvedesenvolve.cscolecaodeskins.R;
import br.com.envolvedesenvolve.cscolecaodeskins.Utils;
import br.com.envolvedesenvolve.cscolecaodeskins.model.Skin;
import br.com.envolvedesenvolve.cscolecaodeskins.view.DetailActivity;

public class ViewAdapterList extends RecyclerView.Adapter<ViewAdapterList.ViewHolder> {

    private static final String TAG = ViewAdapterList.class.getName();

    private final boolean modeCollection = Utils.getInstance().MODE_COLLECTION;

    public ViewAdapterList(List<Skin> dataList) {
        this.dataList = dataList;
    }

    private final List<Skin> dataList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_skin, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        Log.e(TAG, "onBindViewHolder image: " + image);
        holder.itemTextView.setText(Utils.getInstance().filterRemoveType(dataList.get(position).getName()));

        Picasso.get().load(URL_IMAGE + dataList.get(position).getImage()).error(R.mipmap.ic_launcher).into(holder.itemImageView);
        if (modeCollection)
            holder.itemImageView.setAlpha(0.4f);

        holder.itemView.setOnClickListener(view -> {
//            Log.e(TAG, "onBindViewHolder type: " + listaItens.get(position).getType() + " name: " + listaItens.get(position).getName());

            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra("detail_item", dataList.get(position));
            view.getContext().startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(view -> {
            Log.e(TAG, "onBindViewHolder longClick: " + dataList.get(position).getType() + " name: " + dataList.get(position).getName());

            holder.itemImageView.setAlpha(1.0f);
            holder.itemTextView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            Snackbar.make(view, "Item salvo !", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // Método para adicionar um novo item na lista
    public void addItem(Skin newItem) {
        dataList.add(newItem);
        notifyItemInserted(dataList.size() - 1);
    }

    // Método para remover um item da lista
    public void removeItem(int position) {
        if (position >= 0 && position < dataList.size()) {
            dataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    // Método para atualizar um item na lista
    public void updateItem(int position, Skin updatedItem) {
        if (position >= 0 && position < dataList.size()) {
            dataList.set(position, updatedItem);
            notifyItemChanged(position);
        }
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

