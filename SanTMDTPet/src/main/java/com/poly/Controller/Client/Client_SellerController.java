package com.poly.Controller.Client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.Model.Seller;
import com.poly.Reponsitory.SellerRepository;

import jakarta.websocket.server.PathParam;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/home")
public class Client_SellerController {
	@Autowired
	SellerRepository sellerRepository;

	@GetMapping("/seller/all")
	public List<Seller> getSellerAList() {
		return sellerRepository.findAll();
	}
	
//	@GetMapping("/seller/staus")
//	public List<Seller> getSellerById(@PathParam int ){
//		
//	}
}
