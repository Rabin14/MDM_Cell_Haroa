package block.mdmcellharoa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class CCH_Details extends AppCompatActivity implements View.OnClickListener {
    TextView school,category,gp,name;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    EditText shg_name,cch_name,mobile,caste,account,bank_name,branch_name,ifsc;
    Button buttonAddItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_c_h__details);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        school    = (TextView) findViewById(R.id.school);
        category = (TextView) findViewById(R.id.category);
        gp = (TextView) findViewById(R.id.gp);
        name = (TextView) findViewById(R.id.name);

        shg_name = (EditText)findViewById(R.id.shg_name);
        cch_name = (EditText)findViewById(R.id.cch_name);
        mobile = (EditText)findViewById(R.id.mobile);
        caste = (EditText)findViewById(R.id.caste);
        account = (EditText)findViewById(R.id.account);
        bank_name = (EditText)findViewById(R.id.bank_name);
        branch_name = (EditText)findViewById(R.id.branch_name);
        ifsc = (EditText)findViewById(R.id.ifsc);



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
                name.setText(documentSnapshot.getString("Name"));

            }
        });


    }

    //This is the part where data is transafeered from Your Android phone to Sheet by using HTTP Rest API calls

    private void   addItemToSheet() {
        if(shg_name.getText().toString().isEmpty()||cch_name.getText().toString().isEmpty()||mobile.getText().toString().isEmpty()||caste.getText().toString().isEmpty()||account.getText().toString().isEmpty()||bank_name.getText().toString().isEmpty()||branch_name.getText().toString().isEmpty()||ifsc.getText().toString().isEmpty()) {
            Toast.makeText(CCH_Details.this, "Fill the required Details", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog loading = ProgressDialog.show(this,"Sending CCH Details","Please wait");
        final String school1 = school.getText().toString().trim();
        final String category1 = category.getText().toString().trim();
        final String gp1 = gp.getText().toString().trim();
        final String name1 = name.getText().toString().trim();
        final String shg_name1 = shg_name.getText().toString().trim();
        final String cch_name1 = cch_name.getText().toString().trim();
        final String mobile1 = mobile.getText().toString().trim();
        final String caste1 = caste.getText().toString().trim();
        final String account1 = account.getText().toString().trim();
        final String bank_name1 = bank_name.getText().toString().trim();
        final String branch_name1 = branch_name.getText().toString().trim();
        final String ifsc1 = ifsc.getText().toString().trim();






        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxg4PQZT68_VdRltRuO70Sh9wlpsYoi7xQF1DCLcW06D4Coz-6V/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(CCH_Details.this,response,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),Miscellaneous.class);

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
                parmas.put("shg_name",shg_name1);
                parmas.put("cch_name",cch_name1);
                parmas.put("mobile",mobile1);
                parmas.put("caste",caste1);
                parmas.put("account",account1);
                parmas.put("bank_name",bank_name1);
                parmas.put("branch_name",branch_name1);
                parmas.put("ifsc",ifsc1);




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

    public void view(View view) {

        Intent intent = new Intent(getApplicationContext(),List_item_CCH.class);
        startActivity(intent);
        finish();
    }
}
