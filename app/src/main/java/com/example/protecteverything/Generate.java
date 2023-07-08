package com.example.protecteverything;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.protecteverything.Utility.Helper;

public class Generate extends Fragment {

    Context context;
    EditText codeEditText;
    Button genCopyButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = inflater.getContext();
        return inflater.inflate(R.layout.fragment_generate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        codeEditText = view.findViewById(R.id.siteCode);
        genCopyButton = view.findViewById(R.id.genCopy);
        genCopyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyPassword();
            }
        });

    }

    private void copyPassword() {
        if (codeEditText.getText().toString().equals("")){
            Toast.makeText(context, "Type something", Toast.LENGTH_SHORT).show();
            return;
        }

        String siteCode = codeEditText.getText().toString();
        String password = Helper.createPassword(siteCode);
        if (password==null){
            Toast.makeText(context, "Please enter a valid code.", Toast.LENGTH_SHORT).show();
            return;
        }
        codeEditText.setText("");
        Helper.copyToClipboard(context, password);
    }


}