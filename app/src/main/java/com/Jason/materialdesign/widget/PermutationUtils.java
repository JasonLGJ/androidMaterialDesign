package com.Jason.materialdesign.widget;

import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (c) 2003,成都天奥信息科技有限公司
 * All rights reserved.

 * 功能描述：全排列工具类: 用于生成全-排列
 *
 * 编写人：李广金
 * 开始日期：2020.03.14
 */
public final class PermutationUtils
{
    private PermutationUtils()
    {
    }

    /**
     * 按顺序全排列
     *
     * @param input {a,b} {1,2}
     * @return a1, a2, b1, b2
     */
    public static List<String> fullPermutation(List<String[]> input)
    {
        List<String> list = new ArrayList<String>();
        if (input == null || input.isEmpty())
        {
            return list;
        }
        if (input.size() == 1)
        {
            final List<String> stringList = doFullPermutation("", input);
            if (stringList != null)
            {
                list.addAll(stringList);
            }
            return list;
        }
        String[] first = input.get(0);

        List<String[]> sub = input.subList(1, input.size());
        for (String word : first)
        {
            final List<String> stringList = doFullPermutation(word, sub);
            if (stringList != null)
            {
                list.addAll(stringList);
            }
        }

        return list;
    }

    private static List<String> doFullPermutation(String word, List<String[]> sub)
    {
        if (sub.size() == 1)
        {
            return doSinglePermutation(word, sub.get(0));
        }
        else
        {
            List<String> list = fullPermutation(sub);
            return addPrefix(word, list);
        }
    }

    private static List<String> addPrefix(String word, List<String> list)
    {
        List<String> ret = new ArrayList<String>(list.size());
        for (String s : list)
        {
            ret.add(word + s);
        }
        return ret;
    }

    private static List<String> doSinglePermutation(String word, String[] sub)
    {
        List<String> list = new ArrayList<String>();
        for (String s : sub)
        {
            list.add(word + s);
        }

        return list;
    }
}
