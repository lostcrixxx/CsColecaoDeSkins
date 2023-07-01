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
