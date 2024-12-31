package ecommercewebModule.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/login")
	public ResponseEntity<?> login() {		
		return ResponseEntity.ok("Home page is visible");		
	}
	
}
