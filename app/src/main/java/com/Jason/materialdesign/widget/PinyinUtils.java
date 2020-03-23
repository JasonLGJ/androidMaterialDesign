package com.spaceon.conbox.utils;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (c) 2003,成都天奥信息科技有限公司
 * All rights reserved.
 *
 * 功能描述：获取输入字符串的拼音信息
 * 编写人：李广金
 * 开始日期：2020.3.13
 */
public final class PinyinUtils
{
    private PinyinUtils()
    {
    }

    private static HanyuPinyinOutputFormat hanyuPinyinOutputFormat;

    static
    {
        hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
        hanyuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 获取词语的拼音首字母,大写表示
     * 如'中国' 返回Z
     *
     * @param word
     * @return
     */
    public static String getPinyin(String word)
    {
        if (StringUtils.isEmpty(word))
        {
            return "";
        }
        String[] pinyin = null;
        try
        {
            pinyin = PinyinHelper.toHanyuPinyinStringArray(word.charAt(0), hanyuPinyinOutputFormat);
        }
        catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination)
        {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        if (pinyin == null || pinyin.length <= 0)
        {
            return word.toUpperCase();
        }
        else
        {
            return pinyin[0].toUpperCase();
        }
    }

    public static List<String> getPinyinFull(String word)
    {
        if (StringUtils.isEmpty(word))
        {
            return null;
        }

        List<String[]> list = new ArrayList<String[]>();
        int len = word.length();
        for (int i = 0; i < len; i++)
        {
            String[] pinyin = null;
            try
            {
                pinyin = PinyinHelper.toHanyuPinyinStringArray(word.charAt(i), hanyuPinyinOutputFormat);
            }
            catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination)
            {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
            if (pinyin == null || pinyin.length <= 0)
            {
                continue;
            }
            else
            {
                list.add(pinyin);
            }
        }

        final List<String> out = PermutationUtils.fullPermutation(list);
        return out;
    }
}
