package com.rossie.untitled.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DataModel extends RepresentationModel<DataModel> {
    private int id;
    private List<Integer> list;
    private List<Integer> sortedList;
    private String sortAlgorithm;
}
