package com.triple.triple.Interface;

/**
 * Created by PokeGuys on 4/9/18.
 */
public interface DataManager {
    void saveData(String key, String data);
    String getData(String key);
}
