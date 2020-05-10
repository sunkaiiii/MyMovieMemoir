package com.example.mymoviememoir.fragment.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.network.request.SignUpPersonRequest;

/**
 * @author sunkai
 */
public class MainTopViewPersonModel extends ViewModel {
    private MutableLiveData<SignUpPersonRequest> mPerson;

    public MainTopViewPersonModel() {
        mPerson = new MutableLiveData<>();
    }

    public void setPerson(SignUpPersonRequest person) {
        mPerson.setValue(person);
    }

    public LiveData<SignUpPersonRequest> getPerson() {
        return mPerson;
    }
}
