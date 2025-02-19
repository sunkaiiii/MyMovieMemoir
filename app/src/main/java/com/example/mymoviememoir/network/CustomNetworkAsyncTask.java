package com.example.mymoviememoir.network;

import android.os.AsyncTask;

import com.example.mymoviememoir.network.interfaces.RequestAction;

import java.io.IOException;

/**
 * A general AsyncTask Class to implement the basic function of Network requesting
 * @author sunkai
 */
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
        } catch (NullPointerException | IOException | RequestHelper.NoSuchTypeOfModelException | OkHttpNetworkConnection.HTTPConnectionErrorException e) {
            e.printStackTrace();
            return new CustomNetworkResponse(e.getMessage(), e);
        }
    }

    @Override
    protected void onPostExecute(CustomNetworkResponse response) {
        if (response.getException() != null) {
            requestAction.onExecuteFailed(requestHelper, response.getException().getMessage(), response.getException());
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
