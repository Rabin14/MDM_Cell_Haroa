package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class Monthly_Report extends AppCompatActivity {
    TextView dise;
    Spinner dyear,dmonth;
    String [] yearlist;
    String [] monthlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly__report);
        dise =findViewById(R.id.dise);
        dyear =findViewById(R.id.dyear);
        dmonth =findViewById(R.id.dmonth);
// Get the value of dise from share prefer  back
        SharedPreferences getShared = getSharedPreferences("disecode", MODE_PRIVATE);
        String value = getShared.getString("dise", "Enter coverage");
        dise.setText(value);


        yearlist= getResources().getStringArray(R.array.yearlist);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,yearlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dyear.setAdapter(adapter);


        monthlist= getResources().getStringArray(R.array.monthlist);
        ArrayAdapter<String> adapter2= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,monthlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dmonth.setAdapter(adapter2);
    }

    public void showreport(View view) {


    }
}