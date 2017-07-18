package com.example.donghyunlee.project2w;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity  {

    private static final int GRID_LAYOUT = 0;
    private static final int LIST_LAYOUT = 1;
    private static final int ITEM_SIZE = 8;
    private int willChange_flag = 3;
    private List<ContentItem> items = new ArrayList<>();
    final DBManager mDBManager = new DBManager(getApplicationContext(), "Store.db", null, 1);
    @BindView(R.id.orderDist) Button distButton;
    @BindView(R.id.orderPopular) Button PopularButton;
    @BindView(R.id.orderRecent) Button recentButton;
    @BindView(R.id.changeCard) ImageButton changeButton;
    @BindView(R.id.selectMenu) ImageButton selectButton;
    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    StaggeredGridLayoutManager mStaggeredLayoutManager;
    LinearLayoutManager mLinearLayoutManger;
    FragmentManager fm = getSupportFragmentManager();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        settingItem();          // 리사이클러뷰에 사용할 Item을 List에 담는 것.
        actionRecylerView();    // 리사이클러뷰 생성 및 레이아웃 셋팅(Item 등)
        init();
    }

    private void init() {
        changeButton.setImageResource(R.drawable.ic_listbutton);
        distButton.setTextColor(getResources().getColor(R.color.colorGreen));
        //    Item List sorting
        Collections.sort(items, new DistAscCompare());
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.selectMenu)
    void selectmenu(){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, selectButton);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item0:
                        Intent intent = new Intent(MainActivity.this, AddRegisterActivity.class) ;
                        startActivity(intent) ;
                        return true;
                    case R.id.item1:
                        Toast.makeText(getApplicationContext(), "내가 체크한 맛집", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item2 :
                        Toast.makeText(getApplicationContext(), "맛집 위치 보기", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item3:
                        Toast.makeText(getApplicationContext(), "사용자정보", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item4:
                        Toast.makeText(getApplicationContext(), "종료하기", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return true;
                }
            }


        });
    }

    @OnClick(R.id.changeCard)
    void settingLayoutButton(){
        if(willChange_flag == GRID_LAYOUT || willChange_flag == 3){
            willChange_flag = LIST_LAYOUT;
        }
        else{
            willChange_flag = GRID_LAYOUT;
        }
        if(willChange_flag == GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
            changeButton.setImageResource(R.drawable.ic_listbutton);
        }
        else{
            mRecyclerView.setLayoutManager(mLinearLayoutManger);
            changeButton.setImageResource(R.drawable.ic_dashbutton);
        }
    }

    @OnClick(R.id.orderDist)
    void distMethod() {
        Collections.sort(items, new DistAscCompare());
        mAdapter.notifyDataSetChanged();
        colorChange("distMethod");
    }
    @OnClick(R.id.orderPopular)
    void popularMethod() {
        Collections.sort(items, new PopularDescCompare());
        mAdapter.notifyDataSetChanged();
        colorChange("popularMethod");
    }
    @OnClick(R.id.orderRecent)
    void recentMethod(){
        Collections.sort(items, new RecentAscCompare());
        mAdapter.notifyDataSetChanged();
        colorChange("recentMethod");
    }
    void actionRecylerView(){
         /*
            Staggered 레이아웃매니저 setting
         */
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(2,1);
        mStaggeredLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mStaggeredLayoutManager.setOrientation(StaggeredGridLayoutManager.VERTICAL);

        /*
            Linear 레이아웃매니저 setting
         */
        mLinearLayoutManger = new LinearLayoutManager(getApplicationContext());

        /*
            리사이클러뷰 setting
         */
        mAdapter = new RecyclerAdapter(this, items, R.layout.cardview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    /*
       글씨 색깔 체인지
        - 셀렉트로도 구현가능 (To do list)
    */
    void colorChange(String howMethod) {
        if(howMethod.equals("distMethod")){
            distButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGreen));
            PopularButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
            recentButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        }
        else if(howMethod.equals("popularMethod")){
            distButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
            PopularButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGreen));
            recentButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
        }
        else if(howMethod.equals("recentMethod")) {
            distButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
            PopularButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
            recentButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGreen));
        }
    }
    public void settingItem() {
        TypedArray storeimgs = getResources().obtainTypedArray(R.array.storeimg);
        String[] storenames = getResources().getStringArray(R.array.storename);
        String[] storecontents = getResources().getStringArray(R.array.storecontent);
        String[] dists = getResources().getStringArray(R.array.dist);
        String[] populars = getResources().getStringArray(R.array.popular);
        String[] recents = getResources().getStringArray(R.array.recent);
        int[] checks = getResources().getIntArray(R.array.check);
        String[] storeaddress = getResources().getStringArray(R.array.storeaddress);
        /*
            Date 받아오기.
         */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
        Date date = null;

        for(int i = 0 ; i < ITEM_SIZE ; i++){
            try {
                 date = simpleDateFormat.parse(recents[i]);
            } catch (ParseException e) {              // Insert this block.
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            items.add( new ContentItem(storeaddress[i], storeimgs.getResourceId(i, -1), storenames[i], storecontents[i], Double.parseDouble(dists[i]),
                    Double.parseDouble(populars[i]), date, checks[i]));
        }
    }
    /*
        Item List 정렬
     */
    static class DistAscCompare implements Comparator<ContentItem> {
        @Override
        public int compare(ContentItem o1, ContentItem o2) {
            if (o1.getDist() < o2.getDist()) return -1; // 오른쪽이 크면 -1
            if (o1.getDist() > o2.getDist()) return 1;  // 왼쪽이 크면 1
            return 0;                                   // 똑같으면 0
        }
    }
    static class PopularDescCompare implements Comparator<ContentItem> {
        @Override
        public int compare(ContentItem o1, ContentItem o2) {
            if (o1.getPopular() > o2.getPopular()) return -1;
            if (o1.getPopular() < o2.getPopular()) return 1;
            return 0;
        }
    }
    static class RecentAscCompare implements Comparator<ContentItem> {
        @Override
        public int compare(ContentItem o1, ContentItem o2) {

            return o1.getRecent().compareTo(o2.getRecent());
        }
    }
}