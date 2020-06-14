package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;

public class List_item_CCH extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    ListView listView;
    SimpleAdapter adapter;
    ProgressDialog loading;
    EditText et_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item__c_c_h);

        listView = (ListView) findViewById(R.id.lv_items);
        et_search = (EditText) findViewById(R.id.et_search);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                assert documentSnapshot != null;
                et_search.setText(documentSnapshot.getString("School"));

            }
        });



        getItems();
    }
    private void getItems() {

        loading =  ProgressDialog.show(this,"Loading","please wait",false,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbyCImaULqiirPK2AJBD7iSjSMgGdXKJ_cTz7cz8yv-fWpoEr3w/exec?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }


    private void parseItems(String jsonResposnce) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();





        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");
            final String schooln = et_search.getText().toString().trim();

            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);
                if (jo.getString("school").equals(schooln)){
                    String school100n = jo.getString("school");
                    String school100 = jo.getString("shg_name");
                    String school1 = jo.getString("cch_name");
                    String class_pp1 = jo.getString("mobile");
                    String class_v1 = jo.getString("caste");
                    String class_vi1 = jo.getString("account");
                    String class_vi11 = jo.getString("ifsc");

                    HashMap<String, String> item = new HashMap<>();
                    item.put("school", school100n);
                    item.put("shg_name", school100);
                    item.put("cch_name", school1);
                    item.put("mobile", class_pp1);
                    item.put("caste",class_v1);
                    item.put("account",class_vi1);
                    item.put("ifsc",class_vi11);
                    list.add(item);
                }



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter = new SimpleAdapter(this,list,R.layout.list_item_cchdetails,
                new String[]{"school","shg_name","cch_name","mobile","caste","account","ifsc"},new int[]{R.id.school,R.id.shg_name,R.id.cch_name,R.id.mobile_number,R.id.caste,R.id.ac_number,R.id.ifsc_code});


        listView.setAdapter(adapter);
        loading.dismiss();




    }






}