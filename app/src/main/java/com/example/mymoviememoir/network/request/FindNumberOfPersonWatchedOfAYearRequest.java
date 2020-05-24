package com.example.mymoviememoir.network.request;

import com.example.mymoviememoir.network.RestfulGetModel;

import java.util.Arrays;
import java.util.List;

/**
 * @author sunkai
 */
public class FindNumberOfPersonWatchedOfAYearRequest implements RestfulGetModel {
    private int personId;
    private String year;

    public FindNumberOfPersonWatchedOfAYearRequest(int personId, String year) {
        this.personId = personId;
        this.year = year;
    }

    public int getPersonId() {
        return personId;
    }

    public String getYear() {
        return year;
    }

    @Override
    public List<String> getPathParameter() {
        return Arrays.asList(String.valueOf(personId), year);
    }
}
