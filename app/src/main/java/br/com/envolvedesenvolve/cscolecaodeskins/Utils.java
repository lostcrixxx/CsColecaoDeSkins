package br.com.envolvedesenvolve.cscolecaodeskins;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import br.com.envolvedesenvolve.cscolecaodeskins.db.SkinDao;
import br.com.envolvedesenvolve.cscolecaodeskins.model.Skin;

public class Utils {

    private static final String TAG = Utils.class.getName();

    private static Utils instance;

    public static boolean MODE_COLLECTION = false;

    public Utils() {
    }

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }


    public List<Skin> filterList(List<Skin> list, String typeFilter) {
        List<Skin> filteredList = new ArrayList<>();
        for (Skin item : list) {
            if (item.getName() != null && !item.getName().contains("(Field-Tested)") && !item.getName().contains("Minimal Wear") && !item.getName().contains("Well-Worn") && !item.getName().contains("Battle-Scarred"))
                if (item.getType().toLowerCase().contains(typeFilter.toLowerCase()))
                    filteredList.add(item);
        }
        return filteredList;
    }

    public String filterRemoveType(String item) {
        String[] text = {"(Nova de Fábrica)", "(Pouco Usada)", "(Testada em Campo)", "(Bem Desgastada)", "(Veterana de Guerra)"};

        return item.replace(text[0], "").replace(text[1], "").replace(text[2], "").replace(text[3], "").replace(text[4], "");
    }

    public boolean isInternetAvailable() throws UnknownHostException {
        boolean result = false;

        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            Log.e(TAG, "isConnected(): " + !ipAddr.equals(""));
            result = !ipAddr.equals("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void copyDatabaseToApp(Context context) throws IOException {
        Log.e(TAG, "passed start copiarBancoDeDados()");
        InputStream inputStream = context.getAssets().open("database.db");
        String outFileName = context.getDatabasePath("database.db").getPath();

        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        Log.e(TAG, "passed end copiarBancoDeDados()");
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        Log.e(TAG, "passed close databases");
    }

    public boolean databaseExist(Context context) {
        File dbFile = context.getDatabasePath("database.db");
        return dbFile.exists();
    }
}
