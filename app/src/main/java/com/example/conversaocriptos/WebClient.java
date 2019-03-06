package com.example.conversaocriptos;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebClient {


    public  static String getJson(String nameCoin){

        String jsonDeResposta=null;
        try {
            String url = "https://www.mercadobitcoin.net/api/"+nameCoin+"/ticker/";

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(url).build();

            Response response = client.newCall(request).execute();

            jsonDeResposta = response.body().string();
        }catch (Exception e){
            Log.e("REQUEST", e.getMessage());
        }


        return jsonDeResposta;
    }


    public static Coin getCurrenciesFromJson(String resposta,String nameCoin){

        JSONObject json= null;
        try {
            json = new JSONObject(resposta);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String name;
        Double buy;
        Coin coin=null;
        try {
            JSONObject results = json.getJSONObject("ticker");
            name=nameCoin;
            buy=results.getDouble("buy");
            coin = new Coin(name,buy);
        }catch (JSONException ex){
            Log.d("ERROR",ex.getMessage());
        }
        return coin;
    }

    public static Coin getCoin(String namecoin){
       return getCurrenciesFromJson(getJson(namecoin),namecoin);
    }
}
