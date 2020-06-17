package block.mdmcellharoa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView fullName, school, dateText, dateText2, dateText3, dise;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("MDM, Haroa Block");

        dise = findViewById(R.id.dise);
        school = findViewById(R.id.schoolname);
        fullName = findViewById(R.id.profileName);
        dateText3 = (TextView) findViewById(R.id.dateText3);
        dateText2 = (TextView) findViewById(R.id.dateText2);
        dateText = (TextView) findViewById(R.id.dateText);

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        //set it as current date.
        dateText.setText(date_n);

       // Get the value of shared preference back
         SharedPreferences getShared = getSharedPreferences("demo2", MODE_PRIVATE);
         String value = getShared.getString("str2","attendance");
         dateText2.setText(value);

        // Get the value of shared preference back
        SharedPreferences getShared2 = getSharedPreferences("demo3", MODE_PRIVATE);
        String value2 = getShared2.getString("str2", "coverage");
        dateText3.setText(value2);


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
}