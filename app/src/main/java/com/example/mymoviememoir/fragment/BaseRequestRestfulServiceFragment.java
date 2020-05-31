package com.example.mymoviememoir.fragment;

import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.network.interfaces.DefaultRequestHttpAction;
import com.example.mymoviememoir.network.RequestHelper;

/**
 * @author sunkai
 */
public abstract   class BaseRequestRestfulServiceFragment extends Fragment implements DefaultRequestHttpAction {
    @Override
    public void preExecute(RequestHelper helper) {
    }

    @Override
    public void onExecuteFailed(RequestHelper helper, String message, Exception ex) {
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
    }
}
