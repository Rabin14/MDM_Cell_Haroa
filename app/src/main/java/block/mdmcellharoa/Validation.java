package block.mdmcellharoa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;

public class Validation extends AppCompatActivity {
    TextView mobile, rabin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        mobile = (TextView) findViewById(R.id.mobile);
        rabin = (TextView) findViewById(R.id.rabin);

        Bundle bb;
        bb = getIntent().getExtras();
        mobile.setText(bb.getString("phoneno"));


        fStore = FirebaseFirestore.getInstance();
        userId = mobile.getText().toString().trim();


        DocumentReference documentReference = fStore.collection("phone").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                rabin.setText(documentSnapshot.getString("mobile"));
                String getvalue = rabin.getText().toString().trim();
                checkUserProfile();


            }
        });


    }

    private void checkUserProfile() {
        String getvalue = rabin.getText().toString().trim();

        if (TextUtils.isEmpty(getvalue)) {
            Toast.makeText(Validation.this, "Your Mobile Number is not Register", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), ErrorPage.class));
            finish();
        } else {
            //Toast.makeText(Register.this, "Profile Do not Exists.", Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent ( Validation.this, Details.class );
            // startActivity(intent);

            startActivity(new Intent(getApplicationContext(), Details.class));
            finish();
        }
    }
}
