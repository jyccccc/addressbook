package com.jyc.addressbook.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jyc.addressbook.R;
import com.jyc.addressbook.activities.ContactorInfo;
import com.jyc.addressbook.po.Contactor;
import com.thinkcool.circletextimageview.CircleTextImageView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ContactorAdapter extends RecyclerView.Adapter<ContactorAdapter.ViewHolder> {
    private List<Contactor> contactors;

    class ViewHolder extends RecyclerView.ViewHolder {
        View contactorView;
        TextView name;
        CircleTextImageView imageView;

        public ViewHolder(View view) {
            super(view);
            contactorView = view;
            name = view.findViewById(R.id.mName);
            imageView = view.findViewById(R.id.mUserPhoto);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_contacts_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.contactorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Contactor contactor = contactors.get(position);
                // 点击了联系人之后，显示联系人信息
                Intent intent = new Intent(view.getContext(), ContactorInfo.class);
                intent.putExtra("contactor",contactor.getPhone());
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contactor contactor = contactors.get(position);
        String name = contactor.getName();
        Log.d(TAG, "onBindViewHolder: "+name);
        String firstName = name.substring(0,1);
        holder.name.setText(name);
        holder.imageView.setText(firstName);
    }

    @Override
    public int getItemCount() {
        return contactors.size();
    }

    public ContactorAdapter(List<Contactor> contactors) {
        this.contactors = contactors;
    }
}
