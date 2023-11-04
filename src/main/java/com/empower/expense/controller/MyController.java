package com.empower.expense.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.empower.expense.model.Users;
import com.empower.expense.service.EmailServiceImpl;
import com.empower.expense.service.UserService;
import com.empower.expense.util.AuthRequest;
import com.empower.expense.util.JwtUtil;
import com.empower.expense.util.MyToken;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class MyController {
	@Autowired
    private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager am;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@PostMapping("/login")
	public MyToken validateLogin(@RequestBody AuthRequest ar)
	{
		System.out.println(ar.getEmailId()+":"+ar.getPassword());
		MyToken myToken=new MyToken();
		try
		{
			am.authenticate(new UsernamePasswordAuthenticationToken(ar.getEmailId(), ar.getPassword()));
			System.out.println("Line 41");
			String token = jwtUtil.generateToken(ar.getEmailId());
			System.out.println("Line 43");
//			myToken.token=token;
			myToken.setToken(token);
		}catch(Exception ex)
		{
			System.out.println("Inside catch block");
//			myToken.token="Login failed";
			myToken.setToken("Login failed");
		}
		return myToken;
		
	}
	
	@PostMapping("/signup")
  public Users signup(@RequestBody Users users, HttpSession session) {
      return userService.save(users);
  }
	
//	@PostMapping("/signup")
//    public Users signup(@RequestBody Users users, HttpSession session) {
//        // Generate and store OTP
//        int noOfDigits = 4;
//        String otp = "";
//        for (int i = 0; i < noOfDigits; i++) {
//            otp += (int) (Math.random() * 10);
//        }
//        session.setAttribute("otp", otp);
//
//        // Send OTP to user's email
//        emailService.sendSimpleMessage(users.getEmailId(), "Otp", otp + " is the OTP to signup to our website. Never share OTP with anybody");
//
//        // Register the user (save to the database)
//        return userService.save(users);
//    }
	
//	@PostMapping("/validateOtp")
//    public ResponseEntity<String> validateOtp(@RequestParam("otp") String otp, HttpSession session) {
//        // Validate OTP
//        String originalOtp = (String) session.getAttribute("otp");
//        if (originalOtp == null) {
//            return ResponseEntity.badRequest().body("No OTP session found. Please register again.");
//        } else if (otp.equals(originalOtp)) {
//            // OTP is valid - you can proceed with login
//            // Clear the OTP session attribute to enhance security
//            session.removeAttribute("otp");
//            return ResponseEntity.ok("Otp is valid");
//        } else {
//            return ResponseEntity.badRequest().body("Entered OTP is invalid.");
//        }
//    }
//	@PostMapping("/validateOtp")
//	@ResponseBody
//	public ResponseEntity<String> validateOtp(@RequestBody String otp, HttpSession session) {
//	    // Validate OTP
//	    String originalOtp = (String) session.getAttribute("otp");
//	    if (originalOtp == null) {
//	        return ResponseEntity.badRequest().body("No OTP session found. Please register again.");
//	    } else if (otp.equals(originalOtp)) {
//	        // OTP is valid - you can proceed with login
//	        // Clear the OTP session attribute to enhance security
//	        session.removeAttribute("otp");
//	        return ResponseEntity.ok("Otp is valid");
//	    } else {
//	        return ResponseEntity.badRequest().body("Entered OTP is invalid.");
//	    }
//	}

	
	@GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
	
    @GetMapping("/byName/{name}")
    public ResponseEntity<Users> getUserByName(@PathVariable String name) {
        Users users = userService.findByName(name);
        if (users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/byEmailId/{emailId}")
    public ResponseEntity<Users> getUserByEmailId(@PathVariable String emailId) {
        Optional<Users> users = userService.findByEmailId(emailId);
        if (users.isPresent()) {
            return new ResponseEntity<>(users.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/byId/{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable Long userId) {
        Users users = userService.findById(userId);
        if (users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users users) {
        Users createdUser = userService.save(users);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<Users> updateUser(@PathVariable Long userId, @RequestBody Users users) {
        Users existingUser = userService.findById(userId);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        users.setUserId(userId);
        Users updatedUser = userService.save(users);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long userId) {
    	Users users = userService.findById(userId);
        if (users != null) {
            userService.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
