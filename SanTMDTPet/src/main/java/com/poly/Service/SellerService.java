package com.poly.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.Model.Seller;
import com.poly.Model.User;
import com.poly.Reponsitory.SellerRepository;
import com.poly.Reponsitory.UserRepository;
import com.poly.dto.SellerDTO;

@Service
public class SellerService {

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private UserService userService;
	@Autowired
	UserRepository userRepository;

	// Đăng ký seller mới (trạng thái mặc định là "PENDING")
	public void registerSeller(SellerDTO sellerDTO, int userId) {
		Seller seller = new Seller();
		seller.setShopName(sellerDTO.getShopName());
		seller.setAvtShop(sellerDTO.getAvtShop());
		seller.setBackround(sellerDTO.getBackround());
		seller.setTypeBusiness(sellerDTO.getTypeBusiness());
		seller.setTaxCode(sellerDTO.getTaxCode());
		seller.setCccdCmnd(sellerDTO.getCccdCmnd());
		seller.setFrontCCCD(sellerDTO.getFrontCCCD());
		seller.setBackCCCD(sellerDTO.getBackCCCD());
		seller.setStatus("PENDING"); // Trạng thái ban đầu là "PENDING"

		// Tìm user theo userId và thiết lập quan hệ với seller
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			seller.setUser(user); // Thiết lập user cho seller
			sellerRepository.save(seller);
		} else {
			throw new RuntimeException("User not found with ID: " + userId);
		}
	}

	// Phê duyệt seller
	public void approveSeller(int sellerId) {
		Optional<Seller> optionalSeller = sellerRepository.findById(sellerId);
		if (optionalSeller.isPresent()) {
			Seller seller = optionalSeller.get();
			seller.setStatus("APPROVED"); // Cập nhật trạng thái thành "APPROVED"
			sellerRepository.save(seller);

			// Sau khi phê duyệt seller, cập nhật role của user
			userService.updateUserRoleToSeller(seller.getUser().getUsersId());
		} else {
			throw new RuntimeException("Seller not found with ID: " + sellerId);
		}
	}
}
