package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Admin_Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__dashboard);
    }

    public void addmobile(View view) {
        Intent intent = new Intent(Admin_Dashboard.this, Mobile_Registration.class);
        startActivity(intent);

    }

    public void addnotice(View view) {
        Intent intent = new Intent(Admin_Dashboard.this, Add_Notice.class);
        startActivity(intent);
    }

    public void attendance(View view) {
        Intent intent = new Intent(Admin_Dashboard.this, Report_attendance.class);
        startActivity(intent);
    }

    public void coverage(View view) {
        Intent intent = new Intent(Admin_Dashboard.this, Report_Coverage.class);
        startActivity(intent);
    }

    public void savexl(View view) {
        Intent intent = new Intent(Admin_Dashboard.this, web.class);
        intent.putExtra("url", "https://docs.google.com/spreadsheets/d/1yliNEZjWbflfZRYuUcHEe8MRE_Z4EBYxW2W1QSor_Ko/export?format=xlsx");
        startActivity(intent);

    }

    public void savepdf(View view) {
        Intent intent = new Intent(Admin_Dashboard.this, web.class);
        intent.putExtra("url", "https://docs.google.com/spreadsheets/d/1yliNEZjWbflfZRYuUcHEe8MRE_Z4EBYxW2W1QSor_Ko/export?format=pdf");
        startActivity(intent);
    }

    public void send(View view) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Your Subject");
            String sAux = "\n MDM,Haroa Block Database Excel File\n\n";
            sAux = sAux + "https://docs.google.com/spreadsheets/d/1yliNEZjWbflfZRYuUcHEe8MRE_Z4EBYxW2W1QSor_Ko/export?format=xlsx \n\n"; // here define package name of you app
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            Log.e(">>>", "Error: " + e);
        }
    }
}
