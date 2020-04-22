package block.mdmcellharoa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    TextView fullName,school;
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
        getMenuInflater().inflate(R.menu.menuitem,menu);



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.signout);{

            FirebaseAuth.getInstance().signOut();//logout
            startActivity(new Intent(getApplicationContext(),PhoneRegister.class));
            finish();

        }


        if (item.getItemId()==R.id.privacy);{
            Intent intent = new Intent (MainActivity.this, web.class);
            intent.putExtra("url", "https://mdm-cell-haroa.web.app/");
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }




    public void covid19(View view) {
        Intent intent = new Intent(getApplicationContext(),Distribution.class);
        startActivity(intent);

    }
}