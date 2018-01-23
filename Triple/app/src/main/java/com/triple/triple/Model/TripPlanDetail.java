package com.triple.triple.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin on 2018/1/22.
 */

public class TripPlanDetail implements Serializable {
    private ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String, Object>>();

    public TripPlanDetail() {
    }

    public ArrayList<HashMap<String, Object>> getListData() {
        return listData;
    }

    public void setListData(ArrayList<HashMap<String, Object>> listData) {
        this.listData = listData;
    }

}
