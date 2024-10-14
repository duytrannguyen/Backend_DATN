package com.poly.Controller.Client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.Model.Seller;
import com.poly.Reponsitory.SellerRepository;
import com.poly.Service.SellerService;
import com.poly.dto.SellerDTO;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/home")
public class Client_SellerController {
	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	SellerService sellerService;

	@GetMapping("/seller/all")
	public List<Seller> getSellerAList() {
		return sellerRepository.findAll();
	}

	// Đăng ký seller
	@PostMapping("/seller/register")
	public ResponseEntity<String> registerSeller(@RequestBody SellerDTO sellerDTO, @RequestParam int userId) {
		try {
			sellerService.registerSeller(sellerDTO, userId);
			return ResponseEntity.ok("Đăng ký seller thành công! Đợi admin phê duyệt.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đăng ký thất bại!");
		}
	}

}
