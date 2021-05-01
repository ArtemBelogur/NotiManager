package com.asbelogur.notimanager.fragments.gallery;

import android.annotation.SuppressLint;
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

import com.asbelogur.notimanager.DatabaseHelper;
import com.asbelogur.notimanager.NotificationsAdapter;
import com.asbelogur.notimanager.R;
import com.asbelogur.notimanager.fragments.home.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GalleryFragment extends Fragment {

    RecyclerView recyclerView;

    DatabaseHelper dbHelper;
    ArrayList<String> id, package_name, appName, textOfNotification, user, post_time, chanel_id, group_id, notification_id;
    NotificationsAdapter notificationsAdapter;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = root.findViewById(R.id.today_recyclerview);
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM");
        Date currentDate = new Date();
        String dateText = simpleDateFormat.format(currentDate);

        if (cursor.getCount() > 0)
            while (cursor.moveToNext()) {
                String date = simpleDateFormat.format(Long.parseLong(cursor.getString(5)));

                if (dateText.equals(date)) {
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