package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.os.Bundle;

public class Distribution extends AppCompatActivity implements View.OnClickListener{
    TextView school,category,gp;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    EditText total,distribution;
    Button buttonAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution);
        school    = (TextView) findViewById(R.id.school);
        category = (TextView) findViewById(R.id.category);
        gp = (TextView) findViewById(R.id.gp);
        total = (EditText)findViewById(R.id.total);
        distribution  = (EditText) findViewById(R.id.distribution);
        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);





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

            }
        });


    }

    //This is the part where data is transafeered from Your Android phone to Sheet by using HTTP Rest API calls

    private void   addItemToSheet() {
        if(total.getText().toString().isEmpty() ||distribution.getText().toString().isEmpty()) {
            Toast.makeText(Distribution.this, "Fill the required Details", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog loading = ProgressDialog.show(this,"Adding Item","Please wait");
        final String school1 = school.getText().toString().trim();
        final String category1 = category.getText().toString().trim();
        final String gp1 = gp.getText().toString().trim();
        final String total1 = total.getText().toString().trim();
        final String distribution1 = distribution.getText().toString().trim();






        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbwl8yFG4DASk6YRy0_ShHcxNxKDOOhcqF4mJHJINaXTfyg4EJKC/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(Distribution.this,response,Toast.LENGTH_LONG).show();
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
                parmas.put("total",total1);
                parmas.put("distribution",distribution1);


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

            addItemToSheet();

            //Define what to do when button is clicked
        }

    }
}
