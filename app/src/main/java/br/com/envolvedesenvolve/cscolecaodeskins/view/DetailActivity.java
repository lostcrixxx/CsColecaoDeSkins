package br.com.envolvedesenvolve.cscolecaodeskins.view;

import static br.com.envolvedesenvolve.cscolecaodeskins.Utils.URL_IMAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
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
    private ImageView detailImage, detailImage2, detailImage3, detailImage4, detailImage5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        Skin itemSkin = (Skin) i.getSerializableExtra("detail_item");

        // Check the current orientation
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_detail);

            detailName = findViewById(R.id.detail_name);
            detailWear = findViewById(R.id.detail_wear);
            detailType = findViewById(R.id.detail_type);

            detailName.setText(Utils.getInstance().filterRemoveType(itemSkin.getName()));
            detailWear.setText(itemSkin.getWear());
            detailType.setText(itemSkin.getType());
        } else {
            setContentView(R.layout.activity_detail_landscape);
        }

        detailImage = findViewById(R.id.detail_image);
//        detailImage2 = findViewById(R.id.detail_image2);
//        detailImage3 = findViewById(R.id.detail_image3);
//        detailImage4 = findViewById(R.id.detail_image4);
//        detailImage5 = findViewById(R.id.detail_image5);

        Log.e(TAG, "DetailActivity name: " + itemSkin.getName() + " type: " + itemSkin.getType() + " image: " + itemSkin.getImage());

//        detailImage.setImageURI();
        Picasso.get().load(URL_IMAGE + itemSkin.getImage()).error(R.mipmap.ic_launcher).into(detailImage);
//        Picasso.get().load(URL_IMAGE + itemSkin.getImage()).error(R.mipmap.ic_launcher).into(detailImage2);
//        Picasso.get().load(URL_IMAGE + itemSkin.getImage()).error(R.mipmap.ic_launcher).into(detailImage3);
//        Picasso.get().load(URL_IMAGE + itemSkin.getImage()).error(R.mipmap.ic_launcher).into(detailImage4);
//        Picasso.get().load(URL_IMAGE + itemSkin.getImage()).error(R.mipmap.ic_launcher).into(detailImage5);
    }
}