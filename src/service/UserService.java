package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.Controller;
import dao.UserDao;
import util.ScanUtil;
import util.View;

public class UserService {

	private UserService() {}
	private static UserService instance;
	public static UserService getInstance() {
		if(instance == null) {
			instance = new UserService();
		}
		return instance;
	}
	private UserDao userDao = UserDao.getInstance();
	
	public int login() {			//로그인
		System.out.println("============ 로그인 ============");
		System.out.print("아이디> ");
		String userId = ScanUtil.nextLine();
		System.out.print("비밀번호> ");
		String password = ScanUtil.nextLine();
		
		Map<String, Object> user = userDao.selectUser(userId, password);
		
		if(user == null) {
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		} else{
			System.out.println("로그인 성공");
			
			Controller.loginUser = user;
			Controller.type = Integer.parseInt(Controller.loginUser.get("TYPE").toString());
			int tp = Integer.parseInt(Controller.loginUser.get("TYPE").toString());	//Object를 int로 형변환

			if(tp == 0) {
				return View.M_HOME;
			} else {
				return View.U_HOME;
			}
		}
		return View.LOGIN;
	}
	
	
	public int join() {				//회원가입
		String id_regex = "[a-z0-9]{5,10}";			//ID 유효성 검사
		Pattern id_p = Pattern.compile(id_regex);
		String tel_regex = "^0\\d{1,3}-\\d{3,4}-\\d{4}";	//전화번호 유효성 검사
		Pattern tel_p = Pattern.compile(tel_regex);
		String pw_regex = "[a-z0-9~!]{7,20}";		//비밀번호 유효성 검사
		Pattern pw_p = Pattern.compile(pw_regex);
		String nm_regex = "\\W{2,4}";			//이름 유효성 검사
		Pattern nm_p = Pattern.compile(nm_regex);
		
		System.out.println("<아이디는 영문자,숫자로 5~10이내로 입력>");
		System.out.print("아이디> ");
		String userId = ScanUtil.nextLine();
		Matcher id_m = id_p.matcher(userId);
		while(id_m.matches() == false) {
			System.out.print("형식에 맞게 입력하시오> ");
			userId = ScanUtil.nextLine();
			id_m = id_p.matcher(userId);
		}
		
		List<Map<String, Object>> idlist = userDao.selectidlist();
		for(int i = 0; i < idlist.size(); i++) {
			if(userId.equals(idlist.get(i).get("ID").toString())){
				System.out.println("중복되는 아이디입니다.");
				return View.JOIN;	
			}
		}
		
		
		System.out.println("<비밀번호는 소문자,숫자,특수문자(~ , !)로 7~20 이내로 입력>");
		System.out.print("비밀번호> ");
		String password = ScanUtil.nextLine();
		Matcher pw_m = pw_p.matcher(password);
		while(pw_m.matches() == false) {
			System.out.println("형식에 맞게 입력하시오> ");
			password = ScanUtil.nextLine();
			pw_m = pw_p.matcher(password);
		}
		System.out.print("이름> ");
		String userName = ScanUtil.nextLine();
		Matcher nm_m = nm_p.matcher(userName);
		while(nm_m.matches() == false) {
			System.out.print("형식에 맞게 입력하시오> ");
			userName = ScanUtil.nextLine();
			nm_m = nm_p.matcher(userName);
		}
		System.out.println("<전화번호는 -로 구별하여 입력> ");
		System.out.print("전화번호> ");
		String tel = ScanUtil.nextLine();
		Matcher tel_m = tel_p.matcher(tel);
		while(tel_m.matches() == false) {
			System.out.print("형식에 맞게 입력하시오> ");
			tel = ScanUtil.nextLine();
			tel_m = tel_p.matcher(tel);
		}
		System.out.println("====================================");
		
		Map<String, Object> param = new HashMap<>();
		param.put("ID", userId);
		param.put("PASSWORD", password);
		param.put("NAME", userName);
		param.put("TEL", tel);
		

		int result = userDao.insertUser(param);
		
		if(0 < result) {
			System.out.println("회원가입 성공");
		} else {
			System.out.println("회원가입 실패");
		}
		
		return View.HOME;
	}
	
	
	
}
