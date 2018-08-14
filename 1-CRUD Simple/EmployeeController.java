package com.example.demo;

import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;


// Xu ly hanh dong dua tren duong dan cua hanh dong
@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	// In tat ca cac phan tu cua bang Employee
	@GetMapping("/employee")
	public String index(Model model) {
		model.addAttribute("employees", employeeService.getAllEmp());
		return "list";
	}
	
	// Tim kiem theo ten
	@GetMapping("/employee/search")
	public String search(@RequestParam("s") String s, Model model) {
		if (s.equals("")) {
			return "redirect:/employee";
		}

		model.addAttribute("employees", employeeService.search(s));
		return "list";
	}
	
	//Edit phan tu Employee
	//Dung function Save de save Edit
	@GetMapping("/employee/{id}/edit")
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("employee", employeeService.findOne(id));
		return "form";
	}
	
	// Tao phan tu moi
	@PostMapping("/employee/save")
	public String save(@Valid Employee employee, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "form";
		}
		employeeService.save(employee);
		redirect.addFlashAttribute("success", "Saved employee successfully!");
		return "redirect:/employee";
	}
	
	// Xoa phan tu
	@GetMapping("/employee/{id}/delete")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		employeeService.delete(id);
		redirect.addFlashAttribute("success", "Deleted employee successfully!");
		return "redirect:/employee";
	}
	
	// Chuyen duong dan sang form de tao doi tuong moi
	@GetMapping("/employee/create")
	public String create(Model model) {
		model.addAttribute("employee", new Employee());
		return "form";
	}
	
	

}
