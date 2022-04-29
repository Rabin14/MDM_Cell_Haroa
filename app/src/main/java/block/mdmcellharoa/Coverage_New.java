package block.mdmcellharoa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Coverage_New extends AppCompatActivity {
    TextView school, category, gp, name, dateText, dateText2, demototal1, demodistri1, dise;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    public static final String TAG = "TAG";
    private static final String KEY_DATE = "date";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_PRESENT = "present";
    EditText dyear, month, demototal, demodistri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverage_new);


        dise = (TextView) findViewById(R.id.dise);
        dyear = (EditText) findViewById(R.id.year);
        month = (EditText) findViewById(R.id.month);


        dateText2 = (TextView) findViewById(R.id.dateText2);
        dateText = (TextView) findViewById(R.id.dateText);
        school = (TextView) findViewById(R.id.school);
        category = (TextView) findViewById(R.id.category);
        gp = (TextView) findViewById(R.id.gp);
        name = (TextView) findViewById(R.id.name);

        demototal = findViewById(R.id.demototal);
        demototal1 = findViewById(R.id.demototal1);
        demodistri1 = findViewById(R.id.demodistri1);
        demodistri = findViewById(R.id.demodistri);
        demototal.requestFocus();


        //get month
        String monthname = (String) android.text.format.DateFormat.format("MMM", new Date());
        Calendar calendar = Calendar.getInstance();
        month.setText(monthname);
        //get year
        Calendar calendar1 = Calendar.getInstance();
        int year = calendar1.get(Calendar.YEAR);
        dyear.setText("" + year);

        String date_n = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //set it as current date.
        dateText.setText(date_n);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Get the value of dise from share prefer  back
        SharedPreferences getShared = getSharedPreferences("disecode", MODE_PRIVATE);
        String value = getShared.getString("dise", "Enter coverage");
        dise.setText(value);


//load firebase
        loadNote();

            /*Get the value of shared preference back
             SharedPreferences getShared = getSharedPreferences("demo3", MODE_PRIVATE);
             String value = getShared.getString("str2","Enter coverage");

             String value3 = getShared.getString("str3","0");
             String value4 = getShared.getString("str4","0");
             dateText2.setText(value);
             demototal.setText(value3);
             demodistri.setText(value4);
             */

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

    @SuppressLint("SetTextI18n")
    private void addItemToSheet() {


        final ProgressDialog loading = ProgressDialog.show(this, "Adding Coverage", "Please wait");
        final String school1 = school.getText().toString().trim();
        final String category1 = category.getText().toString().trim();
        final String gp1 = gp.getText().toString().trim();

        final String name1 = name.getText().toString().trim();


        final String s_total = demototal.getText().toString().trim();
        final String d_total = demodistri.getText().toString().trim();


        final String dateText1 = dateText.getText().toString().trim();

        //add
        if (demototal.getText().toString().length() == 0) {
            demototal.setText("0");
        }
        if (demodistri.getText().toString().length() == 0) {
            demodistri.setText("0");
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyG52VcPFH1FKmEEWMMiXTRbXnqgg6cQBZ7T5qHeajHncHWforur9CIQ2ZzUne2eBd-8w/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(Coverage_New.this, response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), Piechart_Coverage.class);

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
                parmas.put("action", "addItem");
                parmas.put("school", school1);
                parmas.put("category", category1);
                parmas.put("gp", gp1);
                parmas.put("name", name1);
                parmas.put("total", s_total);
                parmas.put("distribution", d_total);
                parmas.put("dateText", dateText1);
                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }


    private void aladyreenter() {
        String msg2 = dateText.getText().toString();
        String totalp = demototal.getText().toString();
        String distrip = demodistri.getText().toString();
        SharedPreferences shrd = getSharedPreferences("demo3", MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();

        editor.putString("str2", msg2);
        editor.putString("str3", totalp);
        editor.putString("str4", distrip);

        editor.apply();


    }

    private void addfirebase() {


        String userID2 = dise.getText().toString();

        DocumentReference docRef = fStore.collection("coverage").document(userID2);
        Map<String, Object> user = new HashMap<>();
        user.put("date", dateText.getText().toString());
        user.put("total", demototal.getText().toString());
        user.put("present", demodistri.getText().toString());


        //add user to database
        docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Data Send");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Data Not Send " + e.toString());

            }
        });

    }

    private void loadNote() {

        String userID2 = dise.getText().toString();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference noteRef = db.collection("coverage").document(userID2);
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String date = documentSnapshot.getString(KEY_DATE);
                            String total = documentSnapshot.getString(KEY_TOTAL);
                            String present = documentSnapshot.getString(KEY_PRESENT);
                            //Map<String, Object> note = documentSnapshot.getData();
                            dateText2.setText(date);
                            demototal1.setText(total);
                            demodistri1.setText(present);

                        } else {
                            Toast.makeText(Coverage_New.this, "Enter Your Coverage", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Coverage_New.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    private void addreport() {

        String userID = dise.getText().toString();
        String userID2 = dyear.getText().toString();
        String userID3 = month.getText().toString();


        DocumentReference docRef = fStore
                .collection("report").document(userID)
                .collection("year").document(userID2)
                .collection("month").document(userID3)
                .collection("report").document();

        Map<String, Object> user = new HashMap<>();

        user.put("date", dateText.getText().toString());
        user.put("total", demototal.getText().toString());
        user.put("present", demodistri.getText().toString());

        //add user to database
        docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Data Added");
                Toast.makeText(Coverage_New.this, "Data Added", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Data Not Send " + e.toString());
                Toast.makeText(Coverage_New.this, "Data  Not Added", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void additemToSheet(View view) {

        final String dateTextn = dateText.getText().toString().trim();
        final String dateTextnn = dateText2.getText().toString().trim();

        if (dateTextn.matches(dateTextnn)) {
            Toast.makeText(Coverage_New.this, " Coverage already send! ", Toast.LENGTH_SHORT).show();

            //share preference
            String msg2 = dateText.getText().toString();
            String totalp = demototal.getText().toString();
            String distrip = demodistri.getText().toString();
            SharedPreferences shrd = getSharedPreferences("demo3", MODE_PRIVATE);
            SharedPreferences.Editor editor = shrd.edit();

            editor.putString("str2", msg2);
            editor.putString("str3", totalp);
            editor.putString("str4", distrip);

            editor.apply();
            //


            Intent intent = new Intent(getApplicationContext(), Piechart_Coverage.class);

            startActivity(intent);
            finish();
        } else {
            addItemToSheet();
            addreport();
            addfirebase();
            aladyreenter();
        }
    }

}

