package com.rossie.untitled.service;

import com.rossie.untitled.model.DataModel;
import com.rossie.untitled.model.SortRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DataService {
    private final Map<Integer, DataModel> dataStore = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public List<DataModel> getAllData(){
        return new ArrayList<>(dataStore.values());
    }

    public DataModel getDataById(int id){
        return dataStore.get(id);
    }

    public List<DataModel> getDataByAlgorithm(String algorithm){
        List<DataModel> result = new ArrayList<>();
        for (DataModel data : dataStore.values()){
            if (algorithm.equals(data.getSortAlgorithm())){
                result.add(data);
            }
        }
        return result;
    }

    public DataModel createData(List<Integer> list){
        int id = idCounter.getAndIncrement();
        DataModel data = new DataModel();
        data.setId(id);
        data.setList(list);
        data.setSortAlgorithm(null);
        dataStore.put(id, data);
        System.out.println("............................." + data);
        return data;
    }

    public void deleteData(int id){
        if (!dataStore.containsKey(id)){
            throw new NoSuchElementException("DataModel with ID "+ id + "not found");
        }
        dataStore.remove(id);
    }

    public DataModel sort(SortRequest sortRequest) {
        DataModel data = dataStore.get(sortRequest.getId());
        if (data == null)
            throw new NoSuchElementException("DataModel not found");

        if (sortRequest.getAlgorithm() == null)
            throw new IllegalArgumentException("Invalid Sorting Algorithm");

        SortAlgorithm sorter = switch (sortRequest.getAlgorithm().toLowerCase()) {
            case "quicksort" -> new QuickSort();
            case "heapsort" -> new HeapSort();
            case "mergesort" -> new MergeSort();
            case "radixsort" -> new RadixSort();
            case "bucketsort" -> new BucketSort();
            default -> throw new IllegalArgumentException("Unsupported sorting algorithm");
        };
        data.setSortAlgorithm(sortRequest.getAlgorithm());
        System.out.println("___________________________" + data);
        List<Integer> list = data.getList();
        System.out.println("**********************************" + list);
        data.setSortedList(sorter.sort(list));
        System.out.println("////////////////////////" + data);
        return data;
    }
}
