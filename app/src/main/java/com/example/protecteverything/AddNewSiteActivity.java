package com.example.protecteverything;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protecteverything.Models.SiteWithCode;
import com.example.protecteverything.Utility.Helper;
import com.example.protecteverything.databinding.ActivityAddNewSiteBinding;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewSiteActivity extends AppCompatActivity {
    ActivityAddNewSiteBinding binding;
    FirebaseDatabase database;
    DatabaseReference reference;

    Integer length;

    TextView shortPassword, longPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewSiteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Databases
        database = FirebaseDatabase.getInstance();
        reference = SigninActivity.reference;

        length = -1;

        shortPassword = binding.shortPassword;
        longPassword = binding.longPassword;

        binding.genAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateAndAdd();
            }
        });

        shortPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (length!=12){
                    length = 12;
                    shortPassword.setAlpha(0.6f);
                    longPassword.setAlpha(1f);

                }
            }
        });
        longPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (length!=16){
                    length = 16;
                    shortPassword.setAlpha(1f);
                    longPassword.setAlpha(0.6f);
                }
            }
        });
    }

    private void generateAndAdd() {
        String siteName = binding.siteName.getText().toString();
        binding.siteName.setText("");
        if (siteName.equals("")){
            Toast.makeText(this, "Please Enter a something", Toast.LENGTH_SHORT).show();
            return;
        }

        if (length==-1){
            Toast.makeText(this, "Please Select Length of Password.", Toast.LENGTH_SHORT).show();
            return;
        }

        SiteWithCode site = Helper.generateCode(siteName, length);
        for (SiteWithCode siteWithCode:SiteCodesFragment.codeArrayList){
            if (siteName.equals(siteWithCode.getSiteName())){
                Toast.makeText(this, "Code has already been created", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        String key;
        try {
            key = reference.child("data").push().getKey();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        if (key==null){
            Toast.makeText(this, "Some error occurred!", Toast.LENGTH_LONG).show();
            return;
        }

        reference.child("data").child(key).setValue(site)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(AddNewSiteActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNewSiteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}