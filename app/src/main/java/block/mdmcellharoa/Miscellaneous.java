package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Miscellaneous extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscellaneous);
    }

    public void distribution(View view) {
        Intent intent = new Intent(getApplicationContext(), Distribution.class);
        startActivity(intent);

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
