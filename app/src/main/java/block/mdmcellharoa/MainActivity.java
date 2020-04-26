package block.mdmcellharoa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    TextView fullName, school;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("MDM, Haroa Block");


        school = findViewById(R.id.schoolname);
        fullName = findViewById(R.id.profileName);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                school.setText(documentSnapshot.getString("School"));
                fullName.setText(documentSnapshot.getString("Name"));

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
            Intent intent = new Intent(MainActivity.this, web.class);
            intent.putExtra("url", "https://mdm-cell-haroa.web.app/");
            startActivity(intent);

        } else if (item.getItemId() == R.id.feedback) {
            Intent intent = new Intent(MainActivity.this, Feedback.class);
            startActivity(intent);

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
        Intent intent = new Intent(getApplicationContext(), Attendance.class);
        startActivity(intent);

    }

    public void extra(View view) {
        Intent intent = new Intent(MainActivity.this, Miscellaneous.class);

        startActivity(intent);


    }

    public void coverage(View view) {
        Intent intent = new Intent(getApplicationContext(), Coverage.class);
        startActivity(intent);


    }

    public void notification(View view) {
        Intent intent = new Intent(getApplicationContext(), ListItem.class);
        startActivity(intent);


    }
}