package com.example.protecteverything;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.protecteverything.Adapter.CodeListAdapter;
import com.example.protecteverything.Models.SiteWithCode;
import com.example.protecteverything.Utility.Helper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;


public class SiteCodesFragment extends Fragment {
    View view;
    RecyclerView siteCodeRecycleView;
    Context context;
    DatabaseReference reference = SigninActivity.reference;
    public static ArrayList<SiteWithCode> codeArrayList;
    public static ArrayList<SiteWithCode> searchArrayList;
    CodeListAdapter adapter;
    EditText searchSite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = inflater.getContext();
        return inflater.inflate(R.layout.fragment_codes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        siteCodeRecycleView = view.findViewById(R.id.siteCodeRecycleview);
        codeArrayList = new ArrayList<>();

        searchSite = view.findViewById(R.id.siteName);
        searchSite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (searchSite.getText().toString().equals("")){
                    updateRecyclerView();
                }
                else {
                    searchArrayList = Helper.searchSite(searchSite.getText().toString());
                    CodeListAdapter searchAdapter = new CodeListAdapter(context, searchArrayList);
                    siteCodeRecycleView.setLayoutManager(new LinearLayoutManager(context));
                    siteCodeRecycleView.hasFixedSize();
                    siteCodeRecycleView.setAdapter(searchAdapter);
                }
            }
        });


        updateRecyclerView();
        getData();
    }

    public void getData(){

        reference.child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren()){
                    SiteWithCode site = data.getValue(SiteWithCode.class);
                    if (!codeArrayList.isEmpty() && codeArrayList.contains(site)) continue;
                    codeArrayList.add(site);
                    codeArrayList.sort(new SiteWithCode.SortByName());
                    adapter.notifyItemInserted(codeArrayList.size()-1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerView() {
        adapter = new CodeListAdapter(context, codeArrayList);
        siteCodeRecycleView.setLayoutManager(new LinearLayoutManager(context));
        siteCodeRecycleView.hasFixedSize();
        siteCodeRecycleView.setAdapter(adapter);
    }

    private void updateRecyclerViewAfterSearch() {
        adapter = new CodeListAdapter(context, searchArrayList);
        siteCodeRecycleView.setLayoutManager(new LinearLayoutManager(context));
        siteCodeRecycleView.hasFixedSize();
        siteCodeRecycleView.setAdapter(adapter);
    }
}
