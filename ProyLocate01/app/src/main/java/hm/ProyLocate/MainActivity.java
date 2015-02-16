package hm.ProyLocate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by USUARIO-WIN on 01/02/2015.
 */
public class MainActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btn = (Button) findViewById(R.id.btn_search);
        Button btn_search1 = (Button) findViewById(R.id.btn_search1);
        Button btn_hist = (Button) findViewById(R.id.btn_Hist);
        Button btn_chgSuper = (Button) findViewById(R.id.btn_ChgSuper);

        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onSearchRequested();
            }
        });

        btn_search1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.callOnClick();
            }
        });

        btn_hist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent histAct = new Intent(getApplicationContext(), HistActivity.class);
                startActivity(histAct);
            }
        });

        btn_chgSuper.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapAct = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(mapAct);
            }
        });

    }
}
