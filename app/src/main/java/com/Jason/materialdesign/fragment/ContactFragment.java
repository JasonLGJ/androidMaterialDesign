package com.Jason.materialdesign.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Jason.materialdesign.R;
import com.Jason.materialdesign.adapter.ContactsAdapter;
import com.Jason.materialdesign.model.ContactData;
import com.Jason.materialdesign.widget.PinyinUtils;
import com.Jason.materialdesign.widget.SideBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ContactFragment extends Fragment {

    private static final String TAG = "ContactFragment";

    private ContactsAdapter mContactsAdapter;

    private RecyclerView mRecyclerView;

    private SideBar mSideBar;

    private List<ContactData> mContactDataList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        initContacts(view);
        return view;
    }

    /**
     * 装载数据
     * @param view 根view
     */
    private void initContacts(View view){
        mRecyclerView = view.findViewById(R.id.recyclercontactlistId);
        mSideBar = view.findViewById(R.id.sidebar);

        mContactsAdapter = new ContactsAdapter(new ArrayList<ContactData>());
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(verticalLayoutManager);
        mRecyclerView.setAdapter(mContactsAdapter);

        initTestData();
    }

    /**
     * 初始化测试数据
     * @return
     */
    private void initTestData(){

        mContactDataList = new ArrayList<>();

        //子线程请求数据
        Executor singleThread = Executors.newSingleThreadExecutor();
        singleThread.execute(new Runnable() {
            @Override
            public void run() {
                List<ContactData> tempContactData = new ArrayList<>();
                tempContactData.add(new ContactData("alibaba", "123456", 1));
                tempContactData.add(new ContactData("tencent", "654321", 2));
                tempContactData.add(new ContactData("spaceon", "999999", 3));
                tempContactData.add(new ContactData("666", "0000", 4));
                tempContactData.add(new ContactData("老黄", "18381680077", 0));
                tempContactData.add(new ContactData("ligj", "1550805", 9));
                tempContactData.add(new ContactData("wwww", "555", 11));
                tempContactData.add(new ContactData("ghjg", "666", 12));
                tempContactData.add(new ContactData("uyty", "678", 13));
                tempContactData.add(new ContactData("xcvb", "8980", 14));
                mContactDataList.addAll(getOrderContacts(tempContactData));
                // 主线程刷新
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mContactsAdapter.setContactDataList(mContactDataList);
                    }
                });
            }
        });
        Log.d(TAG, "initTestData: "+mContactDataList.size());

        //侧面栏点击事件
        mSideBar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                for (int i = 0; i<mContactDataList.size(); i++){
                    if (selectStr.equalsIgnoreCase(mContactDataList.get(i).getFirstLetter())){
                        mRecyclerView.scrollToPosition(i);
                    }
                }
            }
        });
    }


    /**
     * 联系人数据排序
     *
     * @return
     */
    private List<ContactData> getOrderContacts(List<ContactData> contactDataList) {
        List<ContactData> contactOrderList = new ArrayList<>(contactDataList.size());

        // 排序
        for (ContactData contactData : contactDataList) {

            String name = contactData.getUserName();
            String head = PinyinUtils.getPinyin(name.trim());
            //英文字符串时不能正确返回拼音首字母
            head = head.length() > 1 ? head.substring(0, 1) : head;
            head = (head.compareToIgnoreCase("A") < 0 || head.compareToIgnoreCase("Z") > 0) ? "#" : head;
            contactData.setFirstLetter(head);

            //根据head值进行排序从A-Z
            if (head.equals("#")) {
                contactOrderList.add(contactOrderList.size(), contactData);
            }
            else {
                int i;
                for (i = 0; i < contactOrderList.size(); i++) {
                    String head1 = contactOrderList.get(i).getFirstLetter();
                    if (head.compareToIgnoreCase(head1) <= 0 || head1.compareTo("#") == 0) {
                        break;
                    }
                }
                contactOrderList.add(i, contactData);
            }
        }

        //把首字母相同的归为一组（清空首字母）
        String lastHead = "";
        for (ContactData d : contactOrderList) {
            if (!lastHead.equals("")) {
                if (lastHead.equals(d.getFirstLetter())) {
                    d.setFirstLetter("");
                }
                else {
                    lastHead = d.getFirstLetter();
                }
            }
            else {
                lastHead = d.getFirstLetter();
            }
        }

        return contactOrderList;
    }
}
