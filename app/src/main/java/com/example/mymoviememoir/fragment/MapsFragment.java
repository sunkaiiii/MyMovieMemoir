package com.example.mymoviememoir.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.entities.Person;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.reponse.GetAddressResponse;
import com.example.mymoviememoir.network.reponse.Location;
import com.example.mymoviememoir.network.request.GetAddressLATRequest;
import com.example.mymoviememoir.network.request.base.BaseGoogleRequest;
import com.example.mymoviememoir.utils.GlobalContext;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.utils.PersonInfoUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends BaseRequestRestfulServiceFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location location;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(this);
            }
        }
        getUserAddress();
    }

    private void getUserAddress() {
        final Person person = PersonInfoUtils.getPersonInstance();
        final String address = String.format("%s,%s,%s", person.getAddress().replace(' ', '+'), person.getState(), person.getPostcode());
        requestRestfulService(MyMovieMemoirRestfulAPI.GET_ADDRESS_LAT, new GetAddressLATRequest(address));
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        switch (helper.getRestfulAPI()) {
            case GET_ADDRESS_LAT:
                GetAddressResponse addressResponse = GsonUtils.fromJsonToObject(response, GetAddressResponse.class);
                if (!TextUtils.equals(addressResponse.getStatus(), "OK") || addressResponse.getResults().size() == 0) {
                    return;
                }
                location = addressResponse.getResults().get(0).getGeometry().getLocation();
                tryToSetMyLocation();
                break;
            default:
                break;
        }
    }

    private void tryToSetMyLocation() {
        if (mMap == null || location == null) {
            return;
        }
        LatLng latLng = new LatLng(location.getLat(), location.getLng());
        mMap.addMarker(new MarkerOptions().position(latLng).title("Home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        tryToSetMyLocation();
    }
}
