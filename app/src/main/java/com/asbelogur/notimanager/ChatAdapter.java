package com.asbelogur.notimanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context context;
    private ArrayList notification_id, package_name, user, textOfNotification, post_time;

    public ChatAdapter(Context context, ArrayList notification_id, ArrayList package_name, ArrayList user, ArrayList textOfNotification, ArrayList post_time) {
        this.context = context;
        this.notification_id = notification_id;
        this.package_name = package_name;
        this.textOfNotification = textOfNotification;
        this.user = user;
        this.post_time = post_time;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recieved_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, final int position) {
        holder.user_txt.setText(String.valueOf(user.get(position)));
        holder.content_txt.setText(String.valueOf(textOfNotification.get(position)));

        long realTime = Long.parseLong(String.valueOf(post_time.get(position)));

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("h:mm a");
        String time =  simpleDateFormat1.format(realTime);
        holder.time_txt.setText(time);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd MMMM");
        String date =  simpleDateFormat2.format(realTime);
        holder.date_txt.setText(date);

        //holder.logo_txt.setImageDrawable(CNLSHelper.getAppIconFromPackage(context, String.valueOf(package_name.get(position))));
    }



    @Override
    public int getItemCount() {
        return notification_id.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView content_txt, user_txt, time_txt, date_txt;
        //ImageView logo_txt;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            content_txt = itemView.findViewById(R.id.message_text);
            user_txt = itemView.findViewById(R.id.user_name);
            time_txt = itemView.findViewById(R.id.message_time);
            date_txt = itemView.findViewById(R.id.message_date);
            //logo_txt = itemView.findViewById(R.id.notification_logo);
        }
    }
}
