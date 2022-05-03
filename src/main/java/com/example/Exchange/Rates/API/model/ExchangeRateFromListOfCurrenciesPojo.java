package com.example.Exchange.Rates.API.model;

import com.google.gson.JsonObject;

public class ExchangeRateFromListOfCurrenciesPojo {

    private final JsonObject jsonObject;

    public ExchangeRateFromListOfCurrenciesPojo(JsonObject jsonObject) {
        JsonObject rates = jsonObject.get("rates").getAsJsonObject();

        this.jsonObject = new JsonObject();
        this.jsonObject.addProperty("success", jsonObject.get("success").getAsString());
        this.jsonObject.addProperty("base", jsonObject.get("base").getAsString());
        this.jsonObject.addProperty("rates", rates.toString());
    }

    @Override
    public String toString() {
        return this.jsonObject.toString();
    }
}
