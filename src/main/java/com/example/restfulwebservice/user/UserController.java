package com.example.restfulwebservice.user;

import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private final UserDaoService service;

    public UserController(UserDaoService service){
        this.service= service;
    }

    @GetMapping("/users")
    public List<User> Allusers(){
        return service.findAll();
    }
    /*
    @GetMapping("/users/{id}")

    public User getuser(@PathVariable int id){
        User one = service.findOne(id);
        if (one ==null){
            throw new UserNotFoundException(String.format(("ID[%s]"),id));
        }

        //HATEOAS
        EntityModel<User> model = new EntityModel<>(one);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).Allusers());

        model.add(linkTo.withRel("all-userse"));
        return one;
    }*/
    @GetMapping("/users/{id}")
    public EntityModel getuser(@PathVariable int id){
        User one = service.findOne(id);
        if (one ==null){
            throw new UserNotFoundException(String.format(("ID[%s]"),id));
        }

        //HATEOAS
        EntityModel<User> model = new EntityModel<>(one);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).Allusers());
        model.add(linkTo.withRel("all-userse"));
        //Allusers Method를 all-users 라는 link로 만듬
        return model;
    }
    @PostMapping("/users")
    public ResponseEntity<User> addUser( @RequestBody @Valid User user){
        User saveuser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveuser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);
        if (user ==null){
            throw new UserNotFoundException(String.format(("ID[%s]"),id));
        }

    }

}
