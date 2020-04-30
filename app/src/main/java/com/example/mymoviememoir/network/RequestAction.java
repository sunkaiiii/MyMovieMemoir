package com.example.mymoviememoir.network;

public interface RequestAction {
    void preExecute(RequestHelper helper);
    void onExecuteFailed(RequestHelper helper, Exception ex);
    void onPostExecute(RequestHelper helper, String response);
}
