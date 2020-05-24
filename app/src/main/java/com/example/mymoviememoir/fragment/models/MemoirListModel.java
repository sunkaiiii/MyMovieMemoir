package com.example.mymoviememoir.fragment.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.entities.Memoir;

import java.util.List;

/**
 * @author sunkai
 */
public class MemoirListModel extends ViewModel {
    private MutableLiveData<List<Memoir>> mMemoir;

    public MemoirListModel() {
        mMemoir = new MutableLiveData<>();
    }

    public void setMemoirs(List<Memoir> memoirs) {
        mMemoir.setValue(memoirs);
    }

    public LiveData<List<Memoir>> getMemoir() {
        return mMemoir;
    }
}
