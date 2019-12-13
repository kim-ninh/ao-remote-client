package com.ninhhk.aoremote.Utils;

import java.util.Iterator;
import java.util.List;

public class ListUtils {
    public static <T> void removeIf(List<T> list, Condition<T> condition) {

        for (Iterator<T> iterator = list.iterator(); iterator.hasNext(); ) {
            T item = iterator.next();
            if (condition.isMatch(item)) {
                iterator.remove();
            }
        }
    }

    public interface Condition<T> {
        boolean isMatch(T item);
    }
}
