package com.example.mymoviememoir.network;

import android.os.AsyncTask;

import java.io.IOException;

public class CustomNetworkAsyncTask extends AsyncTask<RequestHelper, RequestHelper, CustomNetworkAsyncTask.CustomNetworkResponse> {
    private RequestHelper requestHelper;
    private RequestAction requestAction;

    public CustomNetworkAsyncTask(RequestHelper requestHelper, RequestAction requestAction) {
        this.requestHelper = requestHelper;
        this.requestAction = requestAction;
    }

    @Override
    protected void onPreExecute() {
        requestAction.preExecute(requestHelper);
    }

    @Override
    protected CustomNetworkResponse doInBackground(RequestHelper... helpers) {
        try {
            return new CustomNetworkResponse(OkHttpNetworkConnection.getInstance().requestRestfulService(requestHelper), null);
        } catch (NullPointerException | IOException e) {
            requestAction.onExecuteFailed(requestHelper, e);
            e.printStackTrace();
            return new CustomNetworkResponse(null, e);
        }
    }

    @Override
    protected void onPostExecute(CustomNetworkResponse response) {
        if (response.getException() != null) {
            requestAction.onExecuteFailed(requestHelper, response.getException());
            return;
        }
        requestAction.onPostExecute(requestHelper, response.getResponseString());
    }

    static class CustomNetworkResponse {
        private final String responseString;
        private final Exception e;

        CustomNetworkResponse(String responseString, Exception e) {
            this.responseString = responseString;
            this.e = e;
        }

        String getResponseString() {
            return responseString;
        }

        Exception getException() {
            return e;
        }
    }
}
