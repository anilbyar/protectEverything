package com.example.protecteverything.Adapter;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.protecteverything.Models.SiteWithCode;
import com.example.protecteverything.R;
import com.example.protecteverything.Utility.Helper;

import java.util.ArrayList;

public class CodeListAdapter extends RecyclerView.Adapter<CodeListAdapter.CodeItemHolder> {

    Context context;
    ArrayList<SiteWithCode> siteWithCode;

    public CodeListAdapter(Context context, ArrayList<SiteWithCode> siteWithCode) {
        this.context = context;
        this.siteWithCode = siteWithCode;
    }

    @NonNull
    @Override
    public CodeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.secretcode, parent, false);
        return new CodeItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeItemHolder holder, int position) {
        holder.siteName.setText(siteWithCode.get(position).getSiteName());
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
                Helper.copyToClipboard(context, siteWithCode.get(holder.getAdapterPosition()).getSiteCode());
            }
        });
        holder.copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
                Helper.copyToClipboard(context, siteWithCode.get(holder.getAdapterPosition()).getSiteCode());
            }
        });

        // show short or long password
        if (siteWithCode.get(position).getLength()==12) holder.lengthofpass.setText("short");
        else holder.lengthofpass.setText("long");
    }

    @Override
    public int getItemCount() {
        return siteWithCode.size();
    }

    public static class CodeItemHolder extends RecyclerView.ViewHolder {
        TextView siteName;
        TextView lengthofpass;
        CardView copy;
        ImageButton copyBtn;
        public CodeItemHolder(@NonNull View itemView) {
            super(itemView);
            siteName = itemView.findViewById(R.id.siteName);
            copy = itemView.findViewById(R.id.copy);
            copyBtn = itemView.findViewById(R.id.copyBtn);
            lengthofpass = itemView.findViewById(R.id.lenghtOfPassword);
        }
    }
}
