package hm.ProyLocate;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Date;
import hm.ProyLocate.R;

public class HistActivity extends Activity {


    static final int DATE_DIALOG_ID = 999;
    private CheckBox mCheckFecha;

    private int year = 2014;
    private int month;
    private int day;
    Date fecha;

    private ListView mLvlistaHistorial;
    SimpleCursorAdapter adapter;
    BaseHelper db = new BaseHelper(this, SuperDB.DBNAME, null, SuperDB.VERSION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hist);

        mLvlistaHistorial = (ListView) findViewById(R.id.lista_Hist);

        Cursor c = db.consultarHist();

        String[] from = new String[]{"_id", SuperDB.COLUMN_DATE};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                c,
                from,
                to,
                0 );
        mLvlistaHistorial.setAdapter(adapter);

        addListenerOnCheckFecha();

    }

    protected void addListenerOnCheckFecha(){
        mCheckFecha = (CheckBox) findViewById(R.id.CheckFecha);

        mCheckFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    showDialog(DATE_DIALOG_ID);
                }

            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            java.util.Date utilDate = new java.util.GregorianCalendar(year, month, day).getTime();
            fecha = new Date(utilDate.getTime());
            Toast.makeText(getApplicationContext(), "" + fecha + "" , Toast.LENGTH_SHORT).show();
            // set selected date into datepicker also
            //dpResult.init(year, month, day, null);

            Relleno(fecha);

        }

    };
    private void Relleno(Date fecha){

        Cursor c = db.consultarHistPorFecha(fecha);

        String[] from = new String[]{"_id", SuperDB.COLUMN_DATE};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                c,
                from,
                to,
                0 );
        mLvlistaHistorial.setAdapter(adapter);
    }
}
