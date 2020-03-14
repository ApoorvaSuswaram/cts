package com.cts.start.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.cts.start.email.EmailService;
import com.cts.start.pojo.User;

@RestController
@CrossOrigin(origins="*")  
@RequestMapping(value="/Userportal")
public class UserController {
	@Autowired
	private com.cts.start.dao.UserRepository userRepository;

	@RequestMapping("/getAllUsers")
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	@Autowired
	 private EmailService emailService;

	 @PostMapping("/saveUser")

	 public User saveUser(@RequestBody User user) {

	 System.out.println(user);

	 userRepository.save(user);



	 StringBuffer mailContain= new StringBuffer();

	 mailContain.append("Hi "+user.getUsername()+"\n");	

	 mailContain.append("Please Click on Below Click to Confirm Your Email With Us\n");

	 mailContain.append("<a href='http://localhost:8088/Userportal/confirmEmail/"+user.getEmail()+">Click</a>\n");

	 mailContain.append("Thanks And Regards\n CTS Participant\n");

	 emailService.sendMail(user.getEmail(),"Email Confirmation", mailContain.toString());

	    //emailService.sendPreConfiguredMail("Ho ho ho");
	 return user;

	 }
	

	@PutMapping("/updateuser/{username}")
	public User updateUser(@RequestBody User user, @PathVariable("username") String username) {
		user.setUsername(username);
		System.out.println(user);
		userRepository.save(user);
		return user;
	}

	@DeleteMapping("/deleteuser/{username}")
	public User deleteUser(@RequestBody User user, @PathVariable("username") String username) {
		System.out.println(user);
		userRepository.deleteById(username);
		return user;
	}
	@GetMapping("/findOneInAll6/{username}")
	public User findoneinall(@PathVariable("username") String username)
	{
		Optional<User> user = userRepository.findById(username);
		return user.get();
	}
	@GetMapping("/confirmEmail/{emailId}")

	 public User confirmEmail(@PathVariable("emailId") String emailId)

	 {

	 Optional<User> user= userRepository.findByEmail(emailId);

	 if(user!=null && user.get()!=null)

	 {

	  User u=user.get();

	  u.setConfirmed("yes");

	  userRepository.save(u);

	  return u;



	 }

	 return user.get();

	 }
	@GetMapping("/findByUsernameAndPassword/{username}/{password}")

	 public User findByUsernameAndPassword(@PathVariable("username") String username,@PathVariable("password") String password)

	 {

	 User user= userRepository.findByUsernameAndPasswordAndConfirmed(username, password,"yes");

	 return user;

	 }
	
}
