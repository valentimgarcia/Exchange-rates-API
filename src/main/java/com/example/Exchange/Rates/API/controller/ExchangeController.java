package com.example.Exchange.Rates.API.controller;

import com.example.Exchange.Rates.API.model.ExchangeRateAmountPojo;
import com.example.Exchange.Rates.API.model.ExchangeRateFromListOfCurrenciesPojo;
import com.example.Exchange.Rates.API.model.ExchangeRatePojo;
import com.example.Exchange.Rates.API.model.ExchangeRateFromOneCurrencyPojo;
import com.google.gson.JsonParser;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import com.google.gson.JsonObject;

@RestController
@CacheConfig(cacheNames = "myExchangeController")
public class ExchangeController {

    @Autowired
    RestTemplate restTemplate;

    private final String url = "https://api.exchangerate.host";
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the latest exchange rates"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature!"),
            @ApiResponse(code = 500, message = "An exception was thrown"),
    })
    @GetMapping(value ="/api")
    @Cacheable
    public String api() {
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String response = restTemplate.exchange(url + "/latest", HttpMethod.GET, entity, String.class).getBody();  // json string
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonObject result = jsonObject.get("rates").getAsJsonObject();

        return "Latest Exchange Rates: \n" + result.toString();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns exchange rate from Currency A to Currency B"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature!"),
            @ApiResponse(code = 500, message = "An exception was thrown"),
    })
    @GetMapping(value = "/exchange/rate/{currencyA}/{currencyB}", produces = "application/json")
    @Cacheable
    public String exchangeRate(@PathVariable String currencyA, @PathVariable String currencyB) {
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String response = restTemplate.exchange(url + "/convert?from=" + currencyA + "&to=" + currencyB, HttpMethod.GET, entity, String.class).getBody();
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        ExchangeRatePojo pojo = new ExchangeRatePojo(jsonObject);

        return pojo.toString();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns all exchange rates from Currency A"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature!"),
            @ApiResponse(code = 500, message = "An exception was thrown"),
    })
    @GetMapping(value ="/all/exchange/rates/{currencyA}", produces = "application/json")
    @Cacheable
    public String exchangeRateFromOneCurrency(@PathVariable String currencyA) {
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String response = restTemplate.exchange(url + "/latest?base=" + currencyA, HttpMethod.GET, entity, String.class).getBody();
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        ExchangeRateFromOneCurrencyPojo pojo = new ExchangeRateFromOneCurrencyPojo(jsonObject);

        return pojo.toString();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns value conversion from Currency A to Currency B"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature!"),
            @ApiResponse(code = 500, message = "An exception was thrown"),
    })
    @GetMapping(value ="/conversion/{amount}/{currencyA}/{currencyB}", produces = "application/json")
    @Cacheable
    public String exchangeRateAmount(@PathVariable Integer amount, @PathVariable String currencyA, @PathVariable String currencyB) {
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String response = restTemplate.exchange(url + "/convert?amount=" + amount + "&from=" + currencyA + "&to=" + currencyB, HttpMethod.GET, entity, String.class).getBody();
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        ExchangeRateAmountPojo pojo = new ExchangeRateAmountPojo(jsonObject);

        return pojo.toString();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns value conversion from Currency A to a list of supplied currencies"),
            @ApiResponse(code = 403, message = "You do not have permission to access this feature!"),
            @ApiResponse(code = 500, message = "An exception was thrown"),
    })
    @GetMapping(value ="/conversion/list/{currencyA}/{list}", produces = "application/json")
    @Cacheable
    public String exchangeRateFromListOfCurrencies(@PathVariable String currencyA, @PathVariable String list) {
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String response = restTemplate.exchange(url + "/latest?base=" + currencyA + "&symbols=" + list, HttpMethod.GET, entity, String.class).getBody();
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        ExchangeRateFromListOfCurrenciesPojo pojo = new ExchangeRateFromListOfCurrenciesPojo(jsonObject);

        return pojo.toString();
    }
}
