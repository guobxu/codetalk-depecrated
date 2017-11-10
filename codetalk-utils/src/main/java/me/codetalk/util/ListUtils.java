package me.codetalk.util;

import java.util.List;

/**
 * Created by guobxu on 17/7/2017.
 */
public class ListUtils {

    public static final String concat(List<String> list, String sep) {
        StringBuffer buf = new StringBuffer();

        for(int i = 0, size = list.size(); i < size; i++) {
            buf.append(list.get(i));

            if(i != size - 1) {
                buf.append(sep);
            }
        }

        return buf.toString();
    }
    
    public static final String concat(String[] strarr, String sep) {
        StringBuffer buf = new StringBuffer();

        for(int i = 0, size = strarr.length; i < size; i++) {
            buf.append(strarr[i]);

            if(i != size - 1) {
                buf.append(sep);
            }
        }

        return buf.toString();
    }

}
