package com.asbelogur.notimanager.fragments.all;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllNotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AllNotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}