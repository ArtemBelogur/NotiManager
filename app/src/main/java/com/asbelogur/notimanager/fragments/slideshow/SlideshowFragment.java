package com.asbelogur.notimanager.fragments.slideshow;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asbelogur.notimanager.AppsAdapter;
import com.asbelogur.notimanager.DatabaseHelper;
import com.asbelogur.notimanager.NotificationsAdapter;
import com.asbelogur.notimanager.R;
import com.asbelogur.notimanager.fragments.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    RecyclerView recyclerView;

    DatabaseHelper dbHelper;
    ArrayList<String> id, package_name, appName, textOfNotification, user, post_time, chanel_id, group_id, notification_id;
    AppsAdapter appsAdapter;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        recyclerView = root.findViewById(R.id.apps_recyclerview);
        dbHelper = new DatabaseHelper(root.getContext());
        id = new ArrayList<>();
        package_name = new ArrayList<>();
        appName = new ArrayList<>();
        /*
        textOfNotification = new ArrayList<>();
        user = new ArrayList<>();
        post_time = new ArrayList<>();
        chanel_id = new ArrayList<>();
        group_id = new ArrayList<>();
        notification_id = new ArrayList<>();
         */

        storeDataInArrays();

        appsAdapter = new AppsAdapter(root.getContext(), id, package_name, appName);
        recyclerView.setAdapter(appsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }

    public void storeDataInArrays() {
        Cursor cursor = dbHelper.getUniqueAppsNames();
        if (cursor.getCount() > 0)
            while (cursor.moveToNext()) {
                id.add(0, cursor.getString(0));
                package_name.add(0, cursor.getString(1));
                appName.add(0, cursor.getString(2));
            }
    }
}