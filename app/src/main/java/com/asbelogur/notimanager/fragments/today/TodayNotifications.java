package com.asbelogur.notimanager.fragments.today;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asbelogur.notimanager.useful.DatabaseHelper;
import com.asbelogur.notimanager.adapters.NotificationsAdapter;
import com.asbelogur.notimanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TodayNotifications extends Fragment {

    RecyclerView recyclerView;

    DatabaseHelper dbHelper;
    ArrayList<String> id, package_name, appName, textOfNotification, user, post_time, chanel_id, group_id, notification_id;
    NotificationsAdapter notificationsAdapter;

    private TodayNotificationsViewModel todayNotificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        todayNotificationsViewModel =
                ViewModelProviders.of(this).get(TodayNotificationsViewModel.class);
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

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        return root;
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    dbHelper.deleteOneRow(id.get(viewHolder.getAdapterPosition()));

                    id.remove(viewHolder.getAdapterPosition());
                    package_name.remove(viewHolder.getAdapterPosition());
                    appName.remove(viewHolder.getAdapterPosition());
                    textOfNotification.remove(viewHolder.getAdapterPosition());
                    user.remove(viewHolder.getAdapterPosition());
                    post_time.remove(viewHolder.getAdapterPosition());
                    chanel_id.remove(viewHolder.getAdapterPosition());
                    group_id.remove(viewHolder.getAdapterPosition());

                    notificationsAdapter.notifyDataSetChanged();
                }
            };

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