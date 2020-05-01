package block.mdmcellharoa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;

import android.os.Bundle;

public class Report_Coverage extends AppCompatActivity {
    ListView listView;
    SimpleAdapter adapter;
    ProgressDialog loading;
    EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report__coverage);
        listView = (ListView) findViewById(R.id.lv_items);
        et_search = (EditText) findViewById(R.id.et_search);

        getItems();
    }
    private void getItems() {

        loading =  ProgressDialog.show(this,"Loading","please wait",false,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbykuCwT2UPdXrshFGPO7t31nw4ixVAl6rStSwRKI6WNMUj-JkUi/exec?action=getItems",
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
                String school1 = jo.getString("school");
                String class_pp1 = jo.getString("class_pp");
                String class_v1 = jo.getString("class_v");
                String class_vi1 = jo.getString("class_vi");




                HashMap<String, String> item = new HashMap<>();
                item.put("date_n", date_n1);
                item.put("school", school1);
                item.put("class_pp", class_pp1);
                item.put("class_v",class_v1);
                item.put("class_vi",class_vi1);


                list.add(item);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter = new SimpleAdapter(this,list,R.layout.list_item_coverage,
                new String[]{"date_n","school","class_pp","class_v","class_vi"},new int[]{R.id.date_n,R.id.school,R.id.class_pp,R.id.class_v,R.id.class_vi});


        listView.setAdapter(adapter);
        loading.dismiss();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Report_Coverage.this.adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


}



