package com.example.mymoviememoir.fragment.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.entities.Person;

/**
 * @author sunkai
 */
public class MainTopViewPersonModel extends ViewModel {
    private MutableLiveData<Person> mPerson;

    public MainTopViewPersonModel() {
        mPerson = new MutableLiveData<>();
    }

    public void setPerson(Person person) {
        mPerson.setValue(person);
    }

    public LiveData<Person> getPerson() {
        return mPerson;
    }
}
