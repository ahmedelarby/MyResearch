package com.myresearch.myresearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;

    private TextView Signup;
    private TextView forget_password;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore =FirebaseFirestore.getInstance();
    private String Email;
    private String Password;
    private String time;
    private AlertDialog dialog_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        email=findViewById(R.id.edittextemaillogin);
        password=findViewById(R.id.editpasswordlogin);
        login=findViewById(R.id.btnSign_in);
        forget_password=findViewById(R.id.forget_password);
        Signup=findViewById(R.id.Signup);
        // this is get time login
        long timeInMillis = System.currentTimeMillis();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timeInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd hh:mm a", Locale.ENGLISH);
        time = dateFormat.format(cal1.getTime());
        //Action Buttons
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(Login.this);
                View view66 = getLayoutInflater().inflate(R.layout.item_diloge_forget,null);
                builder.setTitle("Reest password").setIcon(R.drawable.ic_password);
                EditText emailUpdet = (EditText) view66.findViewById(R.id.edit_pass);
                Button  up_em1 =(Button) view66.findViewById(R.id.reest_pass);
                ProgressBar pro_reest=(ProgressBar)view66.findViewById(R.id.pro_reest);
                up_em1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String up1= emailUpdet.getText().toString().trim();
                        if (up1.isEmpty()){

                            emailUpdet.setError("please enter your email");
                            return;
                        } else
                            pro_reest.setVisibility(View.VISIBLE);
                            mAuth.sendPasswordResetEmail(up1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull  Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Login.this, "is Successful", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(Login.this, "go to email cheek massege", Toast.LENGTH_SHORT).show();

                                        dialog_login.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull  Exception e) {
                                    pro_reest.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            }
                });




                builder.setView(view66);
                dialog_login = builder.create();
                dialog_login.show();

            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SignupActivity= new Intent(Login.this,Page_Signup.class);
                startActivity(SignupActivity);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email=email.getText().toString().trim();
                Password=password.getText().toString().trim();
                if (Email.isEmpty()){
                    email.setError("please Enter your Email");
                    return;
                }
                if (Password.isEmpty()){
                    password.setError("please Enter your password");
                    return;
                }
                else{

                    AlertDialog.Builder builder1 =new AlertDialog.Builder(Login.this);
                    View view1 = getLayoutInflater().inflate(R.layout.item_progress_login,null);
                    ProgressBar pro = view1.findViewById(R.id.progress_login);
                    pro.setVisibility(View.VISIBLE);
                    builder1.setView(view1);
                    dialog_login = builder1.create();
                    dialog_login.show();
                    mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                isEmailVerified();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog_login.dismiss();
                            Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });









    }
    private void isEmailVerified(){
        FirebaseUser used= mAuth .getCurrentUser();
        if (used.isEmailVerified()){
            data_users_login data=new data_users_login();
            data.setEmail(Email);
            data.setPassword(Password);
            data.setTime(time);
            data.setKey(mAuth.getUid());
            firestore.collection("Login").document(mAuth.getUid()).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull  Task<Void> task) {
                    if (task.isSuccessful()){
                        dialog_login.dismiss();
                        Intent ActivityHome = new Intent(Login.this,Home.class);
                        startActivity(ActivityHome);
                        finish();
                    }
                }
            });

        }else {
            Toast.makeText(Login.this, "Go to your Email and enter link", Toast.LENGTH_LONG).show();
            dialog_login.dismiss();


        }
    }


}