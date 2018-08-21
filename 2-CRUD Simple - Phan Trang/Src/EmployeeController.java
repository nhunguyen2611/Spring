package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
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
	
	
	
	@GetMapping("/")
	public String home() {
		return "redirect:/employee";
	}
	
	
	@GetMapping("/employee")
	public String index(Model model,HttpServletRequest request
			,RedirectAttributes redirect) {
		request.getSession().setAttribute("employeelist", null);
		
		if(model.asMap().get("success") != null)
			redirect.addFlashAttribute("success",model.asMap().get("success").toString());
		return "redirect:/employee/page/1";
	}
	
	@GetMapping("/employee/page/{pageNumber}")
	public String showEmployeePage(HttpServletRequest request, 
			@PathVariable int pageNumber, Model model) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("employeelist");
		int pagesize = 5;
		List<Employee> list =(List<Employee>) employeeService.getAllEmp();
		System.out.println(list.size());
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("employeelist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/employee/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("employees", pages);

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
	
	// Controler cua Phan trang 
	
	//-- phương thức phân trang vào class EmployeeController:
	
}
