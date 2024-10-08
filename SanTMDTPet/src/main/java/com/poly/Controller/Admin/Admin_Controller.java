package com.poly.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class Admin_Controller {
	@RequestMapping("/index1")
	public String Login() {
		return "indexAdmin";
	}
}
