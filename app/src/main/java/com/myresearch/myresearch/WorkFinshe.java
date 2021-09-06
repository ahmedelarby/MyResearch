package com.myresearch.myresearch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WorkFinshe extends AppCompatActivity {
RecyclerView rec2;
    ArrayList<data_model_finshe> idates2 = new ArrayList();
    Adapterrec2 adapter;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    LinearLayoutManager linearLayoutManager;
    CollectionReference db1=FirebaseFirestore.getInstance().collection(auth.getUid()+"get");
    TextView textView;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_finshe);
        bar=findViewById(R.id.proempty);
        bar.setVisibility(View.VISIBLE);
        textView=findViewById(R.id.textno);
        rec2=findViewById(R.id.rec2);
        adapter = new Adapterrec2(idates2,this);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rec2.setLayoutManager(linearLayoutManager);
        rec2.setAdapter(adapter);
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
                idates2.clear();

                for (QueryDocumentSnapshot querySnapshot : value){
                    data_model_finshe dater1 = querySnapshot.toObject(data_model_finshe.class);
                    textView.setText("");
                    bar.setVisibility(View.GONE);
                    idates2.add(dater1);
                }
                adapter.notifyDataSetChanged();



            }
        });

    }
}