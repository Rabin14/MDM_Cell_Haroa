package block.mdmcellharoa;

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

public class Details extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText firstName,dise;
    TextView phone;
    Spinner school;
    Spinner catagory;
    Spinner gpname;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String [] schoollist;
    String [] catagorylist;
    String [] gplist;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        firstName = findViewById(R.id.firstName);
        dise = findViewById(R.id.dise);
        school = findViewById(R.id.spinner);
        catagory = findViewById(R.id.spinner2);
        gpname = findViewById(R.id.spinner3);
        saveBtn = findViewById(R.id.saveBtn);
        progressBar = findViewById(R.id.progressBar2);
        phone = findViewById(R.id.phone);

        // Get the value of shared preference back
        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        String value = getShared.getString("str1","Enter Mobile Number");
        phone.setText(value);





        schoollist= getResources().getStringArray(R.array.schoolname);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,schoollist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        school.setAdapter(adapter);


        catagorylist= getResources().getStringArray(R.array.catagoryname);
        ArrayAdapter<String> adapter2= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,catagorylist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catagory.setAdapter(adapter2);

        gplist= getResources().getStringArray(R.array.gpname);
        ArrayAdapter<String> adapter3= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,gplist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gpname.setAdapter(adapter3);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstName.getText().toString().isEmpty()||dise.getText().toString().isEmpty()||school.getSelectedItem().toString().isEmpty() ||catagory.getSelectedItem().toString().isEmpty() ||gpname.getSelectedItem().toString().isEmpty() ||phone.getText().toString().isEmpty()){
                    Toast.makeText(Details.this, "Fill the required Details", Toast.LENGTH_SHORT).show();
                    return;
                }

                DocumentReference docRef = fStore.collection("users").document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("Name",firstName.getText().toString());
                user.put("dise",dise.getText().toString());
                user.put("School",school.getSelectedItem().toString());
                user.put("Category",catagory.getSelectedItem().toString());
                user.put("GPName",gpname.getSelectedItem().toString());
                user.put("Mobile",phone.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
                //add user to database
                docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: User Profile Created." + userID);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Failed to Create User " + e.toString());
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}