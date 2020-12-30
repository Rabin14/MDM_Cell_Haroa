package block.mdmcellharoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import android.os.Bundle;

public class Attendance extends AppCompatActivity implements LocationListener, View.OnClickListener {
    TextView school, category, gp, name, dateText, dateText2, demototal, demodistri, dise;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    public static final String TAG = "TAG";
    EditText total, distribution;
    Button buttonAddItem;
    private static final String KEY_DATE = "date";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_PRESENT = "present";
    TextView textView_location;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        textView_location = findViewById(R.id.text_location);
        dise = (TextView) findViewById(R.id.dise);
        school = (TextView) findViewById(R.id.school);
        category = (TextView) findViewById(R.id.category);
        gp = (TextView) findViewById(R.id.gp);
        name = (TextView) findViewById(R.id.name);
        total = (EditText) findViewById(R.id.total);
        distribution = (EditText) findViewById(R.id.distribution);
        buttonAddItem = (Button) findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);
        dateText = (TextView) findViewById(R.id.dateText);
        dateText2 = (TextView) findViewById(R.id.dateText2);
        demototal = (TextView) findViewById(R.id.demototal);
        demodistri = (TextView) findViewById(R.id.demodistri);
        //Runtime  location permissions
        checkGPSStatus();
        if (ContextCompat.checkSelfPermission(Attendance.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Attendance.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        getLocation();

        String date_n = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //set it as current date.
        dateText.setText(date_n);

        // Get the value of dise from share prefer  back
        SharedPreferences getShared = getSharedPreferences("disecode", MODE_PRIVATE);
        String value = getShared.getString("dise", "Enter coverage");
        dise.setText(value);

        getLocation();
//load firebase
        loadNote();
        /**
         SharedPreferences getShared = getSharedPreferences("demo2", MODE_PRIVATE);
         String value = getShared.getString("str2","Enter coverage");
         String value3 = getShared.getString("str3","0");
         String value4 = getShared.getString("str4","0");
         dateText2.setText(value);
         demototal.setText(value3);
         demodistri.setText(value4);
         */


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

    private void addItemToSheet() {
        if (total.getText().toString().isEmpty() || distribution.getText().toString().isEmpty()) {
            Toast.makeText(Attendance.this, "Fill the required Details", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog loading = ProgressDialog.show(this, "Sending Attendance", "Please wait");
        final String school1 = school.getText().toString().trim();
        final String category1 = category.getText().toString().trim();
        final String gp1 = gp.getText().toString().trim();
        final String name1 = name.getText().toString().trim();

        final String total1 = total.getText().toString().trim();
        final String distribution1 = distribution.getText().toString().trim();
        final String dateText1 = dateText.getText().toString().trim();
        final String location1 = textView_location.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxcXNjpliTIdqUzMXQN1zyfBquI2lCVUZSJjQ2lIj5JbEhgoasME79H/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(Attendance.this, response, Toast.LENGTH_LONG).show();
                        Intent ii = new Intent(Attendance.this, Piechart_Attendance.class);
                        startActivity(ii);
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
                parmas.put("total", total1);
                parmas.put("distribution", distribution1);
                parmas.put("dateText", dateText1);
                parmas.put("location", location1);


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

        if (v == buttonAddItem) {
            final String dateTextn = dateText.getText().toString().trim();
            final String dateTextnn = dateText2.getText().toString().trim();
            final String location1 = textView_location.getText().toString().trim();
            if (dateTextn.matches(dateTextnn)) {
                Toast.makeText(Attendance.this, " Attendance already send! ", Toast.LENGTH_SHORT).show();
                //share preference
                String msg2 = dateText.getText().toString();
                String totalp = demototal.getText().toString();
                String distrip = demodistri.getText().toString();
                SharedPreferences shrd = getSharedPreferences("demo2", MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("str2", msg2);
                editor.putString("str3", totalp);
                editor.putString("str4", distrip);
                editor.apply();
                //

                Intent ii = new Intent(Attendance.this, Piechart_Attendance.class);
                startActivity(ii);
                finish();
            }
            if (location1.matches("Please wait.")){
                getLocation();
                Toast.makeText(Attendance.this, "Please wait just a minute to get your location ! ", Toast.LENGTH_SHORT).show();
            }


            else {
                addItemToSheet();

                addfirebase();
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

    private void addfirebase() {


        String userID2 = dise.getText().toString();

        DocumentReference docRef = fStore.collection("attendance").document(userID2);
        Map<String, Object> user = new HashMap<>();
        user.put("date", dateText.getText().toString());
        user.put("total", total.getText().toString());
        user.put("present", distribution.getText().toString());


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
        DocumentReference noteRef = db.collection("attendance").document(userID2);
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
                            demototal.setText(total);
                            demodistri.setText(present);

                        } else {
                            Toast.makeText(Attendance.this, "Enter Your Attendance", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Attendance.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
//////////////////////////////////// auto generate method//////start
    @Override
    public void onLocationChanged(@NonNull Location location) {
      //  Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();

        try {
            Geocoder geocoder = new Geocoder(Attendance.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            textView_location.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
    //////////////////////////////////// auto generate method///////////end
    @SuppressLint("MissingPermission")

    //method get  location
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,Attendance.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //to check GPS rnable or not
    private void checkGPSStatus() {
        LocationManager locationManager = null;
        boolean gps_enabled = false;
        boolean network_enabled = false;
        if ( locationManager == null ) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex){}
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex){}
        if ( !gps_enabled && !network_enabled ){
            AlertDialog.Builder dialog = new AlertDialog.Builder(Attendance.this);
            dialog.setMessage("GPS/Location not enabled");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //this will navigate user to the device location settings screen
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            AlertDialog alert = dialog.create();
            alert.show();
        }
    }
    }



