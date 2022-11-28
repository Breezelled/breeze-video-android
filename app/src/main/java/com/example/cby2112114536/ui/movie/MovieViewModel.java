package com.example.cby2112114536.ui.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author breeze
 */
public class MovieViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MovieViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is movie fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}