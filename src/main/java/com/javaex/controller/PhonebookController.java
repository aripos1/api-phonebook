package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaex.service.PhonebookService;
import com.javaex.util.JsonResult;
import com.javaex.vo.PersonVo;

@RestController
public class PhonebookController {

	@Autowired
	private PhonebookService phonebookService;

	@GetMapping("/api/persons")
	public List<PersonVo> getList() {

		List<PersonVo> personList = phonebookService.exePersonList();
		System.out.println("list");

		return personList;
	}

	@PostMapping("/api/persons")
	public String write(@RequestBody PersonVo personVo) {

//		int count = phonebookDao.insertPerson(personVo);
		phonebookService.exeWritePerson(personVo);
//		System.out.println(count);

		return "redirect:/";
	}

	@DeleteMapping("/api/persons/{no}")
	public JsonResult delete(@PathVariable(value = "no") int no) {

		int count = phonebookService.exeDeletePerson(no);

		if (count != 1) {
			return JsonResult.fail("해당번호가 없습니다.");

		} else {
			return JsonResult.success(count);
		}

	}

	// 특정 사용자 정보 조회 (GET 요청)
	@GetMapping("/api/persons/{no}") // 추가된 부분
	public JsonResult getPerson(@PathVariable(value = "no") int no) {
		
		PersonVo person = phonebookService.exeEditForm(no); // 특정 사용자 정보를 조회하는 메소드 호출

		if (person == null) {
			return JsonResult.fail("해당번호가 없습니다.");

		} else {
			return JsonResult.success(person);
		}
	}

	@PutMapping("/api/persons/{no}")
	public JsonResult update(@PathVariable(value = "no") int no, @RequestBody PersonVo personVo) {
		
		personVo.setPersonId(no); // PathVariable로 받은 no 값을 PersonVo에 설정
		int count = phonebookService.exeEditPerson(personVo);
		
		if (count != 1) {
			return JsonResult.fail("수정 실패");

		} else {
			return JsonResult.success(count);
		}

	}

}
