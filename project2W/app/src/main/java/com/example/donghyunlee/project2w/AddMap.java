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
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DONGHYUNLEE on 2017-07-13.
 */

public class AddMap extends Fragment implements OnMapReadyCallback {


    private String address;
    private String storeName;
    private double lat;
    private double lng;
    private Message tempMessage;

    @BindView(R.id.map)
    MapView mapview;
    @BindView(R.id.addressText)
    TextView addressText;
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
       // BusProvider.getInstance().register(this);
    }

    @Subscribe
    public void recievedMessage(Message message) {
        tempMessage = message;
        address = message.getItem().getStoreAddress();
        storeName = message.getItem().getStoreName();
        Log.e("액티비티부터 받은 메세지", ":::::::::"+message.getItem().getStoreAddress());

    }
    @Produce
    public Message produceItem() {
        Log.e("액티비티로 보낼 메세지","::" + tempMessage.getItem().getStoreAddress());
        return tempMessage;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.addmap, container, false);
        ButterKnife.bind(this, v);

        /*
            지도 위 주소 정보 setting
         */
        addressText.setText(address);
        addressText.bringToFront(); // 뷰위에 뷰 덮어쓰기

//        settingMap();
        mapview.onCreate(savedInstanceState);
        mapview.onResume();
        mapview.getMapAsync(this);

        return v;

    }
    @OnClick(R.id.mapback)
    void backmapFun(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(AddMap.this).commit();
        fragmentManager.popBackStack();
    }
    @OnClick(R.id.mapexit)
    void mapExitFun(){
        getActivity().finish();
    }
    @OnClick(R.id.bottomnext)
    void bottomNextfun(){
        BusProvider.getInstance().post(produceItem());
        getActivity().finish();
    }

    void settingMap(){
        Geocoder coder = new Geocoder(getActivity());
        try {
            List<Address> addrList = coder.getFromLocationName(address,3);
            Iterator<Address> addrs = addrList.iterator();

            String infoAddr = "";

            while(addrs.hasNext()) {
                Address loc = addrs.next();
                infoAddr += String.format("Coord : %f, %f", loc.getLatitude(), loc.getLongitude());
                lat = loc.getLatitude();
                lng = loc.getLongitude();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
