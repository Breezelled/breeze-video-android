package com.example.cby2112114536.ui.chart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author breeze
 */
public class ChartViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ChartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is chart fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}