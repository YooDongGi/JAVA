package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class RoomDao {
	private RoomDao() {}
	private static RoomDao instance;
	public static RoomDao getInstance() {
		if(instance == null) {
			instance = new RoomDao();
		}
		return instance;
	}

	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectRoomList() {						//방 목록 조회
		String sql = "SELECT * FROM room ORDER BY r_no DESC";
		
		return jdbc.selectList(sql);
		
	}

	public int insertRoom(Map<String, Object> p) {							//방 추가

		String sql = "INSERT INTO room VALUES (?,?,?,?,?)";
		
		List<Object> param = new ArrayList<>();
		param.add(p.get("R_NO"));
		param.add(p.get("R_NAME"));
		param.add(p.get("PERSONNEL"));
		param.add(p.get("INTRODUCE"));
		param.add(p.get("PRICE"));
		
		return jdbc.update(sql, param);
		
	}

	public int deleteRoom(int input) {										//방 삭제
		
		String sql = "DELETE room WHERE r_no = "+ input;
		
		return jdbc.update(sql);
	}

	public Map<String, Object> roompeople(int r_no) {
		
		String sql = "SELECT personnel FROM room WHERE r_no = " + r_no;
		
		return jdbc.selectOne(sql);
	}

	public List<Map<String, Object>> roomho() {
		String sql = "SELECT r_no FROM room";
		return jdbc.selectList(sql);
	}

	public List<Map<String, Object>> roomdate(Map<String, Object> p) {
		
		String sql =  "SELECT r_no FROM reservation WHERE r_no = ?"
	            + " AND (s_date BETWEEN TO_DATE(? , 'YYYY-MM-DD') AND TO_DATE( ?, 'YYYY-MM-DD')"
	            + " OR e_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')"
	            + " AND TO_DATE( ? ,'YYYY-MM-DD') BETWEEN s_date AND e_date"
	              + " OR TO_DATE( ? ,'YYYY-MM-DD') BETWEEN s_date AND e_date)";

		List<Object> param = new ArrayList<>();
		param.add(p.get("R_NO"));
	    param.add(p.get("S_DATE"));
	    param.add(p.get("E_DATE"));
	    param.add(p.get("S_DATE"));
	    param.add(p.get("E_DATE"));
	    param.add(p.get("S_DATE"));
	    param.add(p.get("E_DATE"));

		
		return jdbc.selectList(sql, param);
	}


	
}
