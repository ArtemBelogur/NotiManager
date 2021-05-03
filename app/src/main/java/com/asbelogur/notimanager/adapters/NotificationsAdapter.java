package com.asbelogur.notimanager.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asbelogur.notimanager.activities.ChatActivity;
import com.asbelogur.notimanager.R;
import com.asbelogur.notimanager.useful.CNLSHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private static final String LOGTAG = "NotificationsAdapter";

    private Context context;
    private ArrayList notification_id, package_name, appName, user, textOfNotification, post_time, chanel_id, group_id;

    public NotificationsAdapter(Context context, ArrayList notification_id, ArrayList package_name, ArrayList appName, ArrayList user, ArrayList textOfNotification, ArrayList post_time, ArrayList chanel_id, ArrayList group_id) {
        this.context = context;
        this.notification_id = notification_id;
        this.package_name = package_name;
        this.appName = appName;
        this.textOfNotification = textOfNotification;
        this.user = user;
        this.post_time = post_time;
        this.chanel_id = chanel_id;
        this.group_id = group_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.messages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, final int position) {
        holder.user_txt.setText(String.valueOf(user.get(position)));
        holder.content_txt.setText(String.valueOf(textOfNotification.get(position)));

        long timeLong = Long.parseLong(String.valueOf(post_time.get(position)));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        String time = simpleDateFormat.format(timeLong);
        holder.time_txt.setText(time);

        holder.logo_txt.setImageDrawable(CNLSHelper.getAppIconFromPackage(context, String.valueOf(package_name.get(position))));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("name", String.valueOf(user.get(position)));

                context.startActivity(intent);
            }
        });

        Log.i(LOGTAG, "Notifications Adapter is working fine");
    }

    @Override
    public int getItemCount() {
        return notification_id.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView content_txt, user_txt, time_txt;
        ImageView logo_txt, large_icon_txt;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            content_txt = itemView.findViewById(R.id.notification_text);
            user_txt = itemView.findViewById(R.id.notification_title);
            time_txt = itemView.findViewById(R.id.post_time);
            logo_txt = itemView.findViewById(R.id.notification_logo);
        }
    }
}
