package com.example.conversaocriptos;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private WebClientTask mTask;
    private TextView mTvBtc;
    private TextView mTvEth;
    private TextView mTvLtc;
    private TextView mTvXrp;
    private ArrayList<Coin> cotacaoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebClient requisicao= new WebClient();
        startDownload();
        mTvBtc= findViewById(R.id.tv_value_btc);
        mTvLtc= findViewById(R.id.tv_value_ltc);
        mTvXrp= findViewById(R.id.tv_value_xrp);
        mTvEth= findViewById(R.id.tv_value_eth);













    }




    /*Não é necessario mexer aqui */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {

            Toast.makeText(this,"Loading Preços",Toast.LENGTH_LONG).show();
            startDownload();


        }
        return true;
    }


    public void startDownload() {
        if (mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new WebClientTask();
            mTask.execute();
        }
    }

    class  WebClientTask extends AsyncTask<Void,Void, ArrayList<Coin>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Pronto...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected ArrayList<Coin> doInBackground(Void... strings) {
            ArrayList<Coin> coinsList=new ArrayList<>();
            Coin  BTC = WebClient.getCoin("BTC");
            Coin  ETH = WebClient.getCoin("ETH");
            Coin  LTC = WebClient.getCoin("LTC");
            Coin  XRP = WebClient.getCoin("XRP");
            coinsList.add(BTC);
            coinsList.add(ETH);
            coinsList.add(LTC);
            coinsList.add(XRP);
            cotacaoes=coinsList;
            return coinsList;
        }

        @Override
        protected void onPostExecute(ArrayList<Coin> coins) {
            super.onPostExecute(coins);
            //     showProgress(false);
            if (coins != null) {

                mTvBtc.setText(coins.get(0).getStringBuy());
                mTvEth.setText(coins.get(1).getStringBuy());
                mTvLtc.setText(coins.get(2).getStringBuy());
                mTvXrp.setText(coins.get(3).getStringBuy());
                Toast.makeText(getApplicationContext(), "Valores Atualizados", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_LONG).show();
            }
        }
    }
}

