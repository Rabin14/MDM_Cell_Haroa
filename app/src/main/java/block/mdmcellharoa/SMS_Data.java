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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SMS_Data extends AppCompatActivity implements View.OnClickListener {
    TextView school,category,gp,name,ddate;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    EditText dise,school_name,incharge,degi1,mobile1,teacher2,degi2,mobile2,teacher3,degi3,mobile3,pp,pp1,pp2,pp3,pp4,pp5,pp6,pp7,pp8,sc_male,sc_female,st_male,st_female,obc_male,obc_female,minority_male,minority_female,others_male,others_female;
    Button buttonAddItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_m_s__data);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        ddate = findViewById(R.id.ddate);
        ddate.setText("" + year);

        school    = (TextView) findViewById(R.id.school);
        category = (TextView) findViewById(R.id.category);
        gp = (TextView) findViewById(R.id.gp);
        name = (TextView) findViewById(R.id.name);

        dise = (EditText)findViewById(R.id.dise);
        school_name = (EditText)findViewById(R.id.school_name);
        incharge = (EditText)findViewById(R.id.incharge);
        degi1 = (EditText)findViewById(R.id.degi1);
        mobile1 = (EditText)findViewById(R.id.mobile1);
        teacher2 = (EditText)findViewById(R.id.teacher2);
        degi2 = (EditText)findViewById(R.id.degi2);
        mobile2 = (EditText)findViewById(R.id.mobile2);
        teacher3 = (EditText)findViewById(R.id.teacher3);
        degi3 = (EditText)findViewById(R.id.degi3);
        mobile3 = (EditText)findViewById(R.id.mobile3);
        pp = (EditText)findViewById(R.id.pp);
        pp1 = (EditText)findViewById(R.id.pp1);
        pp2 = (EditText)findViewById(R.id.pp2);
        pp3 = (EditText)findViewById(R.id.pp3);
        pp4 = (EditText)findViewById(R.id.pp4);
        pp5 = (EditText)findViewById(R.id.pp5);
        pp6 = (EditText)findViewById(R.id.pp6);
        pp7 = (EditText)findViewById(R.id.pp7);
        pp8 = (EditText)findViewById(R.id.pp8);
        sc_male = (EditText)findViewById(R.id.sc_male);
        sc_female = (EditText)findViewById(R.id.sc_female);
        st_male = (EditText)findViewById(R.id.st_male);
        st_female = (EditText)findViewById(R.id.st_female);
        obc_male = (EditText)findViewById(R.id.obc_male);
        obc_female = (EditText)findViewById(R.id.obc_female);
        minority_male = (EditText)findViewById(R.id.minority_male);
        minority_female = (EditText)findViewById(R.id.minority_female);
        others_male = (EditText)findViewById(R.id.others_male);
        others_female = (EditText)findViewById(R.id.others_female);


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
        if(dise.getText().toString().isEmpty() ||school_name.getText().toString().isEmpty()) {
            Toast.makeText(SMS_Data.this, "Fill the required Details", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog loading = ProgressDialog.show(this,"Sending Details","Please wait");
        final String school1 = school.getText().toString().trim();
        final String category1 = category.getText().toString().trim();
        final String gp1 = gp.getText().toString().trim();
        final String name1 = name.getText().toString().trim();

        final String ddate1 = ddate.getText().toString().trim();
        final String dise1 = dise.getText().toString().trim();
        final String school_name1 = school_name.getText().toString().trim();
        final String incharge1 = incharge.getText().toString().trim();
        final String degi11 = degi1.getText().toString().trim();
        final String mobile11 = mobile1.getText().toString().trim();
        final String teacher21 = teacher2.getText().toString().trim();
        final String degi21 = degi2.getText().toString().trim();
        final String mobile21 = mobile2.getText().toString().trim();
        final String teacher31 = teacher3.getText().toString().trim();
        final String degi31 = degi3.getText().toString().trim();
        final String mobile31 = mobile3.getText().toString().trim();
        final String pp_1 = pp.getText().toString().trim();
        final String pp11 = pp1.getText().toString().trim();
        final String pp21 = pp2.getText().toString().trim();
        final String pp31 = pp3.getText().toString().trim();
        final String pp41 = pp4.getText().toString().trim();
        final String pp51 = pp5.getText().toString().trim();
        final String pp61 = pp6.getText().toString().trim();
        final String pp71 = pp7.getText().toString().trim();
        final String pp81 = pp8.getText().toString().trim();
        final String sc_male1 = sc_male.getText().toString().trim();
        final String sc_female1 = sc_female.getText().toString().trim();
        final String st_male1 = st_male.getText().toString().trim();
        final String st_female1 = st_female.getText().toString().trim();
        final String obc_male1 = obc_male.getText().toString().trim();
        final String obc_female1 = obc_female.getText().toString().trim();
        final String minority_male1 = minority_male.getText().toString().trim();
        final String minority_female1 = minority_female.getText().toString().trim();
        final String others_male1 = others_male.getText().toString().trim();
        final String others_female1 = others_female.getText().toString().trim();








        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbz7rO8rXsuC9ypcwma6kSTielHBIo7YNVOKNdjcrY4LYtpWRHw/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(SMS_Data.this,response,Toast.LENGTH_LONG).show();
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
                parmas.put("ddate",ddate1);
                parmas.put("dise",dise1);
                parmas.put("school_name",school_name1);
                parmas.put("incharge",incharge1);
                parmas.put("degi1",degi11);
                parmas.put("mobile1",mobile11);
                parmas.put("teacher2",teacher21);
                parmas.put("degi2",degi21);
                parmas.put("mobile2",mobile21);
                parmas.put("teacher3",teacher31);
                parmas.put("degi3",degi31);
                parmas.put("mobile3",mobile31);
                parmas.put("pp",pp_1);
                parmas.put("pp1",pp11);
                parmas.put("pp2",pp21);
                parmas.put("pp3",pp31);
                parmas.put("pp4",pp41);
                parmas.put("pp5",pp51);
                parmas.put("pp6",pp61);
                parmas.put("pp7",pp71);
                parmas.put("pp8",pp81);
                parmas.put("sc_male",sc_male1);
                parmas.put("sc_female",sc_female1);
                parmas.put("st_male",st_male1);
                parmas.put("st_female",st_female1);
                parmas.put("obc_male",obc_male1);
                parmas.put("obc_female",obc_female1);
                parmas.put("minority_male",minority_male1);
                parmas.put("minority_female",minority_female1);
                parmas.put("others_male",others_male1);
                parmas.put("others_female",others_female1);



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