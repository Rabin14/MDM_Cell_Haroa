package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import android.os.Bundle;

public class Mobile_Registration extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mobile,number1,number2;

    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile__registration);
        mobile = findViewById(R.id.mobile);
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);

        saveBtn = findViewById(R.id.saveBtn);
        progressBar = findViewById(R.id.progressBar2);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number1.getText().toString().isEmpty()||number2.getText().toString().isEmpty() ||mobile.getText().toString().isEmpty()){
                    Toast.makeText(Mobile_Registration.this, "Fill the required Details", Toast.LENGTH_SHORT).show();
                    return;
                }
                userID =  number1.getText().toString();
                progressBar.setVisibility(View.VISIBLE);

                DocumentReference docRef = fStore.collection("phone").document(userID);
                Map<String,Object> user = new HashMap<>();

                user.put("phone",number1.getText().toString());
                user.put("mobile",number1.getText().toString());


                //add user to database
                docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Mobile_Registration.this, "Successfully Registered ! ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Admin_Dashboard.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Mobile_Registration.this, " Failed to register ! ", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}