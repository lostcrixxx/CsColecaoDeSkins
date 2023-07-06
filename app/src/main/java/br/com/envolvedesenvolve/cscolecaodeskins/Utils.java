package br.com.envolvedesenvolve.cscolecaodeskins;

import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import br.com.envolvedesenvolve.cscolecaodeskins.model.Skin;

public class Utils {

    private static final String TAG = Utils.class.getName();
    private static Utils instance;

    public static final String URL_IMAGE = "https://community.cloudflare.steamstatic.com/economy/image/";

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
            if (item.getName().contains("(Nova de Fábrica)") || item.getType().contains("Adesivo") || item.getType().contains("Recipiente"))
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
}
