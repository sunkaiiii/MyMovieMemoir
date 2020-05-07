package com.example.mymoviememoir.network.request;

import java.util.Date;

/**
 * @author sunkai
 */
public class SignUpPersonRequest extends BasePostModel {
    private String dob;
    private String fname;
    private String surname;
    private String postcode;
    private String gender;
    private String state;
    private CredentialsId credentialsId;

    public SignUpPersonRequest() {
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public CredentialsId getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(CredentialsId credentialsId) {
        this.credentialsId = credentialsId;
    }

    public static class CredentialsId {

        private int id;
        private String username;
        private String password;

        public CredentialsId(int id, String username, String password) {
            this.id = id;
            this.username = username;
            this.password = password;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
