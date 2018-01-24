package com.triple.triple.Model.Mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.triple.triple.Model.Trip;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Kevin on 2018/1/24.
 */

public class TripPlanEntityJsonMapper {
    private final Gson gson;

    public TripPlanEntityJsonMapper() {
        this.gson = new Gson();
    }

    public Trip transformUserEntity(String userJsonResponse) throws JsonSyntaxException {
        final Type tripType = new TypeToken<Trip>() {}.getType();
        return this.gson.fromJson(userJsonResponse, tripType);
    }

    public List<Trip> transformUserEntityCollection(String userListJsonResponse)
            throws JsonSyntaxException {
        final Type listOfUserEntityType = new TypeToken<List<Trip>>() {}.getType();
        return this.gson.fromJson(userListJsonResponse, listOfUserEntityType);
    }
}
