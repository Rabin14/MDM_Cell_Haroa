package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
}
