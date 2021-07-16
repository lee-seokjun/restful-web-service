package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminUserController {
    private final UserDaoService service;

    public AdminUserController(UserDaoService service){
        this.service= service;
    }

    @GetMapping("/users")
    public MappingJacksonValue Alliusers(){
        List<User> all = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);
        MappingJacksonValue mapping= new MappingJacksonValue(all);
        mapping.setFilters(filters);


        return mapping;
    }
    // /admin/users/1 -> admin/v1/users/1
   //@GetMapping("/v1/users/{id}")
    //@GetMapping(value="/users/{id}/",params="version=1")
   //@GetMapping(value="/users/{id}/",headers="X-API-VERSION=1")
    @GetMapping(value="/users/{id}/",produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue getuserv1(@PathVariable int id){
        User one = service.findOne(id);
        if (one ==null){
            throw new UserNotFoundException(String.format(("ID[%s]"),id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);
        MappingJacksonValue mapping= new MappingJacksonValue(one);
        mapping.setFilters(filters);
        return mapping;
    }
    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value="/users/{id}/",params="version=2")
    //@GetMapping(value="/users/{id}/",headers="X-API-VERSION=2")
    @GetMapping(value="/users/{id}/",produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue getuserv2(@PathVariable int id){
        User one = service.findOne(id);
        if (one ==null){
            throw new UserNotFoundException(String.format(("ID[%s]"),id));
        }
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(one,userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter);
        MappingJacksonValue mapping= new MappingJacksonValue(userV2);
        mapping.setFilters(filters);
        return mapping;
    }

}
