package com.example.Exchange.Rates.API.model;

import com.google.gson.JsonObject;

public class ExchangeRatePojo {

    private final JsonObject jsonObject;

    public ExchangeRatePojo(JsonObject jsonObject) {
        String from = jsonObject.get("query").getAsJsonObject().get("from").getAsString();
        String to = jsonObject.get("query").getAsJsonObject().get("to").getAsString();
        String amount = jsonObject.get("query").getAsJsonObject().get("amount").getAsString();

        this.jsonObject = new JsonObject();
        this.jsonObject.addProperty("success", jsonObject.get("success").getAsString());
        this.jsonObject.addProperty("from", from);
        this.jsonObject.addProperty("to", to);
        this.jsonObject.addProperty("amount", amount);
        this.jsonObject.addProperty("result", jsonObject.get("result").getAsString());
    }

    @Override
    public String toString() {
        return this.jsonObject.toString();
    }
}
