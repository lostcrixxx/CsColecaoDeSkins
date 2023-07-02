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
            if (item.getType().toLowerCase().contains(typeFilter.toLowerCase()))
                filteredList.add(item);
        }
        return filteredList;
    }

    public String filterRemoveType(String item) {

        String text1 = "(Nova de Fábrica)";
        String text2 = "(Pouco Usada)";
        String text3 = "(Testada em Campo)";
        String text4 = "(Bem Desgastada)";
        String text5 = "(Veterana de Guerra)";

        return item.replace(text1, "").replace(text2, "").replace(text3, "").replace(text4, "").replace(text5, "");
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
