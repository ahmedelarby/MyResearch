package com.myresearch.myresearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Page_Signup extends AppCompatActivity {
    private EditText edittextemailSignup;
    private EditText editpasswordSignup;
    private EditText editnameSignup;
    private EditText edit_pone_Signup;
    private EditText edit_unverstiy_Signup;
    private EditText edit_elfarqa_Signup;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String TheUniversity;
    private String studyGroup;
    private String time;
    Button SignUp;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
ProgressBar progressBar_SignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_signup);
        edittextemailSignup = findViewById(R.id.edittextemailSignup);
        editpasswordSignup = findViewById(R.id.editpasswordSignup);
        editnameSignup = findViewById(R.id.editnameSignup);
        edit_pone_Signup = findViewById(R.id.edit_pone_Signup);
        edit_unverstiy_Signup = findViewById(R.id.edit_unverstiy_Signup);
        edit_elfarqa_Signup = findViewById(R.id.edit_elfarqa_Signup);
        SignUp = findViewById(R.id.SignUp);
        progressBar_SignUp=findViewById(R.id.progressBar_SignUp);

        // this is get time SignUp
        long timeInMillis = System.currentTimeMillis();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timeInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd hh:mm a", Locale.ENGLISH);
        time = dateFormat.format(cal1.getTime());


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edittextemailSignup.getText().toString().trim();
                password = editpasswordSignup.getText().toString().trim();
                name = editnameSignup.getText().toString().trim();
                phone = edit_pone_Signup.getText().toString().trim();
                TheUniversity = edit_unverstiy_Signup.getText().toString().trim();
                studyGroup = edit_elfarqa_Signup.getText().toString().trim();
                if (email.isEmpty()) {
                    edittextemailSignup.setError("please enter your email");
                    return;
                }
                if (password.isEmpty()) {
                    editpasswordSignup.setError("please create password");
                    return;
                }
                if (name.isEmpty()) {
                    editnameSignup.setError("please enter your name");
                    return;
                }
                if (phone.isEmpty()) {
                    edit_pone_Signup.setError("please enter your phone");
                    return;
                }
                if (TheUniversity.isEmpty()) {
                    edit_unverstiy_Signup.setError("please enter your the University");
                    return;
                }
                if (studyGroup.isEmpty()) {
                    edit_elfarqa_Signup.setError("please enter your study group");
                    return;
                } else {
                    SignUp.setVisibility(View.GONE);
                    progressBar_SignUp.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                cheek_vrefcation();

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Page_Signup.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar_SignUp.setVisibility(View.GONE);
                            SignUp.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    private void cheek_vrefcation() {
        FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull  Task<Void> task) {
                if (task.isSuccessful()){
                    SignUp.setVisibility(View.GONE);
                    progressBar_SignUp.setVisibility(View.VISIBLE);
                    data_users_login data = new data_users_login();
                    data.setName(name);
                    data.setPassword(password);
                    data.setEmail(email);
                    data.setKey(mAuth.getUid());
                    data.setPhone(phone);
                    data.setTheUniversity(TheUniversity);
                    data.setStudyGroup(studyGroup);
                    data.setTime(time);
                    firestore.collection("all users").document(mAuth.getUid()).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull  Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Page_Signup.this, "isSuccessful", Toast.LENGTH_SHORT).show();
                                Toast.makeText(Page_Signup.this, "go to your email cheeke massege from My Research", Toast.LENGTH_SHORT).show();
                                Intent back= new Intent(Page_Signup.this,Login.class);
                                startActivity(back);
                                finish();
                            }
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(Page_Signup.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar_SignUp.setVisibility(View.GONE);
                SignUp.setVisibility(View.VISIBLE);
            }
        });






















    }}
















