package com.example.mymoviememoir.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.network.request.SignUpPersonRequest;

/**
 * @author sunkai
 */
public class MainViewPersonModel extends ViewModel {
    private MutableLiveData<SignUpPersonRequest> mPerson;

    public MainViewPersonModel() {
        mPerson = new MutableLiveData<>();
    }

    public void setPerson(SignUpPersonRequest person) {
        mPerson.setValue(person);
    }

    public LiveData<SignUpPersonRequest> getPerson() {
        return mPerson;
    }
}
