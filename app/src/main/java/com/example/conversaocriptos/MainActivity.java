package com.example.conversaocriptos;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private WebClientTask mTask;
    private TextView mTvBtc;
    private TextView mTvEth;
    private TextView mTvLtc;
    private TextView mTvXrp;

    private Button mBtn;

    private ArrayList<Coin> cotacaoes;

    private  Coin  mBTC;
    private Coin  mETH;
   private Coin  mLTC ;
    private Coin  mXRP;

    private double valorTotalltc;
    private double valorTotalbtc;
    private double valorTotalxrp;
    private double valorTotaleth;
    DecimalFormat formato = new DecimalFormat("#.##");
    private TextView valor;
    private String valorPassado;


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
        mBtn= findViewById(R.id.btn_show);
        valor = findViewById(R.id.numberText);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valor.getText()!=null){
                    String valorRecebido = valor.getText().toString();
                    double quantidadeMoeda = Double.parseDouble(valorRecebido);

                    valorTotalbtc =  quantidadeMoeda / mBTC.getBuy();
                    valorTotaleth =  quantidadeMoeda / mETH.getBuy();
                    valorTotalltc =  quantidadeMoeda / mLTC.getBuy();
                    valorTotalxrp =  quantidadeMoeda / mXRP.getBuy();

                    DecimalFormat df = new DecimalFormat("##0.00");

                    String valorFinalltc = df.format(valorTotalltc);
                    String valorFinalbtc = df.format(valorTotalbtc);
                    String valorFinaleth = df.format(valorTotaleth);
                    //oi
                    String valorFinalxrp = df.format(valorTotalxrp);


                    TextView convxrp = findViewById(R.id.convxrp);
                    TextView convltc = findViewById(R.id.convltc);
                    TextView conveth = findViewById(R.id.conveth);
                    TextView convbtc = findViewById(R.id.convbtc);

                    convbtc.setText(valorFinalbtc);
                    convltc.setText(valorFinalltc);
                    conveth.setText(valorFinaleth);
                    convxrp.setText(valorFinalxrp);

                }else {
                    Toast.makeText(getApplicationContext(),"Digite um valor valido!",Toast.LENGTH_LONG).show();
                }
            }
        });
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
            mBTC = WebClient.getCoin("BTC");
            mETH = WebClient.getCoin("ETH");
            mLTC = WebClient.getCoin("LTC");
            mXRP = WebClient.getCoin("XRP");
            coinsList.add(mBTC);
            coinsList.add(mETH);
            coinsList.add(mLTC);
            coinsList.add(mXRP);
            cotacaoes=coinsList;
            Log.i("BTC",cotacaoes.get(0).getStringBuy());
            Log.i("ETH",cotacaoes.get(1).getStringBuy());
            Log.i("XRP",cotacaoes.get(2).getStringBuy());
            Log.i("LTC",cotacaoes.get(3).getStringBuy());
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

