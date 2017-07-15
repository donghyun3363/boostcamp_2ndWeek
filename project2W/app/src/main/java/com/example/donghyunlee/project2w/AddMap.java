package com.example.donghyunlee.project2w;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DONGHYUNLEE on 2017-07-13.
 */

public class AddMap extends Fragment implements OnMapReadyCallback {


    private String address;
    private String storeName;
    private double lat;
    private double lng;

    public AddMap() {
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 버스프로바이더 등록
        BusProvider.getInstance().register(this);
    }
    @Subscribe//액션이 일어난다 !
    public void recievedMessage(Message message) {

        address = message.getItem().getStoreAddress();
        storeName = message.getItem().getStoreName();
        Log.e("과연 ", ":::::::::"+message.getItem().getStoreAddress());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.addmap, container, false);
        ImageButton mapback = (ImageButton) v.findViewById(R.id.mapback);
        Button mapexit = (Button) v.findViewById(R.id.mapexit);
        Button mapnext = (Button) v.findViewById(R.id.bottomnext);
        MapView mapview=(MapView)v.findViewById(R.id.map);
        TextView addressText = (TextView) v.findViewById(R.id.addressText);

        addressText.setText(address);
        addressText.bringToFront(); // 뷰위에 뷰 덮어쓰기

        Geocoder coder = new Geocoder(getActivity());

        try {
            List<Address> addrList = coder.getFromLocationName(address,3);
            Iterator<Address> addrs = addrList.iterator();

            String infoAddr = "";

            while(addrs.hasNext()) {
                Address loc = addrs.next();
                infoAddr += String.format("Coord : %f, %f", loc.getLatitude(), loc.getLongitude());
                Log.e("!!!!!!!!!!!!!!","@@@@@@@@@@2"+infoAddr);
                lat = loc.getLatitude();
                lng = loc.getLongitude();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        mapview.onCreate(savedInstanceState);
        mapview.onResume();
        mapview.getMapAsync(this);

        mapback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(AddMap.this).commit();
                fragmentManager.popBackStack();
            }
        });
        mapexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mapnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;

    }

    @Override
    public void onMapReady(final GoogleMap map) {

        Log.e("lat", ":" + lat);
        Log.e("lat", ":" + lng);
        LatLng SEOUL = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title(storeName);
        markerOptions.snippet(address);
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));

    }


}
