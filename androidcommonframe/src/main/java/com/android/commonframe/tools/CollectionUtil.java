package com.android.commonframe.tools;

import java.util.Collection;

/**
 * 集合判断工具类
 * Created by feilong.guo on 16/11/11.
 */

public class CollectionUtil {
    private CollectionUtil() {
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty() || collection.size() == 0;
    }
}
