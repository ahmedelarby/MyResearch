package com.myresearch.myresearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class NewWork extends AppCompatActivity implements TextWatcher {
EditText Page_count;
double num = 40;
    String PathHolder;
TextView taklofa;
    double toital;
    DatePickerDialog.OnDateSetListener setListener;

    EditText number_regst;
    EditText password;
EditText subject;
ProgressBar pro;
EditText file;
double allpic;
EditText words_count;
EditText type_recwest;
EditText anythink;
Button send;
String datatype;
    StorageReference mStorageRef;
    String gettime;
    Uri url;
FirebaseFirestore db=FirebaseFirestore.getInstance();
FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_work);
        pro=findViewById(R.id.pro);
        Page_count = (EditText) findViewById(R.id.page_count);
        Page_count.addTextChangedListener(this);
        taklofa=findViewById(R.id.taklfa);
        number_regst=findViewById(R.id.number_regst);
        password=findViewById(R.id.password);
        subject=findViewById(R.id.subject);
        file=findViewById(R.id.file);

        words_count=findViewById(R.id.words_count);
        type_recwest=findViewById(R.id.type_recwest);
        anythink=findViewById(R.id.anythink);
        send=findViewById(R.id.send);
        long timeInMillis = System.currentTimeMillis();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timeInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "hh:mm:ss a", Locale.ENGLISH);
       gettime = dateFormat.format(cal1.getTime());
      //  Toast.makeText(this, ""+gettime, Toast.LENGTH_SHORT).show();
       file.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ActivityCompat.requestPermissions(NewWork.this,
                       new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                       1);

//
           }
       });

        Calendar cal= Calendar.getInstance();
        final int year=cal.get(Calendar.YEAR);
        final int month=cal.get(Calendar.MONTH);
        final int day=cal.get(Calendar.DAY_OF_MONTH);





       type_recwest.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               DatePickerDialog dialog= new DatePickerDialog(NewWork.this
                       ,R.style.Theme_AppCompat_Light_Dialog_MinWidth,setListener,year,month,day);
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
               dialog.show();
           }
       });
        setListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                i1 =i1+1;
                String date=i+"/"+i1+"/"+i2;
                datatype=date;
                type_recwest.setText(date);


            }
        };





















        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=number_regst.getText().toString();
                String pass=password.getText().toString();
                String sabgect=subject.getText().toString();
                String fil=file.getText().toString();
                String word=words_count.getText().toString();
                String type=type_recwest.getText().toString();
                String ANY=anythink.getText().toString();
                String page=Page_count.getText().toString().trim();
                if (number.isEmpty()){ number_regst.setError("is Empty");return; }
                if (pass.isEmpty()){ password.setError("is empty");return; }
                if (sabgect.isEmpty()){ subject.setError("is Empty");return; }
                if (fil.isEmpty()){file.setError("no choose any file");return;}
                if (page.isEmpty()){Page_count.setError("is Empty");return;}
                if (word.isEmpty()){words_count.setError("is empty");return;}
                if (type.isEmpty()){type_recwest.setError("is empty");return;}
                if (ANY.isEmpty()){anythink.setError("is empty");return;}
                if (PathHolder.isEmpty()){file.setError("is empty");return;}
                if (url.equals(null)){file.setError("is empty");return;}
                else {
                    pro.setVisibility(View.VISIBLE);
                    send.setVisibility(View.GONE);
                    mStorageRef = FirebaseStorage.getInstance().getReference().child(auth.getCurrentUser().getUid());
                    StorageReference file = mStorageRef.child("file"+ UUID.randomUUID().toString());
                    file.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            file.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                Map<String,Object> data=new HashMap<>();

                                    data.put("registration_number",number);
                                    data.put("password",pass);
                                    data.put("subject",sabgect);
                                    data.put("file",fil);
                                    data.put("Page_count",Page_count.getText().toString().trim());
                                    data.put("words_count",word);
                                    data.put("received_date",type);
                                    data.put("notes",ANY);
                                    data.put("request_date",gettime);
                                    data.put("key",auth.getUid());
                                    data.put("price",""+allpic);
                                    data.put("case_request","no");



                                   data.put("url",String.valueOf(uri));

                                    db.collection(auth.getUid()+"set").document(gettime).set(data);


                                    Map<String,Object> data1=new HashMap<>();

                                    data1.put("registration_number",number);
                                    data1.put("password",pass);
                                    data1.put("subject",sabgect);
                                    data1.put("file",fil);
                                    data1.put("Page_count",Page_count.getText().toString().trim());
                                    data1.put("words_count",word);
                                    data1.put("received_date",type);
                                    data1.put("notes",ANY);
                                    data1.put("request_date",gettime);
                                    data1.put("key",auth.getUid());
                                    data1.put("price",""+allpic);
                                    data1.put("case_request","no");

                                    data1.put("url",String.valueOf(uri));

                                    db.collection("allworks").document(gettime).set(data1);








                                    Toast.makeText(NewWork.this, "is complete", Toast.LENGTH_SHORT).show();
                                    Intent back =new Intent(NewWork.this,Ditels_send.class);
                                    back.putExtra("number",gettime);
                                    back.putExtra("type",type);
                                    startActivity(back);
                                    finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull  Exception e) {
                                    Toast.makeText(NewWork.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    pro.setVisibility(View.GONE);
                                    send.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });
























                }
            }
        });




    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().isEmpty()){
            taklofa.setText("التكلفه");
            return;
        }
        double con=Double.parseDouble(charSequence.toString());

        toital=Math.abs(con);
       allpic= num*toital;
        taklofa.setText(""+allpic);


    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent, 7);




                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(NewWork.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }






    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                     file.setText(PathHolder = data.getData().getPath());
                     url = data.getData();

                    Toast.makeText(this, PathHolder
                            , Toast.LENGTH_LONG).show();
                }
                break;
        }


    }
}