package com.myresearch.myresearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapterrec2 extends RecyclerView.Adapter<Adapterrec2.ViewHolder> {
ArrayList<data_model_finshe>finshes;
Context context;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    //
    FirebaseAuth auth=FirebaseAuth.getInstance();

    public Adapterrec2(ArrayList<data_model_finshe> finshes, Context context) {
        this.finshes = finshes;
        this.context = context;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.itemrecfinshe,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapterrec2.ViewHolder holder, int position) {
        data_model_finshe item = finshes.get(position);
    holder.suject.setText("الماده"+" : "+item.getSubject());
    holder.url1.setText("الملف"+" : "+item.getUrl());
    holder.pro.setVisibility(View.GONE);


    holder.dounlowd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            holder.dounlowd.setVisibility(View.GONE);
            holder.pro.setVisibility(View.VISIBLE);

            Intent i =new Intent();
            i.setPackage("com.android.chrome");
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse(item.getUrl()));
            context.startActivity(i);

        }
    });
        holder.dounlowd.setVisibility(View.VISIBLE);
    holder.more.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            holder.more.setVisibility(View.GONE);
            holder.uplod.setVisibility(View.VISIBLE);
            holder.ploblem.setVisibility(View.VISIBLE);
            holder.sendproblem.setVisibility(View.VISIBLE);
        }
    });
    holder.uplod.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            holder.more.setVisibility(View.VISIBLE);
            holder.uplod.setVisibility(View.GONE);
            holder.ploblem.setVisibility(View.GONE);
            holder.sendproblem.setVisibility(View.GONE);
            holder.sendscssfily.setVisibility(View.GONE);


        }
    });
    holder.sendproblem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           String get= holder.ploblem.getText().toString().trim();
            if (get.isEmpty()){holder.ploblem.setError("is Empty");return;}
            Map<String,Object> problem=new HashMap<>();
            problem.put("المشكله",get);
            problem.put("المستخدم",auth.getUid());
            problem.put("الرقم الخاص بالطلب",item.getNumber());
            db.collection("الشكاوي والمشكلات").document().set(problem).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull  Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(context, "isSuccessful", Toast.LENGTH_SHORT).show();
                        holder.sendscssfily.setVisibility(View.VISIBLE);
                        holder.ploblem.setVisibility(View.GONE);
                        holder.uplod.setVisibility(View.GONE);
                        holder.more.setVisibility(View.VISIBLE);
                        holder.sendscssfily.setVisibility(View.GONE);
                        holder.sendproblem.setVisibility(View.GONE);


                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull  Exception e) {
                    Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    });
    }

    @Override
    public int getItemCount() {
        return finshes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView suject;
        TextView url1;
        TextView sendscssfily;
        Button sendproblem;
        Button dounlowd;
        Button uplod;
        EditText ploblem;
        TextView finsh;

        Button more;
        ProgressBar pro;
        View perantview;

        ViewHolder(View view1){
            super(view1);
            perantview = view1;
            finsh=view1.findViewById(R.id.tsh);
            suject=view1.findViewById(R.id.subject123);
            url1=view1.findViewById(R.id.url123);
            dounlowd=view1.findViewById(R.id.dawun);
            uplod=view1.findViewById(R.id.uplodbtn);
            ploblem=view1.findViewById(R.id.getproblem);
            sendscssfily=view1.findViewById(R.id.sendscssfiley);
            sendproblem=view1.findViewById(R.id.sendproblem);
            more=view1.findViewById(R.id.more123);
            pro=view1.findViewById(R.id.pro123);




        }



    }


























}
