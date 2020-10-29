package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import controller.Controller;

public class ReserDao {
	private ReserDao() {}
	private static ReserDao instance;
	public static ReserDao getInstance() {
		if(instance == null) {
			instance = new ReserDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> reserlookup() {
		
		String sql = "SELECT A.r_no, B.name, A.people ,A.s_date, A.e_date "
					+ " FROM reservation A, p_user B "
					+ " WHERE A.user_no = B.num AND B.num =" + Controller.loginUser.get("NUM")
					+ " ORDER BY A.s_date";
		
		return jdbc.selectList(sql);
	}

	public int reser_delete(Map<String, Object> p) {
		
		String sql = "DELETE reservation WHERE r_no = ?"
				+ " AND s_date = TO_DATE( ? , 'YYYY-MM-DD')" 
				+ " AND user_no = "+ Controller.loginUser.get("NUM");
		List<Object> param = new ArrayList<>();
		param.add(p.get("HO"));
		param.add(p.get("S_DATE"));
		return jdbc.update(sql, param);
	}

	public int insertreser(Map<String, Object> p) {
		
		String sql = "INSERT INTO reservation VALUES ( "+ Controller.loginUser.get("NUM")
					+ ",? ,? , ? * (SELECT price FROM room WHERE r_no = ?) "
					+ ",? ,?)";
		
		List<Object> param = new ArrayList<>();
		param.add(p.get("R_NO"));
		param.add(p.get("PEOPLE"));
		param.add(p.get("DAY"));
		param.add(p.get("R_NO"));
		param.add(p.get("S_DATE"));
		param.add(p.get("E_DATE"));
		
		return jdbc.update(sql, param);
	}

	public List<Map<String, Object>> reserlookup_M() {
		
		String sql = "SELECT A.r_no, DECODE(name,'관리자','비회원',name) name, A.people, B.tel , A.s_date, A.e_date "
				+ " FROM reservation A, p_user B "
				+ " WHERE A.user_no = B.num"
				+ " ORDER BY A.s_date, A.r_no";
		
		return jdbc.selectList(sql);
	}

	public int reser_delete_M(Map<String, Object> p) {
		
		String sql = "DELETE reservation WHERE r_no = ?"
				+ " AND s_date = TO_DATE(?, 'YYYY-MM-DD')";
		List<Object> param = new ArrayList<>();
		param.add(p.get("HO"));
		param.add(p.get("S_DATE"));
		
		return jdbc.update(sql,param);
		
	}

	public Map<String, Object> money_all() {
		
		String sql = "SELECT NVL(SUM(price),0) MONEY FROM reservation ";
		
		
		return jdbc.selectOne(sql);
	}

	

}
