package com.example.mymoviememoir.network.interfaces;

import com.example.mymoviememoir.network.RequestHelper;

public interface RequestAction {
    /**
     * The action that will be executed before a Network request
     * This would run on the UI thread
     * @param helper the helper class that contains all the information of the request
     */
    void preExecute(RequestHelper helper);

    /**
     * The action that will be executed when the Network request is unsuccessful
     * This would run on the UI thread
     * @param helper the helper class that contains all the information of the request
     * @param message the error message provided by the server side
     * @param ex the detail information which is contained in the Exception object
     */
    void onExecuteFailed(RequestHelper helper, String message, Exception ex);

    /**
     * The action that will be executed after the successful Network request
     * @param helper the helper class that contains all the information of the request
     * @param response The information provided by the server in the body of the response
     */
    void onPostExecute(RequestHelper helper, String response);
}
