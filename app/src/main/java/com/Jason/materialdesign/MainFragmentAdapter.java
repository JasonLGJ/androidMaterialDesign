package com.Jason.materialdesign;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Copyright (c) 2003,成都天奥信息科技有限公司
 * All rights reserved.
 *
 * 功能描述：主界面Fragment适配器
 * 编写人：李广金
 * 开始日期：2020.03.12
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public MainFragmentAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragmentList = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
