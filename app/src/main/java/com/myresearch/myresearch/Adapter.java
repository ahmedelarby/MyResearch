package com.myresearch.myresearch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
ArrayList<DataModel>models;
Context context;
FirebaseAuth auth=FirebaseAuth.getInstance();
FirebaseFirestore db=FirebaseFirestore.getInstance();

    public Adapter(ArrayList<DataModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = (LayoutInflater.from(parent.getContext())).inflate(R.layout.itemrec,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.ViewHolder holder, int position) {
        DataModel item = models.get(position);
        holder.pro.setVisibility(View.VISIBLE);
        holder.requestdata.setText("وقت ارسال الطلب"+" : "+item.getRequest_date());
        holder.password.setText("الرقم السري"+" : "+item.getPassword());
        holder.price.setText("السعر"+" : "+item.getPrice());
        holder.pay.setText("حاله الدفع"+" : "+item.getCase_request());
        holder.subject.setText("الماده"+" : "+item.getSubject());
        holder.receivedate.setText("تاريخ الاستلام"+" : "+item.getReceived_date());
        holder.pro.setVisibility(View.GONE);
        String getcase=item.getCase_request();
        if (getcase.equals("ok")) {
            holder.Pay.setVisibility(View.GONE);

        }
        holder.Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paye= new Intent(context,Ditels_send.class);
                paye.putExtra("number",item.getRequest_date());
                paye.putExtra("type",item.getReceived_date());
                context.startActivity(paye);
            }
        });

holder.more.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String n=item.getRequest_date();
        db.collection(auth.getUid()+"set").document(n).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context, "isScccessful" ,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
});
    }



    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView requestdata;
        TextView receivedate;
        TextView subject;
        TextView password;
        TextView price;
        TextView pay;
        Button Pay;
        Button more;
        ProgressBar pro;
        View perantview;

        ViewHolder(View view1){
            super(view1);
            perantview = view1;
            requestdata=view1.findViewById(R.id.one);
            password=view1.findViewById(R.id.tow);
            price=view1.findViewById(R.id.three);
            pay=view1.findViewById(R.id.fore);
            subject=view1.findViewById(R.id.five);
            receivedate=view1.findViewById(R.id.six);
            Pay=view1.findViewById(R.id.pay_last);
            more=view1.findViewById(R.id.more);
            pro=view1.findViewById(R.id.proshow);




        }



    }


}
