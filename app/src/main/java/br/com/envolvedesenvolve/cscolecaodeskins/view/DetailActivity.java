package br.com.envolvedesenvolve.cscolecaodeskins.view;

import static br.com.envolvedesenvolve.cscolecaodeskins.Utils.URL_IMAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.envolvedesenvolve.cscolecaodeskins.R;
import br.com.envolvedesenvolve.cscolecaodeskins.Utils;
import br.com.envolvedesenvolve.cscolecaodeskins.model.Skin;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = DetailActivity.class.getName();

    private TextView detailName;
    private TextView detailWear;
    private TextView detailType;
    private ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailName = findViewById(R.id.detail_name);
        detailWear = findViewById(R.id.detail_wear);
        detailType = findViewById(R.id.detail_type);
        detailImage = findViewById(R.id.detail_image);

        Intent i = getIntent();
        Skin itemSkin = (Skin) i.getSerializableExtra("detail_item");

        Log.e(TAG, "DetailActivity name: " + itemSkin.getName() + " type: " + itemSkin.getType() + " image: " + itemSkin.getImage());

        detailName.setText(Utils.getInstance().filterRemoveType(itemSkin.getName()));
        detailWear.setText(itemSkin.getWear());
        detailType.setText(itemSkin.getType());
//        detailImage.setImageURI();
        Picasso.get().load(URL_IMAGE + itemSkin.getImage()).error(R.mipmap.ic_launcher).into(detailImage);
    }
}