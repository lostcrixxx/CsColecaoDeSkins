package br.com.envolvedesenvolve.cscolecaodeskins.db;

import static br.com.envolvedesenvolve.cscolecaodeskins.db.SQLiteHelper.COLUMN_CLASSID;
import static br.com.envolvedesenvolve.cscolecaodeskins.db.SQLiteHelper.COLUMN_CREATED_AT;
import static br.com.envolvedesenvolve.cscolecaodeskins.db.SQLiteHelper.COLUMN_HASH_NAME;
import static br.com.envolvedesenvolve.cscolecaodeskins.db.SQLiteHelper.COLUMN_ID;
import static br.com.envolvedesenvolve.cscolecaodeskins.db.SQLiteHelper.COLUMN_IMAGE;
import static br.com.envolvedesenvolve.cscolecaodeskins.db.SQLiteHelper.COLUMN_IMAGE_LARGE;
import static br.com.envolvedesenvolve.cscolecaodeskins.db.SQLiteHelper.COLUMN_INSTANCEID;
import static br.com.envolvedesenvolve.cscolecaodeskins.db.SQLiteHelper.COLUMN_NAME;
import static br.com.envolvedesenvolve.cscolecaodeskins.db.SQLiteHelper.COLUMN_TYPE;
import static br.com.envolvedesenvolve.cscolecaodeskins.db.SQLiteHelper.COLUMN_WEAR;
import static br.com.envolvedesenvolve.cscolecaodeskins.db.SQLiteHelper.TABLE_SKINS;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import br.com.envolvedesenvolve.cscolecaodeskins.model.Skin;

public class SkinDao {

    private SQLiteDatabase database;

    // Construtor que recebe o banco de dados
    public SkinDao(SQLiteDatabase database) {
        this.database = database;
    }

    // Método que faz o SELECT e retorna uma List<Skin>
    public List<Skin> getAllSkins() {
        List<Skin> skins = new ArrayList<>();

        // Definir as colunas a serem selecionadas
        String[] columns = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_HASH_NAME,
                COLUMN_CLASSID,
                COLUMN_INSTANCEID,
                COLUMN_IMAGE,
                COLUMN_IMAGE_LARGE,
                COLUMN_WEAR,
                COLUMN_TYPE,
                COLUMN_CREATED_AT
        };

        // Executar o SELECT
        Cursor cursor = database.query(TABLE_SKINS, columns, null, null, null, null, null);

        if (cursor != null) {
            // Verificar se o cursor tem dados
            if (cursor.moveToFirst()) {
                do {
                    // Criar um novo objeto Skin e preencher os dados
                    Skin skin = new Skin();
                    skin.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                    skin.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                    skin.setHashName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HASH_NAME)));
                    skin.setClassid(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_CLASSID)));
                    skin.setInstanceid(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_INSTANCEID)));
                    skin.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
                    skin.setImageLarge(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_LARGE)));
                    skin.setWear(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEAR)));
                    skin.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
                    skin.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)));

                    // Adicionar o objeto Skin à lista
                    skins.add(skin);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return skins;
    }
}

