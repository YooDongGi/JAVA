package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import service.BoardService;
import util.JDBCUtil;


public class BoardDao {
	
	private BoardDao() {}
	private static BoardDao instance;
	public static BoardDao getInstance() {
		if(instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<Map<String, Object>> selectBoardList() {					//공지사항 목록 조회
		String sql = "SELECT A.NO, A.TITLE, A.CONTENT, B.NAME, A.N_DATE "
				+" FROM notice A, p_user B "
				+" WHERE A.user_no = B.num(+) "
				+" ORDER BY A.NO DESC";
		return jdbc.selectList(sql);
	}

	public int insertBoard(Map<String, Object> p) {							//공지사항 작성
		
		String sql = "INSERT INTO notice VALUES ((SELECT NVL(MAX(no),0) + 1 from notice),"
					+ "?, ?, SYSDATE, ?)";
		List<Object> param = new ArrayList<>();
		param.add(p.get("TITLE"));
		param.add(p.get("CONTENT"));
		param.add(p.get("USER_NO"));
		return jdbc.update(sql, param);
		
	}

	public Map<String, Object> notice_lookup() {							//공지사항 게시글 조회
		
		String sql = "SELECT A.NO, A.TITLE, A.CONTENT, B.NAME, A.N_DATE "
				+ " FROM notice A, p_user B "
				+ " WHERE A.user_no = B.num(+) AND NO = " + BoardService.no;
		
		return jdbc.selectOne(sql);
	}

	public int notice_delete() {											//공지사항 삭제

		String sql = "DELETE notice WHERE no = " + BoardService.no;
		
		
		return jdbc.update(sql);
		
	}

	public int notice_update(Map<String, Object> p) {						//공지사항 수정
		String sql = "UPDATE notice set" 
				+" title = ? , content = ?, n_date = sysdate "
				+" where no = "+ BoardService.no;
		List<Object> param = new ArrayList<>();
		param.add(p.get("TITLE"));
		param.add(p.get("CONTENT"));
	
		return jdbc.update(sql, param);
	}

	public List<Map<String, Object>> selectReviewList() {					//리뷰 목록 조회
		
		String sql = "SELECT A.no, A.title, B.name, A.score "
					+" FROM review A, p_user B "
					+" WHERE A.user_no = B.num(+)"
					+" ORDER BY no DESC";
		return jdbc.selectList(sql);
	}

	public Map<String, Object> review_lookup() {							//리뷰 게시글 조회
		
		String sql = "SELECT A.no, A.title, B.name, A.score, A.content, A.r_date, A.user_no"
					+" FROM review A, p_user B "
					+" WHERE A.user_no = B.num(+) AND no =" + BoardService.no;
		
		return jdbc.selectOne(sql);
	}

	public int insertreview(Map<String, Object> p) {						//리뷰 작성

		String sql = "INSERT INTO review VALUES ( " 
					+" (SELECT NVL(MAX(no),0) + 1 FROM review) "
					+", ?, ?, ?, SYSDATE, ?)";
		List<Object> param = new ArrayList<>();
		param.add(p.get("TITLE"));
		param.add(p.get("SCORE"));
		param.add(p.get("CONTENT"));
		param.add(p.get("USER_NO"));
		
		return jdbc.update(sql, param);
	}

	public int review_delete() {											//리뷰 삭제
		
		String sql = "DELETE review WHERE no = "+ BoardService.no;
		return jdbc.update(sql);
	}

	public int review_update(Map<String, Object> p) {						//리뷰 수정
		
		String sql = "UPDATE review SET TITLE = ? "
					+ ", SCORE = ?, CONTENT = ?, R_DATE = SYSDATE WHERE no = " + BoardService.no;
		List<Object> param = new ArrayList<>();
		param.add(p.get("TITLE"));
		param.add(p.get("SCORE"));
		param.add(p.get("CONTENT"));
		
		return jdbc.update(sql, param);
	}

	
	
}
