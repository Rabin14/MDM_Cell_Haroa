package block.mdmcellharoa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity2 extends AppCompatActivity {
    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    TextView dise, dyear, dmonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dise = findViewById(R.id.dise);
        dyear = findViewById(R.id.dyear);
        dmonth = findViewById(R.id.dmonth);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.firestore_list);
        // get shareprerference

        SharedPreferences getShared = getSharedPreferences("sharenew", MODE_PRIVATE);
        String value = getShared.getString("str1", "000");
        String value2 = getShared.getString("str2", "000");
        String value3 = getShared.getString("str3", "000");
        dise.setText(value);
        dyear.setText(value2);
        dmonth.setText(value3);

        String userID = dise.getText().toString();
        String userID2 = dyear.getText().toString();
        String userID3 = dmonth.getText().toString();

        //Query
//Query query = productsRef.collection("report");

        DocumentReference productsRef = firebaseFirestore.collection("report").document(userID).collection("year").document(userID2).collection("month").document(userID3);

        Query query = productsRef.collection("report").orderBy("date", Query.Direction.ASCENDING);


        //RecyleView
        FirestoreRecyclerOptions<ProductsModel> options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query, ProductsModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ProductsModel model) {
                holder.ddate.setText(model.getDate());
                holder.total.setText(model.getTotal());
                holder.present.setText(model.getPresent());
            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);
    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder {

        private TextView ddate;
        private TextView total;
        private TextView present;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            ddate = itemView.findViewById(R.id.ddate);
            total = itemView.findViewById(R.id.total);
            present = itemView.findViewById(R.id.present);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}