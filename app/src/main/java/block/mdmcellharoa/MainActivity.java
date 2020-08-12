package block.mdmcellharoa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView fullName, school, dateText, dateText2, dateText3,demototal,demodistri,dise,demototal1,demodistri1,textView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    public static final String TAG = "TAG";
    private static final String KEY_DATE = "date";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_PRESENT = "present";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("MDM, Haroa Block");
        demototal  = (TextView) findViewById(R.id.demototal);
        demodistri  = (TextView) findViewById(R.id.demodistri);
        textView = findViewById(R.id.noti);
        demototal1  = (TextView) findViewById(R.id.demototal1);
        demodistri1  = (TextView) findViewById(R.id.demodistri1);
        dise = findViewById(R.id.dise);
        school = findViewById(R.id.schoolname);
        fullName = findViewById(R.id.profileName);
        dateText3 = (TextView) findViewById(R.id.dateText3);
        dateText2 = (TextView) findViewById(R.id.dateText2);
        dateText = (TextView) findViewById(R.id.dateText);
        textView.setSelected(true);
        String date_n = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //set it as current date.
        dateText.setText(date_n);

       // Get the value of date from firebase back

       /**  SharedPreferences getShared = getSharedPreferences("demo2", MODE_PRIVATE);
         String value = getShared.getString("str2","attendance");
         dateText2.setText(value);
*/
        // Get the value of shared preference back

        /**SharedPreferences getShared2 = getSharedPreferences("demo3", MODE_PRIVATE);
        String value2 = getShared2.getString("str2", "coverage");
        dateText3.setText(value2);
*/
        checkConnection();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                school.setText(documentSnapshot.getString("School"));
                fullName.setText(documentSnapshot.getString("Name"));
                dise.setText(documentSnapshot.getString("dise"));
                loadNote();
                loadNote2();


            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitem, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.signout) {

            FirebaseAuth.getInstance().signOut();//logout
            startActivity(new Intent(getApplicationContext(), PhoneRegister.class));
            finish();

        } else if (item.getItemId() == R.id.privacy) {
            Intent intent = new Intent(MainActivity.this, Web2.class);
            intent.putExtra("url", "https://mdm-cell-haroa.web.app/");
            startActivity(intent);

        } else if (item.getItemId() == R.id.feedback) {
            Intent intent = new Intent(MainActivity.this, Feedback.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.admin) {
            Intent intent = new Intent(MainActivity.this, Admin_Login.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.contactus) {
            Intent intent = new Intent(MainActivity.this, ContactUs.class);
            startActivity(intent);


        } else if (item.getItemId() == R.id.update) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }

        } else if (item.getItemId() == R.id.share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Your Subject");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=block.mdmcellharoa \n\n"; // here define package name of you app
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch (Exception e) {
                Log.e(">>>", "Error: " + e);
            }

        }
        return super.onOptionsItemSelected(item);
    }


    public void attendance(View view) {
        final String dateTextn = dateText.getText().toString().trim();
        final String dateTextnn = dateText2.getText().toString().trim();
        aladyreenter2();
        if (dateTextn.matches(dateTextnn)) {
            Toast.makeText(MainActivity.this, " Attendance already added Today ! ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Piechart_Attendance.class);
            startActivity(intent);

        } else {

//save dise code to sharepreference

            String msg2 = dise.getText().toString();
            SharedPreferences shrd = getSharedPreferences("disecode", MODE_PRIVATE);
            SharedPreferences.Editor editor = shrd.edit();
            editor.putString("dise", msg2);
            editor.apply();
            Intent ii = new Intent(MainActivity.this, Attendance.class);
            startActivity(ii);

        }


    }

    public void extra(View view) {
        String msg2 = dise.getText().toString();
        SharedPreferences shrd = getSharedPreferences("disecode", MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();
        editor.putString("dise", msg2);
        editor.apply();
        Intent intent = new Intent(MainActivity.this, Miscellaneous.class);

        startActivity(intent);


    }

    public void coverage(View view) {

        final String dateTextn1 = dateText.getText().toString().trim();
        final String dateTextnn1 = dateText3.getText().toString().trim();
        aladyreenter();
        if (dateTextn1.matches(dateTextnn1)) {
            Toast.makeText(MainActivity.this, " Coverage already added For Today ! ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Piechart_Coverage.class);

            startActivity(intent);

        } else {
            String msg2 = dise.getText().toString();
            SharedPreferences shrd = getSharedPreferences("disecode", MODE_PRIVATE);
            SharedPreferences.Editor editor = shrd.edit();
            editor.putString("dise", msg2);
            editor.apply();
            Intent intent = new Intent(getApplicationContext(), Coverage.class);
            startActivity(intent);

        }


    }

    public void notification(View view) {
        Intent intent = new Intent(getApplicationContext(), ListItem.class);
        startActivity(intent);


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
                            dateText3.setText(date);
                            demototal.setText(total);
                            demodistri.setText(present);


                        } else {
                            Toast.makeText(MainActivity.this, "Please Submit Today's MDM Coverage", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    private void aladyreenter() {

        String totalp = demototal.getText().toString();
        String distrip = demodistri.getText().toString();
        SharedPreferences shrd = getSharedPreferences("demo3", MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();


        editor.putString("str3", totalp);
        editor.putString("str4", distrip);

        editor.apply();



    }
    private void loadNote2() {

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
                            demototal1.setText(total);
                            demodistri1.setText(present);

                        } else {
                            Toast.makeText(MainActivity.this, "Please Submit Today's Staff Attendance", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    private void aladyreenter2() {

        String totalp = demototal1.getText().toString();
        String distrip = demodistri1.getText().toString();

        SharedPreferences shrd = getSharedPreferences("demo2", MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();


        editor.putString("str3", totalp);
        editor.putString("str4", distrip);

        editor.apply();


    }

    public void report1(View view) {

        Intent intent = new Intent(MainActivity.this, MonthlyReportDasboard.class);
        startActivity(intent);
    }

    public void allotment(View view) {

        Intent intent = new Intent(getApplicationContext(), Allotment.class);
        startActivity(intent);
    }
    public void checkConnection()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)

                this.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo wifi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        NetworkInfo  network=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        if (wifi.isConnected())
        {
            //Internet available

        }
        else if(network.isConnected())
        {
            //Internet available


        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), InternetConn.class);
            startActivity(intent);
            fileList();
        }
    }
}