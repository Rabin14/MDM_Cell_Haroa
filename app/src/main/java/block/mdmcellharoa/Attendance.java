package block.mdmcellharoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import android.os.Bundle;

public class Attendance extends AppCompatActivity implements View.OnClickListener{
    TextView school,category,gp,name,dateText,dateText2,demototal,demodistri;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    EditText total,distribution;
    Button buttonAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        school    = (TextView) findViewById(R.id.school);
        category = (TextView) findViewById(R.id.category);
        gp = (TextView) findViewById(R.id.gp);
        name = (TextView) findViewById(R.id.name);
        total = (EditText)findViewById(R.id.total);
        distribution  = (EditText) findViewById(R.id.distribution);
        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);
        dateText  = (TextView) findViewById(R.id.dateText);
        dateText2  = (TextView) findViewById(R.id.dateText2);
        demototal  = (TextView) findViewById(R.id.demototal);
        demodistri  = (TextView) findViewById(R.id.demodistri);

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        //set it as current date.
        dateText.setText(date_n);

        // Get the value of shared preference back
        SharedPreferences getShared = getSharedPreferences("demo2", MODE_PRIVATE);
        String value = getShared.getString("str2","Enter coverage");
        String value3 = getShared.getString("str3","0");
        String value4 = getShared.getString("str4","0");
        dateText2.setText(value);
        demototal.setText(value3);
        demodistri.setText(value4);




        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                school.setText(documentSnapshot.getString("School"));
                category.setText(documentSnapshot.getString("Category"));
                gp.setText(documentSnapshot.getString("GPName"));
                name.setText(documentSnapshot.getString("Name"));

            }
        });


    }

    //This is the part where data is transafeered from Your Android phone to Sheet by using HTTP Rest API calls

    private void   addItemToSheet() {
        if(total.getText().toString().isEmpty() ||distribution.getText().toString().isEmpty()) {
            Toast.makeText(Attendance.this, "Fill the required Details", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog loading = ProgressDialog.show(this,"Sending Attendance","Please wait");
        final String school1 = school.getText().toString().trim();
        final String category1 = category.getText().toString().trim();
        final String gp1 = gp.getText().toString().trim();
        final String name1 = name.getText().toString().trim();

        final String total1 = total.getText().toString().trim();
        final String distribution1 = distribution.getText().toString().trim();
        final String dateText1 = dateText.getText().toString().trim();






        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyzQ5jFuSNj5e9t3nz259uBe0zQ2c3yglz9ul6ZUU5bmEDBmHs/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(Attendance.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                        startActivity(intent);
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","addItem");
                parmas.put("school",school1);
                parmas.put("category",category1);
                parmas.put("gp",gp1);
                parmas.put("name",name1);
                parmas.put("total",total1);
                parmas.put("distribution",distribution1);
                parmas.put("dateText",dateText1);


                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }









    @Override
    public void onClick(View v) {

        if(v==buttonAddItem){
            final String dateTextn = dateText.getText().toString().trim();
            final String dateTextnn = dateText2.getText().toString().trim();

            if (dateTextn.matches(dateTextnn)){
                Toast.makeText(Attendance.this, " Attendance already added Today ! ", Toast.LENGTH_SHORT).show();
            }else {
                addItemToSheet();
                aladyreenter();
            }






            //Define what to do when button is clicked


        }

    }

    private void aladyreenter() {
        String msg2 = dateText.getText().toString();
        String totalp = total.getText().toString();
        String distrip = distribution.getText().toString();

        SharedPreferences shrd = getSharedPreferences("demo2", MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();

        editor.putString("str2", msg2);
        editor.putString("str3", totalp);
        editor.putString("str4", distrip);

        editor.apply();



    }


    }


