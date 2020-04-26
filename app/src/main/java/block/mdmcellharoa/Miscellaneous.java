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
}
