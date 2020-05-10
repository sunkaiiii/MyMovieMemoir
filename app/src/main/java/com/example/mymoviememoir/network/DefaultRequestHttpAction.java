package com.example.mymoviememoir.network;

/**
 * @author sunkai
 */
public interface DefaultRequestHttpAction extends RequestAction{
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
