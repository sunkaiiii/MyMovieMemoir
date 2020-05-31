package com.example.mymoviememoir.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.entities.Cinema;
import com.example.mymoviememoir.entities.Person;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.interfaces.RestfulGetModel;
import com.example.mymoviememoir.network.interfaces.RestfulParameterModel;
import com.example.mymoviememoir.network.reponse.GetAddressResponse;
import com.example.mymoviememoir.network.reponse.Location;
import com.example.mymoviememoir.network.request.GetAddressLATRequest;
import com.example.mymoviememoir.network.request.GetCinemaLATRequest;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.utils.PersonInfoUtils;
import com.example.mymoviememoir.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * @author sunkai
 */
public class MapsFragment extends BaseRequestRestfulServiceFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location location;

    private Bitmap cinemaBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        generateCinemaBitmap();
        getUserAddress();
    }

    private void generateCinemaBitmap() {
        /*
        Change the icon of cinemas
         */
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.baseline_movie_white_24);
        if (drawable instanceof BitmapDrawable) {
            cinemaBitmap = Utils.tintBitmap(((BitmapDrawable) drawable).getBitmap(), ContextCompat.getColor(getContext(), R.color.primaryColor));
        }
    }

    private void getUserAddress() {
        final Person person = PersonInfoUtils.getPersonInstance();
        final String address = String.format("%s,%s,%s", person.getAddress().replace(' ', '+'), person.getState(), person.getPostcode());
        requestRestfulService(MyMovieMemoirRestfulAPI.GET_ADDRESS_LAT, new GetAddressLATRequest(address));
        requestRestfulService(MyMovieMemoirRestfulAPI.GET_ALL_CINEMA, (RestfulGetModel) ArrayList::new);
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        try {
            switch (helper.getRestfulAPI()) {
                case GET_ADDRESS_LAT:
                    GetAddressResponse addressResponse = GsonUtils.fromJsonToObject(response, GetAddressResponse.class);
                    if (!TextUtils.equals(addressResponse.getStatus(), "OK") || addressResponse.getResults().size() == 0) {
                        return;
                    }
                    location = addressResponse.getResults().get(0).getGeometry().getLocation();
                    tryToSetMyLocation(location, helper.getPathRequestModel());
                    break;
                case GET_ALL_CINEMA:
                    List<Cinema> cinemas = GsonUtils.fromJsonToList(response, Cinema.class);
                    for (Cinema cinema : cinemas) {
                        requestRestfulService(MyMovieMemoirRestfulAPI.GET_ADDRESS_LAT, new GetCinemaLATRequest(cinema.getCinemaName(), cinema.getLocationSuburb()));
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tryToSetMyLocation(Location location, RestfulParameterModel pathRequestModel) {
        if (mMap == null || location == null) {
            return;
        }

        if (pathRequestModel instanceof GetAddressLATRequest) {
            LatLng latLng = new LatLng(location.getLat(), location.getLng());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Home"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        } else {
            //Adding a random number to make a slight movement of the icon to avoid cinemas from the same suburb overlapping in the map.
            LatLng latLng = new LatLng(location.getLat() + Math.random()/100.0, location.getLng() + Math.random()/100.0);
            GetCinemaLATRequest requestModel = (GetCinemaLATRequest) pathRequestModel;
            mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(cinemaBitmap)).title(String.format("%s, %s", requestModel.getCinemaName(), requestModel.getCinemaSuburb())));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        tryToSetMyLocation(location, null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (cinemaBitmap != null) {
            cinemaBitmap.recycle();
            cinemaBitmap = null;
        }
    }
}
