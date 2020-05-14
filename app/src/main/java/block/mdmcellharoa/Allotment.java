package block.mdmcellharoa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Allotment extends AppCompatActivity {

    ListView listView;
    SimpleAdapter adapter;
    ProgressDialog loading;
    EditText et_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allotment);

        listView = (ListView) findViewById(R.id.lv_items);
        et_search = (EditText) findViewById(R.id.et_search);

        getItems();

    }


    private void getItems() {

        loading = ProgressDialog.show(this, "Loading", "please wait", false, true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbx9WGU0K-T6ukWLhVD07Qy5on4pRgg2e9GoISFYEPLkHVD3S2Zn/exec?action=getItems",
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


            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);
                String date_n1 = jo.getString("date_n");
                String subject1 = jo.getString("subject");
                String description1 = jo.getString("description");
                String download1 = jo.getString("download");


                HashMap<String, String> item = new HashMap<>();
                item.put("date_n", date_n1);
                item.put("subject", subject1);
                item.put("description", description1);
                item.put("download", download1);


                list.add(item);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter = new SimpleAdapter(this, list, R.layout.list_itemallotment,
                new String[]{"date_n", "subject", "description", "download"}, new int[]{R.id.date_n, R.id.subject, R.id.description, R.id.download});


        listView.setAdapter(adapter);
        loading.dismiss();


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Allotment.this.adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}