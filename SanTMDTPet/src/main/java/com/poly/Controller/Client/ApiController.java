package com.poly.Controller.Client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.poly.Model.Address;
import com.poly.Model.User;
import com.poly.Service.UserService;
import com.poly.dto.request.UserRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;


    @GetMapping("/api/provinces")
    public ResponseEntity<?> getProvinces() {
        return new ResponseEntity<>(userService.fetchProvinces(), HttpStatus.OK);
    }

    @GetMapping("/api/districts/{id}")
    public ResponseEntity<?> getDistricts(@PathVariable String id) {
        return new ResponseEntity<>(userService.fetchDistricts(id), HttpStatus.OK);
    }

    @GetMapping("/api/commune/{id}")
    public ResponseEntity<?> getCommunes(@PathVariable String id) {
        return new ResponseEntity<>(userService.fetchCommunes(id), HttpStatus.OK);
    }

    @PostMapping(value = "/api/register", consumes = "multipart/form-data" )
    public ResponseEntity<?> register(@RequestPart UserRequest userRequest, @RequestPart("profilePicture") MultipartFile file) {
        User register = userService.register(userRequest, file);
        if (register != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/api/edit/{id}",consumes = "multipart/form-data")
    public ResponseEntity<?> edit(@PathVariable Integer id,@RequestPart UserRequest userRequest, @RequestPart("profilePicture") MultipartFile file){
        User edit = userService.edit(id, userRequest, file);
        if (edit != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @PostMapping("/api/addAddress/{id}")
    public ResponseEntity<?> addAddress(@PathVariable Integer id, @RequestBody UserRequest userRequest){
        Address address = userService.addAddress(id, userRequest);
        if (address != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @DeleteMapping("/api/deleteAddress/{id}/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer id, @PathVariable Integer addressId){
        Address address = userService.deleteAddress(id, addressId);
        if (address != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
