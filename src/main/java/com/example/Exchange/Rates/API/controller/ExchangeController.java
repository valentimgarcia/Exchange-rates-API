package com.example.Exchange.Rates.API.controller;

import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ExchangeController {

    @Autowired
    RestTemplate restTemplate;

    private final String url = "https://api.exchangerate.host";
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

    @GetMapping("/api")
    public String api() {

        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String response = restTemplate.exchange(url + "/latest", HttpMethod.GET, entity, String.class).getBody();  // json string
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonObject req_result = jsonObject.get("rates").getAsJsonObject();

        return "Latest Exchange Rates \n" + req_result.toString();
    }

    @GetMapping("/exchange/rate/{x}/{y}")
    public String exchangeRate(@PathVariable String x, @PathVariable String y) {

        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return restTemplate.exchange(url + "/convert?from=" + x + "&to=" + y, HttpMethod.GET, entity, String.class).getBody();
    }

    @GetMapping("/all/exchange/rates/{x}")
    public String exchangeRatesFromOneCurrency(@PathVariable String x) {

        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return restTemplate.exchange(url + "/latest?base=" + x, HttpMethod.GET, entity, String.class).getBody();
    }

    @GetMapping("/conversion/{amount}/{x}/{y}")
    public String exchangeRateAmount(@PathVariable Integer amount, @PathVariable String x, @PathVariable String y) {

        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return restTemplate.exchange(url + "/convert?amount=" + amount + "&from=" + x + "&to=" + y, HttpMethod.GET, entity, String.class).getBody();
    }

    @GetMapping("/conversion/list/{x}/{list}")
    public String exchangeRatesFromListOfCurrencies(@PathVariable String x, @PathVariable String list) {

        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return restTemplate.exchange(url + "/latest?base=" + x + "&symbols=" + list, HttpMethod.GET, entity, String.class).getBody();
    }
}
