package com.spaceon.conbox.contact.model;

import com.google.gson.annotations.SerializedName;
import com.spaceon.conbox.utils.PinyinUtils;
import com.spaceon.smslibrary.db.BaseDbData;

import java.io.Serializable;


/**
 * Copyright (c) 2003,成都天奥信息科技有限公司
 * All rights reserved.
 *
 * 功能描述：手机联系人
 * 编写人：李广金
 * 开始日期：2020.03.13
 */
public class ContactData extends BaseDbData implements Serializable, Comparable<ContactData> {

    @SerializedName("userId")
    int userId;

    @SerializedName("contactId")
    int contactId;

    @SerializedName("contactName")
    String userName;

    @SerializedName("contactMobileNo")
    String phoneNum;

    @SerializedName("countryCode")
    int cityCode;

    @SerializedName("thumbnailUrl")
    String portrait;

    private String pinyin;
    private String firstLetter;

    public ContactData()
    {
    }

    /**
     * 匹配从手机获取的联系人信息
     *
     * @param userName
     * @param phoneNum
     * @param cityCode
     */
    public ContactData(String userName, String phoneNum, int cityCode)
    {
        // this.userId=id;
        this.phoneNum = phoneNum;
        this.userName = userName;
        this.cityCode = cityCode;

        pinyin = PinyinUtils.getPinyin(userName);
        firstLetter = pinyin.substring(0, 1).toUpperCase();
        if (!firstLetter.matches("[A-Z]"))
        {
            firstLetter = "#";
        }
    }

    /**
     * 匹配从服务器获取的联系人信息
     *
     * @param userName
     * @param phoneNum
     * @param cityCode
     * @param userId
     * @param contactrId
     */
    public ContactData(String userName, String phoneNum, int cityCode, int userId, int contactrId)
    {
        // this.userId=id;
        this.phoneNum = phoneNum;
        this.userName = userName;
        this.cityCode = cityCode;
        this.userId = userId;
        this.contactId = contactrId;

        pinyin = PinyinUtils.getPinyin(userName);
        firstLetter = pinyin.substring(0, 1).toUpperCase();
        if (!firstLetter.matches("[A-Z]"))
        {
            firstLetter = "#";
        }
    }

    /**
     * 匹配从服务器获取的联系人信息
     *
     * @param userName
     * @param phoneNum
     * @param cityCode
     * @param userId
     * @param contactrId
     */
    public ContactData(String userName, String phoneNum, int cityCode, int userId, int contactrId, String portrait)
    {
        // this.userId=id;
        this.phoneNum = phoneNum;
        this.userName = userName;
        this.cityCode = cityCode;
        this.userId = userId;
        this.contactId = contactrId;
        this.portrait = portrait;

        pinyin = PinyinUtils.getPinyin(userName);
        firstLetter = pinyin.substring(0, 1).toUpperCase();
        if (!firstLetter.matches("[A-Z]"))
        {
            firstLetter = "#";
        }
    }

    public String getPinyin()
    {
        return pinyin;
    }

    public void setPinyin(String pinyin)
    {
        this.pinyin = pinyin;
    }

    public String getFirstLetter()
    {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter)
    {
        this.firstLetter = firstLetter;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public int getCityCode()
    {
        return cityCode;
    }

    public void setCityCode(int cityCode)
    {
        this.cityCode = cityCode;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getContactId()
    {
        return contactId;
    }

    public void setContactId(int contactId)
    {
        this.contactId = contactId;
    }

    public String getPortrait()
    {
        if (portrait == null)
        {
            portrait = "";
        }
        return portrait;
    }

    public void setPortrait(String portrait)
    {
        this.portrait = portrait;
    }

    @Override
    public String toString()
    {
        return "ContactData{" +
                "userId=" + userId +
                ", contactId=" + contactId +
                ", userName='" + userName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", cityCode=" + cityCode +
                ", portrait='" + portrait + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                '}';
    }

    @Override
    public int compareTo(ContactData another)
    {
        if (firstLetter.equals("#") && !another.getFirstLetter().equals("#"))
        {
            return 1;
        }
        else if (!firstLetter.equals("#") && another.getFirstLetter().equals("#"))
        {
            return -1;
        }
        else
        {
            return pinyin.compareToIgnoreCase(another.getPinyin());
        }
    }
}
