package com.rossie.untitled.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BucketSort implements SortAlgorithm {
    @Override
    public List<Integer> sort(List<Integer> data) {
        List<Integer> sortedList = new ArrayList<>();
        int numberOfBuckets = (int) Math.sqrt(data.size());
        List<Integer>[] buckets = new ArrayList[numberOfBuckets];

        for (int i = 0; i < numberOfBuckets; i++)
            buckets[i] = new ArrayList<>();

        int maximum = Collections.max(data);
        for (int num:data){
            int index = (num * numberOfBuckets) / (maximum + 1);
            buckets[index].add(num);
        }

        for(List<Integer> bucket : buckets){
            Collections.sort(bucket);
            sortedList.addAll(bucket);
        }

        return sortedList;
    }
}
