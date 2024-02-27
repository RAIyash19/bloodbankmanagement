package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.configuration.CustomUserDetail;
import com.example.demo.entity.DonorDetails;
import com.example.demo.entity.PatientDetails;
import com.example.demo.entity.RegistrationDetails;
import com.example.demo.repository.RegistrationDetailsRepository;
import com.example.demo.service.RegistrationDetailsService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService loginService;
	
	
	
	@GetMapping("/viewUserProfileDetails")//1
	public String viewProfileDetails(HttpSession session,Model model) {
		
		CustomUserDetail user = (CustomUserDetail) session.getAttribute("user");
		String e = (String) session.getAttribute("userEmail");
		
		String email = user.getEmail();
		List<RegistrationDetails> detail =  loginService.getProfileDetails(email);
		model.addAttribute("user", detail);
		return "userProfile";
		
	}
	
	@PostMapping("/updateUserDetails")//1
	public String updateProfile(@ModelAttribute("received") RegistrationDetails received, Model model) {
		 System.out.println("update");
		 System.out.println(received);
		 loginService.updateProfile(received);
		 model.addAttribute("userData", received);
		 model.addAttribute("userUpdate", "Details updated successfully");
		 List<RegistrationDetails> detail =  loginService.getProfileDetails(received.getEmail());
			model.addAttribute("user", detail);
		 return "userProfile";
	}
	
