package com.Jason.materialdesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.Jason.materialdesign.fragment.ChatFragment;
import com.Jason.materialdesign.fragment.ContactFragment;
import com.Jason.materialdesign.fragment.FindFragment;
import com.Jason.materialdesign.fragment.MeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * 主界面fragmentlist
     */
    private List<Fragment> mMainFragmentList;

    BottomNavigationView mBottomNavigationView;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);

        mBottomNavigationView = findViewById(R.id.bottom_nav_view);
        mViewPager = findViewById(R.id.content_view_pager);

        initMainData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add:
                Toast.makeText(this, "添加", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    private void  initMainData(){
        mMainFragmentList = new ArrayList<>(4);
        mMainFragmentList.add(new ChatFragment());
        mMainFragmentList.add(new ContactFragment());
        mMainFragmentList.add(new FindFragment());
        mMainFragmentList.add(new MeFragment());

        //viewpager初始化
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mMainFragmentList);
        mViewPager.setAdapter(mainFragmentAdapter);

        //viewpager滑动点击等监听
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int menuId = item.getItemId();
                // 跳转指定页面：Fragment
                switch (menuId) {
                    case R.id.tab_chat:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_contacts:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_find:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.tab_me:
                        mViewPager.setCurrentItem(3);
                        break;
                }

                return false;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //滑动到某页面让底部导航栏为选中状态
                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
