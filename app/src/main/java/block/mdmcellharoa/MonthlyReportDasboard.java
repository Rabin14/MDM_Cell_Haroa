package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MonthlyReportDasboard extends AppCompatActivity {
    TextView dise;
    Spinner dyear,dmonth;
    String [] yearlist;
    String [] monthlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_report_dasboard);

        dise =findViewById(R.id.dise);
        dyear =findViewById(R.id.dyear);
        dmonth =findViewById(R.id.dmonth);

        yearlist= getResources().getStringArray(R.array.yearlist);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,yearlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dyear.setAdapter(adapter);


        monthlist= getResources().getStringArray(R.array.monthlist);
        ArrayAdapter<String> adapter2= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,monthlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dmonth.setAdapter(adapter2);
        // Get the value of dise from share prefer  back
        SharedPreferences getShared = getSharedPreferences("disecode", MODE_PRIVATE);
        String value = getShared.getString("dise", "Enter coverage");
        dise.setText(value);
    }



    public void view(View view) {
        String dise1 = dise.getText().toString();
        String dyear1 = dyear.getSelectedItem().toString();
        String dmonth1 = dmonth.getSelectedItem().toString();

        SharedPreferences shrd = getSharedPreferences("sharenew", MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();

        editor.putString("str1", dise1);
        editor.putString("str2", dyear1);
        editor.putString("str3", dmonth1);

        editor.apply();

        Intent intent = new Intent(MonthlyReportDasboard.this, MainActivity2.class);
        startActivity(intent);
    }
}