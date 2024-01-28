package br.com.envolvedesenvolve.cscolecaodeskins;

import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.envolvedesenvolve.cscolecaodeskins.adapter.CsvAdapter;
import br.com.envolvedesenvolve.cscolecaodeskins.adapter.MyViewPageAdapter;
import br.com.envolvedesenvolve.cscolecaodeskins.db.SQLiteHelper;
import br.com.envolvedesenvolve.cscolecaodeskins.db.SkinDao;
import br.com.envolvedesenvolve.cscolecaodeskins.model.Skin;
import br.com.envolvedesenvolve.cscolecaodeskins.view.SettingsActivity;

/**
 * created by Cristiano M. on 2023-06-26
 * modified by Cristiano M. on 2024-08-31
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private boolean isNightModeOn;

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private MyViewPageAdapter myViewPageAdapter;
    private SharedPreferences prefs;

    private RecyclerView recyclerView;
    private CsvAdapter adapter;
    private List<String> dataList;

    private static final int COUNT = 100;
    private int countTotalPageExecuted = 0;
    private static final String SORT_COLUMN = "price";
    private static final String SORT_DIR = "asc";
    private static final int APP_ID = 730;
    private static final int DELAY = 30000; // 30 segundos de delay

    private TextView recordsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordsTextView = findViewById(R.id.recordsTextView);

        prefs = getSharedPreferences("settings", Context.MODE_PRIVATE);
        Utils.getInstance().MODE_COLLECTION = prefs.getBoolean("modeCollection", false);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            isNightModeOn = false;
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            isNightModeOn = true;
        }

//        startFetching();
        if (SkinDao.getInstance(this).isSkinsTableEmpty()){
            Log.e(TAG, "passed copyDatabaseToApp() new database.db");
            try {
                Utils.getInstance().copyDatabaseToApp(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        recordsTextView.setText("Teste");

        try {
            Log.e(TAG, "passed onCreate() ");
            for (Skin skin : SkinDao.getInstance(this).getAllSkins()) {
                Log.e(TAG, "Skin: " + skin.getHashName());
            }
        } catch (Exception e){
            Log.e(TAG, "ERRO onCreate() " + e.toString());
            e.printStackTrace();
        }


//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//        // Example data, replace with data from CSV
//        dataList = new ArrayList<>();
//        dataList.add("Line 1");
//        dataList.add("Line 2");
//        dataList.add("Line 3");
//
//        adapter = new CsvAdapter(dataList);
//        recyclerView.setAdapter(adapter);
//
//        // Add more data to simulate dynamic addition
//        addMoreData("Line 4");
////        addMoreData("Line 5");


//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();


//        tabLayout = findViewById(R.id.tab_layout);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
//        viewPager2 = findViewById(R.id.view_pager);
//        myViewPageAdapter = new MyViewPageAdapter(this);
//        viewPager2.setAdapter(myViewPageAdapter);
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager2.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                tabLayout.getTabAt(position).select();
//            }
//        });
    }

    public void startFetching() {
        List<JSONObject> allSkins = new ArrayList<>();
        int start = 0;

        SQLiteHelper dbHelper = new SQLiteHelper(this);

        while (true) {
            countTotalPageExecuted++;
            String urlString = Config.URL_STEAM + "?start=" + start + "&count=" + COUNT + "&sort_column=" + SORT_COLUMN + "&sort_dir=" + SORT_DIR + "&appid=" + APP_ID + "&norender=2&language=portuguese";

            try {
                String jsonResponse = getHttpResponse(urlString);
                JSONObject response = new JSONObject(jsonResponse);
                JSONArray results = response.getJSONArray("results");

                if (results.length() == 0) {
                    break;
                }

                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);
                    JSONObject assetDescription = item.getJSONObject("asset_description");
                    JSONObject filteredItem = new JSONObject();
                    filteredItem.put("name", assetDescription.getString("name"));
                    filteredItem.put("hash_name", assetDescription.getString("market_hash_name"));
                    filteredItem.put("classid", assetDescription.getString("classid"));
                    filteredItem.put("instanceid", assetDescription.getString("instanceid"));
                    filteredItem.put("icon_url", assetDescription.getString("icon_url"));
                    filteredItem.put("icon_url_large", assetDescription.optString("icon_url_large", ""));

                    JSONArray descriptions = assetDescription.optJSONArray("descriptions");
                    if (descriptions != null && descriptions.length() > 0) {
                        filteredItem.put("description", descriptions.getJSONObject(0).getString("value"));
                    } else {
                        filteredItem.put("description", "");
                    }

                    filteredItem.put("type", assetDescription.getString("type"));

                    allSkins.add(filteredItem);
                }

                // Verificar se a quantidade de skins atinge o limiar para salvar em um novo arquivo
//                if (allSkins.size() >= FILE_SPLIT_THRESHOLD) {
                saveToDatabase(allSkins, dbHelper, recordsTextView);
//                saveToDatabase(allSkins);
                allSkins.clear();
//                }

                start += COUNT;
                Thread.sleep(DELAY); // Adiciona um delay entre as requisições
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    // Adiciona um delay antes de tentar novamente
                    Thread.sleep(DELAY);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                break;
            } finally {
                dbHelper.close();
            }
        }

//        // Salvar os dados restantes se houver
//        if (!allSkins.isEmpty()) {
//            saveToDatabase(allSkins);
//        }

        // Imprime a quantidade total de skins encontradas
        System.out.println("Processo concluído.");
        startFetching();
    }

    private String getHttpResponse(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();

        return content.toString();
    }

    private void saveToDatabase(List<JSONObject> skins, SQLiteHelper dbHelper, TextView textView) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.beginTransaction();
            for (JSONObject skin : skins) {
                String hashName = skin.getString("hash_name");

                // Verificar se o hash_name já existe
                String query = "SELECT COUNT(*) FROM " + SQLiteHelper.TABLE_SKINS + " WHERE " + SQLiteHelper.COLUMN_HASH_NAME + " = ?";
                Cursor cursor = db.rawQuery(query, new String[]{hashName});
                cursor.moveToFirst();
                int count = cursor.getInt(0);
                cursor.close();

                if (count == 0) {
                    // Inserir somente se não existir
                    ContentValues values = new ContentValues();
                    values.put(SQLiteHelper.COLUMN_NAME, skin.getString("name"));
                    values.put(SQLiteHelper.COLUMN_HASH_NAME, hashName);
                    values.put(SQLiteHelper.COLUMN_CLASSID, skin.getString("classid"));
                    values.put(SQLiteHelper.COLUMN_INSTANCEID, skin.getString("instanceid"));
                    values.put(SQLiteHelper.COLUMN_IMAGE, skin.getString("icon_url"));
                    values.put(SQLiteHelper.COLUMN_IMAGE_LARGE, skin.getString("icon_url_large"));
                    values.put(SQLiteHelper.COLUMN_WEAR, skin.getString("description"));
                    values.put(SQLiteHelper.COLUMN_TYPE, skin.getString("type"));

                    db.insert(SQLiteHelper.TABLE_SKINS, null, values);
                    System.out.println("Item adicionado com sucesso - " + hashName);
                } else {
                    System.out.println("Item já existente - " + hashName);
                }
            }
            db.setTransactionSuccessful();

            Toast.makeText(this, skins.size() + " processados no banco de dados!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao inserir dados no banco de dados: " + e.getMessage());
        } finally {
            db.endTransaction();

            // Contar registros na tabela skins
            String countQuery = "SELECT COUNT(*) FROM " + SQLiteHelper.TABLE_SKINS;
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.moveToFirst();
            int totalRecords = cursor.getInt(0);
            cursor.close();

            // Atualizar o TextView
            textView.setText("Total de registros: " + totalRecords);

            db.close();
        }
    }

    private void addMoreData(String data) {
        dataList.add(data);
        adapter.notifyItemInserted(dataList.size() - 1);
        recyclerView.scrollToPosition(dataList.size() - 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.action_size_list_skin);
//        item.setTitle("items: " + ListSingleton.getInstance().getSkinList().size() + " cadastrados");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                isNightModeOn = true;
                break;
//            case R.id.action_rate:
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                isNightModeOn = false;
//                break;
            case R.id.action_exit:
                this.finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}