package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.PhonebookService;
import com.javaex.vo.PersonVo;

@Controller
public class PhonebookController {
	@Autowired
	private PhonebookService phonebookService;

	@ResponseBody
	@RequestMapping(value="/api/persons", method = RequestMethod.GET)
	public List<PersonVo> getList() {
		
		List<PersonVo> personList = phonebookService.exePersonList();
		System.out.println("list");
		
		return personList;
	}
	@ResponseBody
	@RequestMapping(value = "/api/persons", method = RequestMethod.POST)
	public String write(@RequestBody PersonVo personVo) {
	

//		int count = phonebookDao.insertPerson(personVo);
		phonebookService.exeWritePerson(personVo);
//		System.out.println(count);

		return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/persons/{no}", method = RequestMethod.DELETE)
	public int delete(@PathVariable(value="no") int no) {
	

//		int count = phonebookDao.insertPerson(personVo);
		int count = phonebookService.exeDeletePerson(no);
//		System.out.println(count);

		return count;
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/persons/{no}", method = RequestMethod.PUT)
	 public int update(@PathVariable(value = "no") int no, @RequestBody PersonVo personVo) {
        personVo.setPersonId(no); // PathVariable로 받은 no 값을 PersonVo에 설정
        int count = phonebookService.exeEditPerson(personVo);
        return count;
	}
	
    // 특정 사용자 정보 조회 (GET 요청)
    @ResponseBody
    @RequestMapping(value = "/api/persons/{no}", method = RequestMethod.GET)  // 추가된 부분
    public PersonVo getPerson(@PathVariable(value="no") int no) {
        PersonVo person = phonebookService.exeEditForm(no);  // 특정 사용자 정보를 조회하는 메소드 호출
        return person;
    }
}
