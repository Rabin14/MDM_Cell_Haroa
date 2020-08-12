package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class InternetConn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_conn);



    }
    public void onBackPressed() {
        // super.onBackPressed();
        moveTaskToBack(true);

    }
}