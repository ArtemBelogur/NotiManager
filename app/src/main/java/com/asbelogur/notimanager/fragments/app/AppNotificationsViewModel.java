package com.asbelogur.notimanager.fragments.app;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AppNotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AppNotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}