package com.jyc.addressbook.adapters;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jyc.addressbook.R;
import com.jyc.addressbook.activities.ContactorInfo;
import com.jyc.addressbook.activities.MakeMessage;
import com.jyc.addressbook.po.MessageRecord;
import com.thinkcool.circletextimageview.CircleTextImageView;

import java.util.List;

public class MessageRecordAdapter extends RecyclerView.Adapter<MessageRecordAdapter.ViewHolder> {
    private List<MessageRecord> MessageRecords;

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        CircleTextImageView firstName;
        TextView name;
        TextView phone;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            firstName = view.findViewById(R.id.mUserPhoto2);
            name = view.findViewById(R.id.mName2);
            phone = view.findViewById(R.id.mNumber1);
            time = view.findViewById(R.id.mTime1);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                MessageRecord messageRecord = MessageRecords.get(position);
                // 点击了短信记录，显示短信信息以及发送界面
                Intent intent = new Intent(view.getContext(), MakeMessage.class);
                intent.putExtra("phone",messageRecord.getPhone());
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageRecord MessageRecord = MessageRecords.get(holder.getAdapterPosition());
        String name = MessageRecord.getName();
        String firstName = name.substring(0, 1);
        holder.time.setText(MessageRecord.getTime());
        holder.firstName.setText(firstName);
        holder.name.setText(name);
        holder.phone.setText(MessageRecord.getPhone());
    }

    @Override
    public int getItemCount() {
        return MessageRecords.size();
    }

    public MessageRecordAdapter(List<MessageRecord> list) {
        this.MessageRecords = list;
    }
}
