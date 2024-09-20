package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhonebookDao {

	@Autowired
	private SqlSession sqlSession;

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/phonebook_db";
	private String id = "phonebook";
	private String pw = "phonebook";

	// 생성자
	// 기본생성자 사용(그래서 생략)

	// 메소드 gs
	// 필드값을 외부에서 사용하면 안됨(그래서 생략)

	// 메소드 일반
	// DB연결 메소드
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원정리 메소드
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public int deletePerson(int no) {
		System.out.println("phonebook.delete");
		int count = sqlSession.delete("phonebook.delete", no);

		return count;
	}

	// 사랑 정보 수정하기 1명
	public int updatePerson(PersonVo personVo) {
		int count = sqlSession.update("phonebook.update", personVo);
		return count;
	}

	// 사람 1명 정보 가져오기
	public PersonVo getPersonOne(int no) {

		PersonVo personVo = sqlSession.selectOne("phonebook.selectOne", no);

		return personVo;
	}

	public Map<String, Object> getPersonOne2(int no) {

//		PersonVo personVo = sqlSession.selectOne("phonebook.selectOne", no);
		Map<String, Object> personMap = sqlSession.selectOne("phonebook.selectOneMap", no);
		System.out.println(personMap);

		return personMap;
	}

	// 사람정보 저장
	public int insertPerson(PersonVo personVo) {

		int count = sqlSession.insert("phonebook.insert", personVo);

		return count;

	}

	public int insertPerson2(PersonVo personVo) {

//		int count = sqlSession.insert("phonebook.insert", personVo);

		Map<String, String> pMap = new HashMap<String, String>();

		int count = sqlSession.insert("phonebook.insert2", pMap);

		return count;

	}

	// 리스트 가져오기
	public List<PersonVo> getPersonList() {

		List<PersonVo> personList = sqlSession.selectList("phonebook.selectList");

		return personList;

	}

}
