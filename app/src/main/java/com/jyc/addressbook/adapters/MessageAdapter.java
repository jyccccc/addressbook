package com.jyc.addressbook.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jyc.addressbook.po.MessageRecord;
import com.jyc.addressbook.R;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    // 与某个联系人的短信记录
    private List<MessageRecord> list;

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView header;
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            header = view.findViewById(R.id.messageHeader);
            content = view.findViewById(R.id.messageContent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sms,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageRecord messageRecord = list.get(holder.getAdapterPosition());
        holder.header.setText(messageRecord.getTime());
        holder.content.setText(messageRecord.getContext());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public MessageAdapter(List<MessageRecord> list) {
        this.list = list;
    }
}
