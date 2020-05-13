package block.mdmcellharoa;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Admin_Login extends AppCompatActivity {
    ProgressBar progressBar;
    EditText user_id,user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__login);

        progressBar = findViewById(R.id.progressBar);
        user_id = findViewById(R.id.user_id);
        user_password = findViewById(R.id.user_password);
    }

    public void login(View view) {

        String user_id1 = user_id.getText().toString().trim();
        String user_password1 = user_password.getText().toString().trim();

        if (TextUtils.isEmpty(user_id1)) {
            user_id.setError("User Id is Required.");
            return;
        }

        if (TextUtils.isEmpty(user_password1)) {
            user_password.setError("Password is Required.");
            return;
        }
        if (user_id1.length() < 10) {
            user_id.setError("Wrong Id ");
            return;
        }

        if (user_password1.matches("avijit@123") && (user_id1.matches("9153818852"))){
            Intent intent = new Intent(Admin_Login.this, Admin_Dashboard.class);
            startActivity(intent);
            finish();
            progressBar.setVisibility(View.VISIBLE);
        }else {
            Toast.makeText(Admin_Login.this, "Please Enter Valid User Id and Password ! ", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }




    }
}