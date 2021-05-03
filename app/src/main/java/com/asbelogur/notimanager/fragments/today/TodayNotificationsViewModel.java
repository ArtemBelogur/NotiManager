package com.asbelogur.notimanager.fragments.today;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodayNotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TodayNotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}