package com.poly.controllerAPI;

import com.poly.dto.mapper.AddressDTOMapper;
import com.poly.dto.mapper.UserDTOMapper;
import com.poly.dto.request.UserRequest;
import com.poly.dto.response.AddressReponse;
import com.poly.dto.response.UserResponse;
import com.poly.model.Address;
import com.poly.model.User;
import com.poly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class User_Controller_V1_API {

    private final UserService userService;

    private final UserDTOMapper userDTOMapper;

    private final AddressDTOMapper addressDTOMapper;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file, @RequestPart("id") Integer id) {
        User uploadFile = userService.uploadFile(file, id);
        if (uploadFile != null) {
            UserResponse userResponse = userDTOMapper.apply(uploadFile);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Upload Failed", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/userProfile")
    public ResponseEntity<?> getUserByUsername() {
        User userByHolder = userService.getUserByHolder();
        if (userByHolder != null) {
            UserResponse userResponse = userDTOMapper.apply(userByHolder);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User Not Exist", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addAddress/{id}")
    public ResponseEntity<?> addAddress(@PathVariable Integer id, @RequestBody UserRequest userRequest) {
        Address address = userService.addAddress(id, userRequest);
        if (address != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @GetMapping("/get-addresses/{id}")
    public ResponseEntity<?> getAddresses(@PathVariable Integer id) {
        List<Address> address = userService.getAddress(id);
        if (address != null) {
            List<AddressReponse> addressReponses = address.stream()
                    .map(addressDTOMapper)
                    .toList();
            return new ResponseEntity<>(addressReponses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteAddress/{id}/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer id, @PathVariable Integer addressId) {
        Address address = userService.deleteAddress(id, addressId);
        if (address != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @PutMapping(value = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestPart UserRequest userRequest, @RequestPart("profilePicture") MultipartFile file) {
        User edit = userService.edit(id, userRequest, file);
        if (edit != null) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @GetMapping("/load-image/{fileName}")
    public ResponseEntity<?> loadImage(@PathVariable String fileName, @RequestHeader HttpHeaders httpHeaders) {
        return new ResponseEntity<>(userService.loadImage(fileName, httpHeaders), HttpStatus.OK);
    }

    @PutMapping(value = "/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Integer id, @RequestBody UserRequest userRequest) {
        User changePassword = userService.changePassword(id, userRequest);
        if (changePassword != null) {
            return new ResponseEntity<>("Change Password Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Change Password Failed", HttpStatus.BAD_REQUEST);
        }
    }
}
