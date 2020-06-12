package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class ChartAttendance extends AppCompatActivity {
    TextView dateText2,demototal,demodistri;
    AnyChartView anyChartView;
    String[] total = {"Total Staff","Today's Present"};
    int[] attendance ={7,5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_attendance);
        anyChartView=findViewById(R.id.any_chart_view);
        // Get the value of shared preference back
        SharedPreferences getShared = getSharedPreferences("demo2", MODE_PRIVATE);
        String value = getShared.getString("str2","Enter coverage");
        String value3 = getShared.getString("str3","0");
        String value4 = getShared.getString("str4","0");
        dateText2.setText(value);
        demototal.setText(value3);
        demodistri.setText(value4);


        //chart
        // yvalues.add(new PieEntry(number1, 0));
        // yvalues.add(new PieEntry(number2, 1));


      //  float number1 = Float.parseFloat(demototal.getText().toString());
       // float number2 = Float.parseFloat(demodistri.getText().toString());







        setupPieChart();

    }
    public void setupPieChart(){

        Pie pie= AnyChart.pie();
      List<DataEntry> dataEntries = new ArrayList<>();
      for (int i = 0; i<total.length; i++){
          dataEntries.add(new ValueDataEntry(total[i], attendance[i]));
      }
     pie.data(dataEntries);
      anyChartView.setChart(pie);
    }
}