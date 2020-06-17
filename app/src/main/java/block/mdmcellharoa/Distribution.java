package block.mdmcellharoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.os.Bundle;

public class Distribution extends AppCompatActivity implements View.OnClickListener{
    TextView school,category,gp,demototal,demodistri,name,dateText,dateText2,pp1,pp2,pp,pp3,pp4,pp6,pp7,pp8,dise;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    public static final String TAG = "TAG";
    private static final String KEY_DATE = "date";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_PRESENT = "present";
    String userId;
    EditText class_pp,class_one,class_two,class_three,class_four,class_five,class_six,class_seven,class_eight,class_pp_total,class_one_total,class_two_total,class_three_total,class_four_total,class_five_total,class_six_total,class_seven_total,class_eight_total;
    Button buttonAddItem;
    private View mViewGroup1,mViewGroup2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution);
        dise = (TextView) findViewById(R.id.dise);
        class_pp  = findViewById(R.id.class_pp);
        class_one  = findViewById(R.id.class_one);
        class_two  = findViewById(R.id.class_two);
        class_three  = findViewById(R.id.class_three);
        class_four  = findViewById(R.id.class_four);

        class_five  = findViewById(R.id.class_five);

        class_six  = findViewById(R.id.class_six);
        class_seven  = findViewById(R.id.class_seven);
        class_eight  = findViewById(R.id.class_eight);

        class_pp_total  = findViewById(R.id.class_pp_total);
        class_one_total  = findViewById(R.id.class_one_total);
        class_two_total  = findViewById(R.id.class_two_total);
        class_three_total  = findViewById(R.id.class_three_total);
        class_four_total  = findViewById(R.id.class_four_total);
        class_five_total  = findViewById(R.id.class_five_total);
        class_six_total  = findViewById(R.id.class_six_total);
        class_seven_total  = findViewById(R.id.class_seven_total);
        class_eight_total  = findViewById(R.id.class_eight_total);

        class_pp_total.requestFocus();
        demototal  = (TextView) findViewById(R.id.demototal);
        demodistri  = (TextView) findViewById(R.id.demodistri);
        dateText2  = (TextView) findViewById(R.id.dateText2);
        dateText  = (TextView) findViewById(R.id.dateText);
        school    = (TextView) findViewById(R.id.school);
        category = (TextView) findViewById(R.id.category);
        gp = (TextView) findViewById(R.id.gp);
        name = (TextView) findViewById(R.id.name);

        pp = (TextView) findViewById(R.id.pp);
        pp1 = (TextView) findViewById(R.id.pp1);
        pp2 = (TextView) findViewById(R.id.pp2);
        pp3 = (TextView) findViewById(R.id.pp3);
        pp4 = (TextView) findViewById(R.id.pp4);
        pp6 = (TextView) findViewById(R.id.pp6);
        pp7 = (TextView) findViewById(R.id.pp7);
        pp8 = (TextView) findViewById(R.id.pp8);


        mViewGroup1 = findViewById(R.id.layout_to_hide1);
        mViewGroup2 = findViewById(R.id.layout_to_hide2);



        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
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


        /** Get the value of shared preference back
        SharedPreferences getShared = getSharedPreferences("demo4", MODE_PRIVATE);
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

                show();
            }
        });







    }

    //This is the part where data is transafeered from Your Android phone to Sheet by using HTTP Rest API calls

    private void   addItemToSheet() {



        final ProgressDialog loading = ProgressDialog.show(this,"Adding Coverage","Please wait");
        final String school1 = school.getText().toString().trim();
        final String category1 = category.getText().toString().trim();
        final String gp1 = gp.getText().toString().trim();

        final String name1 = name.getText().toString().trim();

        final String class_pp1 = class_pp.getText().toString().trim();
        final String class_one1 = class_one.getText().toString().trim();
        final String class_two2 = class_two.getText().toString().trim();
        final String class_three2 = class_three.getText().toString().trim();
        final String class_four2 = class_four.getText().toString().trim();
        final String class_five2 = class_five.getText().toString().trim();
        final String class_six2 = class_six.getText().toString().trim();
        final String class_seven2 = class_seven.getText().toString().trim();
        final String class_eight2 = class_eight.getText().toString().trim();


        final String class_pp1_total = class_pp_total.getText().toString().trim();
        final String class_one1_total = class_one_total.getText().toString().trim();
        final String class_two2_total = class_two_total.getText().toString().trim();
        final String class_three2_total = class_three_total.getText().toString().trim();
        final String class_four2_total = class_four_total.getText().toString().trim();
        final String class_five2_total = class_five_total.getText().toString().trim();
        final String class_six2_total = class_six_total.getText().toString().trim();
        final String class_seven2_total = class_seven_total.getText().toString().trim();
        final String class_eight2_total = class_eight_total.getText().toString().trim();


        final String dateText1 = dateText.getText().toString().trim();

        //add sum function
        if (class_pp_total.getText().toString().length() == 0) {
            class_pp_total.setText("0");
        }
        if (class_one_total.getText().toString().length() == 0) {
            class_one_total.setText("0");
        }
        if (class_two_total.getText().toString().length() == 0) {
            class_two_total.setText("0");
        }
        if (class_three_total.getText().toString().length() == 0) {
            class_three_total.setText("0");
        }

        if (class_four_total.getText().toString().length() == 0) {
            class_four_total.setText("0");
        }
        if (class_five_total.getText().toString().length() == 0) {
            class_five_total.setText("0");
        }
        if (class_six_total.getText().toString().length() == 0) {
            class_six_total.setText("0");
        }
        if (class_seven_total.getText().toString().length() == 0) {
            class_seven_total.setText("0");
        }
        if (class_eight_total.getText().toString().length() == 0) {
            class_eight_total.setText("0");
        }
        int num1 = Integer.parseInt(class_pp_total.getText().toString());
        int num2 = Integer.parseInt(class_one_total.getText().toString());
        int num3 = Integer.parseInt(class_two_total.getText().toString());
        int num4 = Integer.parseInt(class_three_total.getText().toString());
        int num5 = Integer.parseInt(class_four_total.getText().toString());
        int num6 = Integer.parseInt(class_five_total.getText().toString());
        int num7 = Integer.parseInt(class_six_total.getText().toString());
        int num8 = Integer.parseInt(class_seven_total.getText().toString());
        int num9 = Integer.parseInt(class_eight_total.getText().toString());
        int sum = num1 + num2+num3+num4+num5+num6+num7+num8+num9;
        demototal.setText(String.valueOf(sum));


        if (class_pp.getText().toString().length() == 0) {
            class_pp.setText("0");
        }
        if (class_one.getText().toString().length() == 0) {
            class_one.setText("0");
        }
        if (class_two.getText().toString().length() == 0) {
            class_two.setText("0");
        }
        if (class_three.getText().toString().length() == 0) {
            class_three.setText("0");
        }

        if (class_four.getText().toString().length() == 0) {
            class_four.setText("0");
        }
        if (class_five.getText().toString().length() == 0) {
            class_five.setText("0");
        }
        if (class_six.getText().toString().length() == 0) {
            class_six.setText("0");
        }
        if (class_seven.getText().toString().length() == 0) {
            class_seven.setText("0");
        }
        if (class_eight.getText().toString().length() == 0) {
            class_eight.setText("0");
        }
        int num100 = Integer.parseInt(class_pp.getText().toString());
        int num200 = Integer.parseInt(class_one.getText().toString());
        int num300 = Integer.parseInt(class_two.getText().toString());
        int num400 = Integer.parseInt(class_three.getText().toString());
        int num500 = Integer.parseInt(class_four.getText().toString());
        int num600 = Integer.parseInt(class_five.getText().toString());
        int num700 = Integer.parseInt(class_six.getText().toString());
        int num800 = Integer.parseInt(class_seven.getText().toString());
        int num900 = Integer.parseInt(class_eight.getText().toString());
        int sum2 = num100+ num200+num300+num400+num500+num600+num700+num800+num900;
        demodistri.setText(String.valueOf(sum2));








        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbwl8yFG4DASk6YRy0_ShHcxNxKDOOhcqF4mJHJINaXTfyg4EJKC/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(Distribution.this,response,Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(getApplicationContext(),Piechart_Distribution.class);

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

                parmas.put("class_pp",class_pp1);
                parmas.put("class_one",class_one1);
                parmas.put("class_two",class_two2);
                parmas.put("class_three",class_three2);
                parmas.put("class_four",class_four2);
                parmas.put("class_five",class_five2);
                parmas.put("class_six",class_six2);
                parmas.put("class_seven",class_seven2);
                parmas.put("class_eight",class_eight2);
                parmas.put("class_pp_total",class_pp1_total);
                parmas.put("class_one_total",class_one1_total);
                parmas.put("class_two_total",class_two2_total);
                parmas.put("class_three_total",class_three2_total);
                parmas.put("class_four_total",class_four2_total);
                parmas.put("class_five_total",class_five2_total);
                parmas.put("class_six_total",class_six2_total);
                parmas.put("class_seven_total",class_seven2_total);
                parmas.put("class_eight_total",class_eight2_total);






                parmas.put("dateText",dateText1);





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
            final String dateTextn = dateText.getText().toString().trim();
            final String dateTextnn = dateText2.getText().toString().trim();

            if (dateTextn.matches(dateTextnn)){
                Toast.makeText(Distribution.this, " Distribution already added For Today ! ", Toast.LENGTH_SHORT).show();
                String msg2 = dateText.getText().toString();
                String totalp = demototal.getText().toString();
                String distrip = demodistri.getText().toString();
                SharedPreferences shrd = getSharedPreferences("demo4", MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();

                editor.putString("str2", msg2);
                editor.putString("str3", totalp);
                editor.putString("str4", distrip);

                editor.apply();

                Intent intent = new Intent(getApplicationContext(),Piechart_Distribution.class);
                startActivity(intent);
                finish();
            }else {
                addItemToSheet();

                addfirebase();
                aladyreenter();
            }






            //Define what to do when button is clicked
        }

    }




    private void show() {

        final String category1 = category.getText().toString().trim();

        if (category1.matches("Primary")) {
            class_six.setVisibility(View.INVISIBLE);
            class_seven.setVisibility(View.INVISIBLE);
            class_eight.setVisibility(View.INVISIBLE);
            class_six_total.setVisibility(View.INVISIBLE);
            class_seven_total.setVisibility(View.INVISIBLE);
            class_eight_total.setVisibility(View.INVISIBLE);
            pp6.setVisibility(View.INVISIBLE);
            pp7.setVisibility(View.INVISIBLE);
            pp8.setVisibility(View.INVISIBLE);
            mViewGroup2.setVisibility(View.GONE);



        }

        if (category1.matches("Upper Primary")) {
            class_pp.setVisibility(View.INVISIBLE);
            class_one.setVisibility(View.INVISIBLE);
            class_two.setVisibility(View.INVISIBLE);
            class_three.setVisibility(View.INVISIBLE);
            class_four.setVisibility(View.INVISIBLE);

            class_pp_total.setVisibility(View.INVISIBLE);
            class_one_total.setVisibility(View.INVISIBLE);
            class_two_total.setVisibility(View.INVISIBLE);
            class_three_total.setVisibility(View.INVISIBLE);
            class_four_total.setVisibility(View.INVISIBLE);

            pp.setVisibility(View.INVISIBLE);
            pp1.setVisibility(View.INVISIBLE);
            pp2.setVisibility(View.INVISIBLE);
            pp3.setVisibility(View.INVISIBLE);
            pp4.setVisibility(View.INVISIBLE);
            mViewGroup1.setVisibility(View.GONE);
        }



    }
    private void aladyreenter() {
        String msg2 = dateText.getText().toString();
        String totalp = demototal.getText().toString();
        String distrip = demodistri.getText().toString();
        SharedPreferences shrd = getSharedPreferences("demo4", MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();

        editor.putString("str2", msg2);
        editor.putString("str3", totalp);
        editor.putString("str4", distrip);

        editor.apply();


    }
    private void addfirebase() {


        String userID2 = dise.getText().toString();

        DocumentReference docRef = fStore.collection("distribution").document(userID2);
        Map<String,Object> user = new HashMap<>();
        user.put("date",dateText.getText().toString());
        user.put("total",demototal.getText().toString());
        user.put("present",demodistri.getText().toString());


        //add user to database
        docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Data Send" );

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
        DocumentReference noteRef = db.collection("distribution").document(userID2);
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
                            Toast.makeText(Distribution.this, "Enter Your Coverage", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Distribution.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
}
