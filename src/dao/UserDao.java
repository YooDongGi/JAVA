package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class UserDao {

	private UserDao() {}
	private static UserDao instance;
	public static UserDao getInstance() {
		if(instance == null) {
			instance = new UserDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public Map<String, Object> selectUser(String userId, String password) {		//로그인
		String sql = "SELECT NUM, ID, NAME, TYPE"
					+ " FROM p_user"
					+ " WHERE ID = ? "
					+ " AND PASSWORD = ?";
		List<Object> param = new ArrayList<>();
		param.add(userId);
		param.add(password);
		
		return jdbc.selectOne(sql, param);
	}
	
	public int insertUser(Map<String, Object> p) {								//회원가입
		String sql = "INSERT INTO p_user VALUES ((SELECT NVL(MAX(num),0) + 1 from p_user),?, ?, ? ,? , 1)";
		
		List<Object> param = new ArrayList<>();
		param.add(p.get("ID"));
		param.add(p.get("PASSWORD"));
		param.add(p.get("NAME"));
		param.add(p.get("TEL"));
		
		return jdbc.update(sql, param);
		
	}

	public List<Map<String, Object>> selectidlist() {
		String sql = "SELECT ID FROM p_user";
		
		
		return jdbc.selectList(sql);
	}
	
	
}
