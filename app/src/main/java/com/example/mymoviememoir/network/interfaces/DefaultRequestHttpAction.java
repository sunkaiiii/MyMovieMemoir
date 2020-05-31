package com.example.mymoviememoir.network.interfaces;

import com.example.mymoviememoir.network.CustomNetworkAsyncTask;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;

/**
 * @author sunkai
 */
public interface DefaultRequestHttpAction extends RequestAction{
    /**
     * The HTTP request action for Activities and Fragments is similar
     * To reduce the duplicated code, use interface with default implementations to allow Activities and Fragments implement it.
     * @param api the object inside the MyMovieMemoirRestfulAPI Enum class
     * @param model the HTTP request model
     */
    default void requestRestfulService(MyMovieMemoirRestfulAPI api, RestfulGetModel model) {
        new CustomNetworkAsyncTask(new RequestHelper(api, model), this).execute();
    }

    default void requestRestfulService(MyMovieMemoirRestfulAPI api, RestfulPostModel model) {
        new CustomNetworkAsyncTask(new RequestHelper(api, model), this).execute();
    }

    default void requestRestfulService(MyMovieMemoirRestfulAPI api, RestfulPutModel model) {
        new CustomNetworkAsyncTask(new RequestHelper(api, model), this).execute();
    }

    default void requestRestfulService(MyMovieMemoirRestfulAPI api, RestfulDeleteModel model) {
        new CustomNetworkAsyncTask(new RequestHelper(api, model), this).execute();
    }
}
