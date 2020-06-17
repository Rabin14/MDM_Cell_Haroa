package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Piechart_Attendance extends AppCompatActivity {
    PieChartView pieChartView;
    TextView demototal,demodistri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart__attendance);
        demototal  = (TextView) findViewById(R.id.demototal);
        demodistri  = (TextView) findViewById(R.id.demodistri);
        pieChartView = findViewById(R.id.chart);
        getSupportActionBar().hide();
        // Get the value of shared preference back
        SharedPreferences getShared = getSharedPreferences("demo2", MODE_PRIVATE);

        String value3 = getShared.getString("str3","0");
        String value4 = getShared.getString("str4","0");

        demototal.setText(value3);
        demodistri.setText(value4);
        Integer t1 = Integer.parseInt(demototal.getText().toString());
        Integer t2 = Integer.parseInt(demodistri.getText().toString());

        String s1 = demototal.getText().toString();
        String s2 = demodistri.getText().toString();

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(t1, Color.BLUE).setLabel("Total:"+s1));
        pieData.add(new SliceValue(t2, Color.MAGENTA).setLabel("Present:"+s2));


        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("Today's Attendance").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#62ed2f"));
        pieChartView.setPieChartData(pieChartData);
    }
}