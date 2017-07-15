package com.example.donghyunlee.project2w;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.squareup.otto.Produce;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DONGHYUNLEE on 2017-07-14.
 */

public class AddRegisterActivity extends AppCompatActivity {

    AddMap appmap = new AddMap();
    @Bind(R.id.addstorename)
    EditText addstorename;
    @Bind(R.id.addstoreaddress)
    EditText addstoreaddress;
    @Bind(R.id.addstorenumber)
    EditText addstorenumber;
    @Bind(R.id.addstoretext)
    EditText addstoretext;

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 버스프로파이더 등록
        BusProvider.getInstance().register(this);
    }
    @Produce
    public Message produceItem() {
// Provide an initial valu  e for location based on the last known position.
        View v = findViewById(R.id.addstoreaddress);
        return new Message(new ContentItem(addstoreaddress.getText().toString(),R.drawable.img_shybana, addstorename.getText().toString(),
                addstoretext.getText().toString(), "0","0","20170715",0));
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addregister);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.topback)
    void topBackFun(){
        finish();
    }
    @OnClick(R.id.topexit)
    void topNextFun() {
        finish();
    }
    @OnClick(R.id.bottomback)
    void bottomBackfun(){
        finish();
    }
    @OnClick(R.id.bottomnext)
    void bottomNextfun()
    {   //(int storeImg, String storeName, String storeContent, String dist, String popular, String recent,int checkbutton)
        BusProvider.getInstance().post(produceItem());
        openFragment(appmap);
    }
    private void openFragment(final AddMap fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.register_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
