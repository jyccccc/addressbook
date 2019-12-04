package com.jyc.addressbook.adapters;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jyc.addressbook.R;
import com.jyc.addressbook.activities.ContactorInfo;
import com.jyc.addressbook.po.CallRecord;
import com.thinkcool.circletextimageview.CircleTextImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CallRecordAdapter extends RecyclerView.Adapter<CallRecordAdapter.ViewHolder> {
    private List<CallRecord> callRecords;

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        CircleTextImageView firstName;
        TextView name;
        TextView phone;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            firstName = view.findViewById(R.id.mUserPhoto1);
            name = view.findViewById(R.id.mName1);
            phone = view.findViewById(R.id.mNumber);
            time = view.findViewById(R.id.mTime);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_call,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                CallRecord callRecord = callRecords.get(position);
                // 点击了通话记录，直接拨出电话
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + callRecord.getPhone()));

                if (ContextCompat.checkSelfPermission(view.getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(view.getContext(),"没有获取通话权限",Toast.LENGTH_SHORT).show();
                }
                view.getContext().startActivity(callIntent);

                // 添加到数据库中
                CallRecord callRecord1 = callRecord;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                callRecord1.setTime(simpleDateFormat.format(date));
                callRecord1.save();
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CallRecord callRecord = callRecords.get(holder.getAdapterPosition());
        String name = callRecord.getName();
        String firstName = name.substring(0,1);
        holder.time.setText(callRecord.getTime());
        holder.firstName.setText(firstName);
        holder.name.setText(name);
        holder.phone.setText(callRecord.getPhone());
    }

    @Override
    public int getItemCount() {
        return callRecords.size();
    }

    public CallRecordAdapter(List<CallRecord> list) {
        this.callRecords = list;
    }
}
