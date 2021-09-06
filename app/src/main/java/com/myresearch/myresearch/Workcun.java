package com.myresearch.myresearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Workcun extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<DataModel> idates = new ArrayList();
    Adapter adapter;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    LinearLayoutManager linearLayoutManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference db1=FirebaseFirestore.getInstance().collection(auth.getUid()+"set");
    TextView textView;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workcun);
        bar=findViewById(R.id.proempty);
        bar.setVisibility(View.VISIBLE);
        textView=findViewById(R.id.textno);
        recyclerView=findViewById(R.id.rec);
        //idates.add(new DataModel("333333333","3333333","5456456","789","88y89","ok"));
        adapter = new Adapter(idates,this);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        bar.setVisibility(View.GONE);


    }

    @Override
    protected void onStart() {
        super.onStart();
        db1.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                idates.clear();

                for (QueryDocumentSnapshot querySnapshot : value){
                    DataModel dater1 = querySnapshot.toObject(DataModel.class);
                    textView.setText("");
                    bar.setVisibility(View.GONE);
                    idates.add(dater1);
                }
                adapter.notifyDataSetChanged();



            }
        });



}



}