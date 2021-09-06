package com.myresearch.myresearch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment {
private Button new_work;
private Button finshe_work;
private Button cuntenuo_work;
private Button SignOut;
private FirebaseAuth auth= FirebaseAuth.getInstance();

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        new_work =view.findViewById(R.id.new_work);
        cuntenuo_work=view.findViewById(R.id.fllowo_work);
        finshe_work=view.findViewById(R.id.finshe_work);
        SignOut=view.findViewById(R.id.SignOut);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent back=new Intent(getContext(),Login.class);
                startActivity(back);
                getActivity().finish();
            }
        });

        new_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent new_work=new  Intent(getContext(),NewWork.class);
                startActivity(new_work);








            }
        });
        cuntenuo_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workcountenuo=new Intent(getContext(),Workcun.class);
                startActivity(workcountenuo);

            }
        });
        finshe_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent f=new Intent(getContext(),WorkFinshe.class);
                startActivity(f);

            }
        });

        return view;
    }



}