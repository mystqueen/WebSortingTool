package com.rossie.untitled;

import com.rossie.untitled.controller.Controller;
import com.rossie.untitled.model.DataModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class HateoasGenerator {
    public void addHateoasLinks(DataModel data){
        Link getAllDataLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(Controller.class).getAllData()).withRel("all-datasets");
        data.add(getAllDataLink);
    }
}
