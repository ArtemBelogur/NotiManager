package com.asbelogur.notimanager.fragments.all;

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

import java.util.ArrayList;
import java.util.Objects;

public class AllNotifications extends Fragment {

    public static RecyclerView recyclerView;

    public DatabaseHelper dbHelper;
    public static ArrayList<String> id, package_name, appName, textOfNotification, user, post_time, chanel_id, group_id;
    public NotificationsAdapter notificationsAdapter;

    private AllNotificationsViewModel allNotificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allNotificationsViewModel =
                ViewModelProviders.of(this).get(AllNotificationsViewModel.class);
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

        /*String refresh = Objects.requireNonNull(requireActivity().getIntent().getExtras()).getString("refresh");
        assert refresh != null;
        if (refresh.equals("yes")){
            storeDataInArrays();
            notificationsAdapter.notifyDataSetChanged();
        }
         */

        storeDataInArrays();

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        notificationsAdapter = new NotificationsAdapter(root.getContext(), id, package_name, appName, user, textOfNotification, post_time, chanel_id, group_id);

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(notificationsAdapter);

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