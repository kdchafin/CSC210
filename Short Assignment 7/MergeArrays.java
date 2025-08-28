
package com.gradescope.mymerge;

import java.util.ArrayList;
import java.util.Arrays;

public class MergeArrays {

    public static <T> ArrayList<T> merge(T[] array1, T[] array2) {
        ArrayList<T> mergedList = new ArrayList<>();
        int minLength = Math.min(array1.length, array2.length);

        // add from smaller array first
        if (array1.length < array2.length) {
            for (int i = 0; i < minLength; i++) {
                mergedList.add(array1[i]);
                mergedList.add(array2[i]);
            }
        }
        else {
            for (int i = 0; i < minLength; i++) {
                mergedList.add(array2[i]);
                mergedList.add(array1[i]);
            }
        }

        // add remained of bigger array
        if (array1.length > minLength) {
            mergedList.addAll(Arrays.asList(array1).subList(minLength, array1.length));
        } else {
            mergedList.addAll(Arrays.asList(array2).subList(minLength, array2.length));
        }

        return mergedList;
    }
}