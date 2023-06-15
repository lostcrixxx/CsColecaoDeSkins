package br.com.envolvedesenvolve.cscolecaodeskins;

import java.util.ArrayList;
import java.util.List;

import br.com.envolvedesenvolve.cscolecaodeskins.model.Skin;

public class Utils {

    private static Utils instance;

    public Utils(){
    }

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public List<Skin> filterList(List<Skin> list, String typeFilter){
        List<Skin> filteredList = new ArrayList<>();
            for(Skin item : list){
                if(item.getType().toLowerCase().contains(typeFilter.toLowerCase()))
                    filteredList.add(item);
            }
        return filteredList;
    }
}
