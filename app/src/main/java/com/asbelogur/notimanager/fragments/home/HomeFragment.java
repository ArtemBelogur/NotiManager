package com.asbelogur.notimanager.fragments.home;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asbelogur.notimanager.DatabaseHelper;
import com.asbelogur.notimanager.NotificationsAdapter;
import com.asbelogur.notimanager.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;

    DatabaseHelper dbHelper;
    ArrayList<String> id, package_name, appName, textOfNotification, user, post_time, chanel_id, group_id, notification_id;
    NotificationsAdapter notificationsAdapter;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.main_recyclerview);
        dbHelper = new DatabaseHelper(root.getContext());
        id = new ArrayList<>();
        package_name = new ArrayList<>();
        appName = new ArrayList<>();
        textOfNotification = new ArrayList<>();
        user = new ArrayList<>();
        post_time = new ArrayList<>();
        chanel_id = new ArrayList<>();
        group_id = new ArrayList<>();
        //notification_id = new ArrayList<>();

        storeDataInArrays();

        notificationsAdapter = new NotificationsAdapter(root.getContext(), id, package_name, appName, user, textOfNotification, post_time, chanel_id, group_id);
        recyclerView.setAdapter(notificationsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }

    public void storeDataInArrays() {
        Cursor cursor = dbHelper.readNotifications();
        if (cursor.getCount() > 0)
            while (cursor.moveToNext()) {
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