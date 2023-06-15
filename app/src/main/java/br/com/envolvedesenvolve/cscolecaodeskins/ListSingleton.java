package br.com.envolvedesenvolve.cscolecaodeskins;

import java.util.ArrayList;
import java.util.List;

import br.com.envolvedesenvolve.cscolecaodeskins.model.Skin;

public class ListSingleton {

    private static ListSingleton instance;
    private List<Skin> skinList = new ArrayList<>();

    public ListSingleton(){
    }

    public static ListSingleton getInstance() {
        if (instance == null) {
            instance = new ListSingleton();
        }
        return instance;
    }

    public List<Skin> getSkinList() {
        return skinList;
    }

    public void setSkinList(List<Skin> skinList) {
        this.skinList = skinList;
    }
}
