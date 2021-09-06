package com.myresearch.myresearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Ditels_send extends AppCompatActivity {
TextView mead;
Button pay;
TextView textView;
    String num;
    StorageReference mStorageRef;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ditels_send);
        num=getIntent().getStringExtra("number");
        String type=getIntent().getStringExtra("type");









        mead=findViewById(R.id.mead);
        pay=findViewById(R.id.pay);
        mead.setText(""+type);

        mStorageRef = FirebaseStorage.getInstance().getReference().child(auth.getCurrentUser().getUid());
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(Ditels_send.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);











            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PopupMenu popupMenu =new PopupMenu(Ditels_send.this,pay);
                    popupMenu.getMenuInflater().inflate(R.menu.item_1,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            switch (id){
                                case R.id.vodafone:
                                    Intent open_Stoduo1 = new Intent(Intent.ACTION_GET_CONTENT);
                                    open_Stoduo1.setType("image/*");
                                    startActivityForResult(Intent.createChooser(open_Stoduo1,"اختر مكان ايصال الدفع "), 1);

                                    break;
                                case R.id.paypal:

                                    Intent open_Stoduo2 = new Intent(Intent.ACTION_GET_CONTENT);
                                    open_Stoduo2.setType("image/*");
                                    startActivityForResult(Intent.createChooser(open_Stoduo2,"اختر مكان ايصال الدفع "), 2);

                                    break;
                                case R.id.bank:
                                    Intent open_Stoduo3= new Intent(Intent.ACTION_GET_CONTENT);
                                    open_Stoduo3.setType("image/*");
                                    startActivityForResult(Intent.createChooser(open_Stoduo3,"اختر مكان ايصال الدفع "), 3);
                                    break;
                                case R.id.westrn:
                                    Intent open_Stoduo4 = new Intent(Intent.ACTION_GET_CONTENT);
                                    open_Stoduo4.setType("image/*");
                                    startActivityForResult(Intent.createChooser(open_Stoduo4,"اختر مكان ايصال الدفع "), 4);
                                    break;
                            }


                            return true;
                        }
                    });
                    popupMenu.show();





                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(Ditels_send.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
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
            case 1:
                if (resultCode == RESULT_OK) {
                String d=data.getData().getPath();
                   Uri url = data.getData();
                   String vodafone="vodafone";
                    StorageReference file = mStorageRef.child("image_rseed"+ UUID.randomUUID().toString());
                    file.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Map<String,Object> data=new HashMap<>();

                                    data.put("name pay",vodafone);
                                    data.put("case_request","ok");

                                    data.put("photo screen pay",String.valueOf(uri));
                                    db.collection(auth.getUid()+"set").document(num).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull  Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Map<String,Object> data2=new HashMap<>();

                                                data2.put("name pay",vodafone);
                                                data2.put("case_request","ok");
                                                data2.put("photo screen pay",String.valueOf(uri));
                                                db.collection("allworks").document(num).set(data2, SetOptions.merge());
                                                Toast.makeText(Ditels_send.this, "isSuccessful", Toast.LENGTH_SHORT).show();
                                                Intent goback=new Intent(Ditels_send.this,Home.class);
                                                startActivity(goback);
                                                finish();
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull  Exception e) {
                                            Toast.makeText(Ditels_send.this, "erorr"+e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });





                                }
                            });
                        }
                    });


                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    String d2 = data.getData().getPath();
                    Uri url2 = data.getData();
                    String paypal = "paypal";

                    StorageReference file = mStorageRef.child("image_rseed"+ UUID.randomUUID().toString());
                    file.putFile(url2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri2) {
                                    Map<String,Object> data=new HashMap<>();

                                    data.put("name pay",paypal);
                                    data.put("case_request","ok");

                                    data.put("photo screen pay",String.valueOf(uri2));
                                    db.collection(auth.getUid()+"set").document(num).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull  Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Map<String,Object> data2=new HashMap<>();

                                                data2.put("name pay",paypal);
                                                data2.put("case_request","ok");
                                                data2.put("photo screen pay",String.valueOf(uri2));
                                                db.collection("allworks").document(num).set(data2, SetOptions.merge());
                                                Toast.makeText(Ditels_send.this, "isSuccessful", Toast.LENGTH_SHORT).show();
                                                Intent goback=new Intent(Ditels_send.this,Home.class);
                                                startActivity(goback);
                                                finish();
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull  Exception e) {
                                            Toast.makeText(Ditels_send.this, "erorr"+e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });





                                }
                            });
                        }
                    });


                }
                    break;
                    case 3:
                        if (resultCode == RESULT_OK) {
                            String d3 = data.getData().getPath();
                            Uri url3 = data.getData();
                            String bank = "bank";

                            StorageReference file = mStorageRef.child("image_rseed"+ UUID.randomUUID().toString());
                            file.putFile(url3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri3) {
                                            Map<String,Object> data3=new HashMap<>();

                                            data3.put("name pay",bank);
                                            data3.put("case_request","ok");

                                            data3.put("photo screen pay",String.valueOf(uri3));
                                            db.collection(auth.getUid()+"set").document(num).set(data3, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull  Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Map<String,Object> data3=new HashMap<>();

                                                        data3.put("name pay",bank);
                                                        data3.put("case_request","ok");
                                                        data3.put("photo screen pay",String.valueOf(uri3));
                                                        db.collection("allworks").document(num).set(data3, SetOptions.merge());
                                                        Toast.makeText(Ditels_send.this, "isSuccessful", Toast.LENGTH_SHORT).show();
                                                        Intent goback=new Intent(Ditels_send.this,Home.class);
                                                        startActivity(goback);
                                                        finish();
                                                    }

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull  Exception e) {
                                                    Toast.makeText(Ditels_send.this, "erorr"+e.getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            });





                                        }
                                    });
                                }
                            });








                        }
                        break;
            case 4:
                if (resultCode == RESULT_OK) {
                    String d4 = data.getData().getPath();
                    Uri url4 = data.getData();
                    String westrn = "Western Union";

                    StorageReference file = mStorageRef.child("image_rseed"+ UUID.randomUUID().toString());
                    file.putFile(url4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri2) {
                                    Map<String,Object> data4=new HashMap<>();

                                    data4.put("name pay",westrn);
                                    data4.put("case_request","ok");

                                    data4.put("photo screen pay",String.valueOf(uri2));
                                    db.collection(auth.getUid()+"set").document(num).set(data4, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull  Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Map<String,Object> data4=new HashMap<>();

                                                data4.put("name pay",westrn);
                                                data4.put("case_request","ok");
                                                data4.put("photo screen pay",String.valueOf(uri2));
                                                db.collection("allworks").document(num).set(data4, SetOptions.merge());
                                                Toast.makeText(Ditels_send.this, "isSuccessful", Toast.LENGTH_SHORT).show();
                                                Intent goback=new Intent(Ditels_send.this,Home.class);
                                                startActivity(goback);
                                                finish();
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull  Exception e) {
                                            Toast.makeText(Ditels_send.this, "erorr"+e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });





                                }
                            });
                        }
                    });









                }



                }



                        }



                }
