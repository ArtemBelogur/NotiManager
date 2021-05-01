package com.asbelogur.notimanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class AppActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    DatabaseHelper dbHelper;
    ArrayList<String> id, package_name, appName, textOfNotification, user, post_time, chanel_id, group_id, notification_id;
    NotificationsAdapter notificationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        String name = getIntent().getStringExtra("name");

        recyclerView = findViewById(R.id.app_recyclerview);
        dbHelper = new DatabaseHelper(AppActivity.this);
        id = new ArrayList<>();
        package_name = new ArrayList<>();
        appName = new ArrayList<>();
        textOfNotification = new ArrayList<>();
        user = new ArrayList<>();
        post_time = new ArrayList<>();
        chanel_id = new ArrayList<>();
        group_id = new ArrayList<>();
        //notification_id = new ArrayList<>();

        storeDataInArrays(name);

        notificationsAdapter = new NotificationsAdapter(AppActivity.this, id, package_name, appName, user, textOfNotification, post_time, chanel_id, group_id);
        recyclerView.setAdapter(notificationsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AppActivity.this));
    }

    public void storeDataInArrays(String name) {
        Cursor cursor = dbHelper.readNotifications();

        String name1;
        if (cursor.getCount() > 0)
            while (cursor.moveToNext()) {
                name1 = cursor.getString(2);
                if (name1.equals(name)) {
                    id.add(0, cursor.getString(0));
                    package_name.add(0, cursor.getString(1));
                    appName.add(0, cursor.getString(2));
                    user.add(0, cursor.getString(3));
                    textOfNotification.add(0, cursor.getString(4));
                    post_time.add(0, cursor.getString(5));
                    chanel_id.add(0, cursor.getString(6));
                    group_id.add(0, cursor.getString(7));
                }
            }
    }
}