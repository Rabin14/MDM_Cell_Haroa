package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Miscellaneous extends AppCompatActivity {
    TextView dateText,dateText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscellaneous);
        dateText2  = (TextView) findViewById(R.id.dateText2);
        dateText  = (TextView) findViewById(R.id.dateText);

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        //set it as current date.
        dateText.setText(date_n);
        // Get the value of shared preference back
        SharedPreferences getShared = getSharedPreferences("demo4", MODE_PRIVATE);
        String value = getShared.getString("str2","distribution");
        dateText2.setText(value);


    }

    public void distribution(View view) {
        final String dateTextn1 = dateText.getText().toString().trim();
        final String dateTextnn1 = dateText2.getText().toString().trim();

        if (dateTextn1.matches(dateTextnn1)){
            Toast.makeText(Miscellaneous.this, " Distribution Report already Send ! ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),Piechart_Distribution.class);

            startActivity(intent);

        }else {
            Intent intent = new Intent(getApplicationContext(), Distribution.class);
            startActivity(intent);

        }




    }

    public void sampleformat(View view) {
        Intent intent = new Intent(getApplicationContext(), Sample_Format.class);
        startActivity(intent);
    }

    public void allotment(View view) {
        Intent intent = new Intent(getApplicationContext(), Allotment.class);
        startActivity(intent);
    }

    public void cch(View view) {
        Intent intent = new Intent(getApplicationContext(), CCH_Details.class);
        startActivity(intent);
    }

    public void teacher(View view) {
        Intent intent = new Intent(getApplicationContext(), Teacher_Details.class);
        startActivity(intent);
    }

    public void smsdata(View view) {
        Intent intent = new Intent(getApplicationContext(), SMS_Data.class);
        startActivity(intent);
    }
}
