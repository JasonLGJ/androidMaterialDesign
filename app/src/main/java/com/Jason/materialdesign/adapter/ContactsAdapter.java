package com.Jason.materialdesign.adapter;


import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Jason.materialdesign.R;
import com.Jason.materialdesign.model.ContactData;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2003,成都天奥信息科技有限公司
 * All rights reserved.
 *
 * 功能描述：联系人adapter
 * 编写人：李广金
 * 开始日期：2020.03.17
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsListHolder> implements SectionIndexer {

    /**
     * 联系人分区
     */
    private SparseIntArray mPositionOfSection;

    /**
     * 联系人分区
     */
    private SparseIntArray mSectionOfPosition;

    // 联系人列表
    private List<ContactData> mContactDataList;

    public ContactsAdapter(List<ContactData> contactDatas) {
        this.mContactDataList = contactDatas;
    }

    @NonNull
    @Override
    public ContactsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item_contact, parent, false);
        ContactsListHolder holder = new ContactsListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsListHolder holder, int position) {
        ContactData contactData = mContactDataList.get(position);
        if (contactData != null) {
            holder.avaterView.setImageResource(R.mipmap.default_avatar);
            holder.nameTxt.setText(contactData.getUserName());
            //首字母显示
            String head = contactData.getFirstLetter();
            holder.pinyinTxt.setText(head);
            if (!head.equals("")) {
                holder.pinyinTxt.setVisibility(View.VISIBLE);
            }
            else {
                holder.pinyinTxt.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mContactDataList.size();
    }

    /**
     * SectionIndexer分区方法
     */
    @Override
    public Object[] getSections() {
        mPositionOfSection = new SparseIntArray();
        mSectionOfPosition = new SparseIntArray();

        // 传入的联系人数量
        int count = mContactDataList.size();

        // list后期转换为array
        List<String> list = new ArrayList<>();
        list.add("");
        mPositionOfSection.put(0, 0);
        mSectionOfPosition.put(0, 0);

        // 开始分区
        for (int i = 1; i < count; i++) {
            String letter = mContactDataList.get(i).getFirstLetter();
            int section = list.size() - 1;
            if (list.get(section) != null && !list.get(section).equals(letter)) {
                list.add(letter);
                section++;
                mPositionOfSection.put(section, i);
            }
            mSectionOfPosition.put(i, section);
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * SectionIndexer分区方法
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        return mPositionOfSection.get(sectionIndex);
    }

    /**
     * SectionIndexer分区方法
     */
    @Override
    public int getSectionForPosition(int position) {
        return mSectionOfPosition.get(position);
    }

    /**
     * 刷新数据
     */
    public void setContactDataList(List<ContactData> contactDataList) {
        mContactDataList = contactDataList;
        notifyDataSetChanged();
    }

    /**
     * 普通联系人holder
     */
    class ContactsListHolder extends RecyclerView.ViewHolder {

        TextView pinyinTxt;

        ImageView avaterView;

        TextView nameTxt;

        public ContactsListHolder(View view) {
            super(view);
            pinyinTxt = (TextView) view.findViewById(R.id.pinyin);
            avaterView = (ImageView) view.findViewById(R.id.avatar);
            nameTxt = (TextView) view.findViewById(R.id.name);
        }
    }
}