//	@GetMapping("/getDonationAndBloodCount/{email}")
//	@ResponseStatus(HttpStatus.OK)
//	@ResponseBody
//	public void getDonationAndBloodCount(@PathVariable("email") String email) {
//		
//		int bloodRequests = loginService.getBloodRequestsCount(email);
//		int donationRequests = loginService.getDonationCount(email);
//		
//	}
	
	@GetMapping("/viewBloodRequestDetails")//1
	public String viewBloodRequestsDetails(HttpSession session,Model model) {
//		if (session.getAttribute("userEmail") == null) {
//            // Session is valid, return the Thymeleaf template name for the user home page
//            return "userLogin";
//        }
//		
		String email = (String) session.getAttribute("userEmail");
		List<PatientDetails> list = loginService.getBloodRequestsDetails(email);
		model.addAttribute("bloodRequestHistory", list);
		return "userBloodRequestHistory";
	}
	
	@GetMapping("/viewDonateRequestDetails")//1------------------------------------------------------
	public String getDonateRequestsDerails(HttpSession session, Model model) {
//		if (session.getAttribute("userEmail") == null) {
//            // Session is valid, return the Thymeleaf template name for the user home page
//            return "userLogin";
//        }
		String email = (String) session.getAttribute("userEmail");
			List<DonorDetails> list = loginService.getDonateRequestsDetails(email);
			
			for(DonorDetails detail:list) {
				System.out.println(detail.toString());
			}
			model.addAttribute("donationRequestHistory", list);
			return "userDonationRequestHistory";
	}
	
	@GetMapping("/viewAcceptedBloodDonationCount/{email}")//1
	public String getBloodDonationCount(@PathVariable("email") String email) {
		 loginService.findBloodDonationsCount(email);
		 return null;
	}
	
	@GetMapping("/viewAcceptedBloodRequestCount/{email}") // acepted requests
	public String getBloodRequestCount(@PathVariable("email") String email) {
		 loginService.findBloodRequestsCount(email);
		 return null;
	}
	
	@PostMapping("/bloodDonationRequest")//1
	public String donateRequest(@ModelAttribute("received") DonorDetails received,HttpSession session, Model model) {
//		
		if (loginService.validateUserDetails(received))
		{
			model.addAttribute("donateMessage", "Fill profile details before going for donation");
			return "donationRequest";
		}
		
		int status = loginService.donateRequest(received);
		switch(status) {
		case 0:model.addAttribute("donateMessage", "Email not found");
		return "donationRequest";
		case 1:  //model.addAttribute("donateMessage", "You are eligible to donate, request needs to be accepted by admin");
		return "userHome";
		case 2:model.addAttribute("donateMessage", "you are not allowed to Donate Blood, age should be minimum 18 years");
		return "donationRequest";
		case 3:model.addAttribute("donateMessage","Older people are not allowed to donate age should be less than 65");
		return "donationRequest";
		case 4:
			model.addAttribute("donateMessage", "You can't make a request again without the last request getting verified");
			return "donationRequest";
		case 5:
			model.addAttribute("donateMessage", "Less than in 90 days period of time is not allowed to donate blood again");
			return "donationRequest";
			
		}
		System.out.println("donation request  "  + "  " + received.getEmail() + " " +  " " + received.getDateOfDonation());
		return "donationRequest";
	}
	
	@PostMapping("/bloodRequestSelf")  //1
	public String bloodRequestSelf(@ModelAttribute("received")  PatientDetails received,HttpSession session, Model model) {
		
		
//		System.out.println("inside self");
//		if (session.getAttribute("userEmail") == null) {
//            // Session is valid, return the Thymeleaf template name for the user home page
//            return "userLogin";
//        } 
		
		if (loginService.validateUserDetailsForPatient(received))
		{
			model.addAttribute("bloodRequestStatus", "Fill profile details before requesting blood");
			return "bloodRequest";
		}
		
		System.out.println(received.getEmail()+ " " + received.getBloodGroup() + " " + received.getBloodUnits());
//		return "userHome";
		int status = loginService.bloodRequestSelf(received);
		if (status==0) {
			System.out.println("You can't made blood request again until the last request being verified");
			model.addAttribute("bloodRequestStatus", "You can't made blood request again until the last request being verified");
		}
		else if (status ==1)
			model.addAttribute("bloodRequestStatus", "Not allowed to take blood more than 5 units");
		else if (status == 2)
			model.addAttribute("bloodRequestStatus", "There is no enough blood in the Inventory");
		else {
			model.addAttribute("bloodRequestStatus", "Blood is available, admin has to accept your request");
			return "bloodRequest";
		}
		return "bloodRequest";
	}
	

	@PostMapping("/bloodRequestOthers")  //1
	public String bloodRequestOthers(@ModelAttribute("received")  PatientDetails received,HttpSession session, Model model) {
		
//		System.out.println("inside others");
//		if (session.getAttribute("userEmail") == null) {
//            // Session is valid, return the Thymeleaf template name for the user home page
//            return "userLogin";
//        } 
		
		if (loginService.validateUserDetailsForPatient(received))
		{
			model.addAttribute("bloodRequestStatus", "Fill profile details before requesting blood");
			return "bloodRequest";
		}
		
		
		System.out.println("mmmm : " + received.getEmail());
		System.out.println(received.getEmail()+ " " + received.getBloodGroup() + " " + received.getBloodUnits());
//		return "userHome";
		int status = loginService.bloodRequestOthers(received);
		if (status==0) {
			System.out.println("You can't made blood request again until the last request being verified");
			model.addAttribute("bloodRequestStatus", "You can't made blood request again until the last request being verified");
		}
		else if (status ==1)
			model.addAttribute("bloodRequestStatus", "Not allowed to take blood more than 5 units");
		else if (status == 2)
			model.addAttribute("bloodRequestStatus", "There is no enough blood in the Inventory");
		else {
			model.addAttribute("bloodRequestStatus", "Blood is available, admin has to accept your request");
			
			return "bloodRequest";
		}
		return "bloodRequest";
	}
	
	@PostMapping("/deleteUser")
	public String deleteUser(HttpSession session, Model model) {
		System.out.println("inside deleteUser");
		
		String email = (String) session.getAttribute("userEmail");
		loginService.deleteUser(email);
		
		 return "redirect:/userLogin";
	}
	
	
	

}
