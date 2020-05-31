package com.example.mymoviememoir.network.request;

import com.example.mymoviememoir.network.interfaces.RestfulGetModel;

import java.util.Arrays;
import java.util.List;

public class MovieSuburbRequest implements RestfulGetModel {
    private int personId;
    private String startingDate;
    private String endingDate;

    public MovieSuburbRequest(int personId, String startingDate, String endingDate) {
        this.personId = personId;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }

    public int getPersonId() {
        return personId;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    @Override
    public List<String> getPathParameter() {
        return Arrays.asList(String.valueOf(personId), startingDate, endingDate);
    }
}
