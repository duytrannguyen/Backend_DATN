package com.poly.Controller.Client;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.poly.Mapper.UserDTOMapper;
import com.poly.Mapper.UserTokenResponseMapper;
import com.poly.Model.User;
import com.poly.Service.UserService;
import com.poly.Service.API.JwtService;
import com.poly.dto.request.UserRequest;
import com.poly.dto.response.TokenResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class User_ControllerAPI {

	private final UserService userService;

	private final UserTokenResponseMapper userTokenResponseMapper;

	private final JwtService jwtService;

	private final UserDTOMapper userDTOMapper;
	
	private final UserDetailsService userDetailsService;
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
	    // Gọi service để xác thực người dùng
	    User user = userService.login(userRequest);
	    
	    if (user != null) {
	        // Tạo token JWT sau khi xác thực
	        String token = jwtService.GenerateToken(user.getUsername()); // Lấy role từ user
	        
	        // Tạo response chứa thông tin token và người dùng
	        TokenResponse tokenResponse = userTokenResponseMapper.mapToTokenResponse(user, token);
	        
	        // Trả về token và thông tin người dùng, không cần dùng session
	        System.out.println("login " + user.getUsername());
	        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Username or password is incorrect", HttpStatus.BAD_REQUEST);
	    }
	}



	@PostMapping(value = "/register")
	public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
		User register = userService.register(userRequest);
		if (register != null) {
			return new ResponseEntity<>("Register Success", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Register Failed", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/uploadTest", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> uploadTest(@RequestParam("file") MultipartFile file) {
		return new ResponseEntity<>(file.getOriginalFilename(), HttpStatus.OK);
	}

	@PostMapping(value = "/forgot-Password/{email}")
	public ResponseEntity<?> forgotPassword(@PathVariable String email) {
		boolean forgotPassword = userService.forgotPassword(email);
		if (forgotPassword) {
			return new ResponseEntity<>("Gửi mail thành công", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Gửi mail thất bại", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getTokenGoogle")
	public ResponseEntity<?> getTokenGoogle(@RequestParam String code) {
		String query = code.substring(code.indexOf('?') + 1);
		String[] params = query.split("&");
		String coded = null;
		for (String param : params) {
			if (param.startsWith("code=")) {
				coded = param.substring(param.indexOf('=') + 1);
				break;
			}
		}

		String tokenGoogle = userService.getTokenGoogle(coded);
		if (tokenGoogle != null) {
			return new ResponseEntity<>(tokenGoogle, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error Get Token", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getGoogleUserInfo")
	public ResponseEntity<?> getGoogleUserInfo(@RequestParam String accessToken) {
		User user = userService.GoogleAccountGetUserInfo(accessToken);
		if (user != null) {
			String token = jwtService.GenerateToken(user.getUsername());
			TokenResponse tokenResponse = userTokenResponseMapper.mapToTokenResponse(user, token);
			return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error Get User Info", HttpStatus.BAD_REQUEST);
		}
	}
}
