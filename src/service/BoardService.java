package service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.BoardDao;


public class BoardService {
	
	private BoardService() {}
	private static BoardService instance;
	public static BoardService getInstance() {
		if(instance == null) {
			instance = new BoardService();
		}
		return instance;
	}
	
	private BoardDao boardDao = BoardDao.getInstance();
	
	
	public int notice_view() {												//공지사항 목록 조회
		
		SimpleDateFormat sdf_n = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> boardList = boardDao.selectBoardList();
		
		System.out.println("===============[공지사항]================");
		System.out.println("번호\t제목\t작성자\t작성일");
		System.out.println("---------------------------------------");
		for(int i = 0; i < boardList.size(); i++) {
			String n_date = sdf_n.format(boardList.get(i).get("N_DATE"));
			System.out.println(boardList.get(i).get("NO") + "\t"
					+ boardList.get(i).get("TITLE") + "\t"
					+ boardList.get(i).get("NAME") + "\t"
					+ n_date);
				//	+ boardList.get(i).get("N_DATE"));
		}
		System.out.println("=======================================");
		if(Controller.type == 0) {
			System.out.println("1.조회\t2.등록\t0.뒤로가기");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1: return View.BOARD_NOTICE_LOOKUP;
			case 2: return View.BOARD_NOTICE_ADD;
			case 0: return View.M_HOME;
			}
		} else if(Controller.type == 1) {
			System.out.println("1.조회\t0.뒤로가기");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1: return View.BOARD_NOTICE_LOOKUP;
			case 0: return View.U_HOME;
			}
		}
		
		return View.BOARD_NOTICE;
	}


	public int notice_insert() {											//공지사항 작성

		System.out.print("제목을 입력하시오> ");
		String title = ScanUtil.nextLine();
		System.out.print("내용을 입력하시오> ");
		String content = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put("TITLE", title);
		param.put("CONTENT", content);
		param.put("USER_NO", Controller.loginUser.get("NUM"));
		int result = boardDao.insertBoard(param);
		
		if(0 < result) {
			System.out.println("등록되었습니다.");
		} else {
			System.out.println("등록되지 않았습니다.");
		}
		
		return View.BOARD_NOTICE;
	}

	public static int no;					//조회한 게시글 번호 저장
	
	public int notice_lookup() {											//공지사항 게시글 조회
		System.out.print("게시글 번호> ");
		no = ScanUtil.nextInt();
		
		Map<String, Object> boardNo = boardDao.notice_lookup();

		System.out.println("=================[공지]===================");
		System.out.println("번호\t" + boardNo.get("NO") + "\t");
		System.out.println("작성자\t" + boardNo.get("NAME") + "\t");
		System.out.println("작성일\t" + boardNo.get("N_DATE") + "\t");
		System.out.println("제목\t" + boardNo.get("TITLE") + "\t");
		System.out.println("내용\t" + boardNo.get("CONTENT") + "\t");
		System.out.println("=========================================");
		if(Controller.type == 0) {
			System.out.println("1.수정\t2.삭제\t0.뒤로가기");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1: return View.BOARD_NOTICE_UPDATE;
			case 2: return View.BOARD_NOTICE_DELETE;
			case 0: return View.BOARD_NOTICE;
			}
		} else if(Controller.type == 1) {
			System.out.println("0.뒤로가기");
			int input = ScanUtil.nextInt();
			switch(input) {
			case 0: return View.BOARD_NOTICE;
			}
		}
		
		return View.BOARD_NOTICE_LOOKUP;
	}


	public int notice_delete() {											//공지사항 삭제
		
		int result = boardDao.notice_delete();
		if(0 < result) {
			System.out.println("삭제되었습니다.");
		} else {
			System.out.println("삭제되지 않았습니다.");
		}
		return View.BOARD_NOTICE;
	}


	public int notice_update() {											//공지사항 수정
		
		System.out.print("제목을 입력하시오.> ");
		String title = ScanUtil.nextLine();		
		System.out.print("내용을 입력하시오.> ");
		String content = ScanUtil.nextLine();
		
		Map<String , Object> param = new HashMap<>();
		param.put("TITLE", title);
		param.put("CONTENT", content);
		int result = boardDao.notice_update(param);
		
		if(0 < result) {
			System.out.println("수정되었습니다.");
		} else {
			System.out.println("수정되지 않았습니다.");
		}
		
		return View.BOARD_NOTICE;
	}


	public int review_view() {												//리뷰 목록 조회 
		
		List<Map<String, Object>> boardList = boardDao.selectReviewList();
		
		System.out.println("================[리뷰]=================");
		System.out.println("번호\t제목\t작성자\t별점");
		System.out.println("--------------------------------------");
		for(int i = 0; i < boardList.size(); i++) {
			System.out.print(boardList.get(i).get("NO") + "\t"
					+ boardList.get(i).get("TITLE") + "\t"
					+ boardList.get(i).get("NAME") + "\t");
			
			for(int j = 1; j <= Integer.parseInt(boardList.get(i).get("SCORE").toString()); j++ ) {
				System.out.print("★");
			}
			System.out.println();
		}
		System.out.println("======================================");
		if(Controller.type == 0) {
			System.out.println("1.조회\t0.뒤로가기");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1: return View.BOARD_REVIEW_LOOKUP;
			case 0: return View.M_HOME;
			}
		} else if(Controller.type == 1) {
			System.out.println("1.조회\t2.등록\t0.뒤로가기");
			int input = ScanUtil.nextInt();
			switch(input) {
			case 1: return View.BOARD_REVIEW_LOOKUP;
			case 2: return View.BOARD_REVIEW_ADD;
			case 0: return View.U_HOME;
			}
		}
		return View.BOARD_REVIEW;
	}


	public int review_lookup() {											//리뷰 게시글 조회
		System.out.print("게시글 번호> ");
		no = ScanUtil.nextInt();
		
		Map<String, Object> boardNo = boardDao.review_lookup();

		System.out.println("=================[리뷰]===================");
		System.out.println("번호\t" + boardNo.get("NO") + "\t");
		System.out.println("작성자\t" + boardNo.get("NAME") + "\t");
		System.out.println("작성일\t" + boardNo.get("R_DATE") + "\t");
		System.out.print("별점\t");		
		for(int i = 1; i <= Integer.parseInt(boardNo.get("SCORE").toString()); i++ ) {
			System.out.print("★");
		}
		System.out.println();
		System.out.println("제목\t" + boardNo.get("TITLE") + "\t");
		System.out.println("내용\t" + boardNo.get("CONTENT") + "\t");
		System.out.println("=========================================");
		if(Controller.type == 0) {
			System.out.println("0.뒤로가기");
			int input = ScanUtil.nextInt();
			switch (input) {		
			case 0: return View.BOARD_REVIEW;
			}
		} else if(Controller.type == 1) {
			if(Integer.parseInt(Controller.loginUser.get("NUM").toString())
					== Integer.parseInt(boardNo.get("USER_NO").toString())) {
				System.out.println("1.수정\t2.삭제\t0.뒤로가기");
				int input = ScanUtil.nextInt();
				switch (input) {
				case 1: return View.BOARD_REVIEW_UPDATE;
				case 2: return View.BOARD_REVIEW_DELETE;
				case 0: return View.BOARD_REVIEW;
				}
			} else {
				System.out.println("0.뒤로가기");
				int input = ScanUtil.nextInt();
				switch (input) {		
				case 0: return View.BOARD_REVIEW;
				}
			}
		}
		
		return View.BOARD_REVIEW_LOOKUP;
		
	}


	public int review_insert() {											//리뷰 작성
		
		System.out.print("제목을 입력하시오.> ");
		String title = ScanUtil.nextLine();
		System.out.print("내용을 입력하시오.> ");
		String content = ScanUtil.nextLine();
		System.out.println("5점 만점입니다.");
		System.out.print("별점을 입력하시오.> ");
		
		int score = ScanUtil.nextInt();
		while(true) {
			
			if(score > 5 || score < 1) {
				System.out.println("평점은 1 ~ 5점으로 입력해야합니다.");
				score = ScanUtil.nextInt();
			}
			else {
				break;
			}
			
		}
		Map<String, Object> param = new HashMap<>();
		param.put("TITLE", title);
		param.put("CONTENT", content);
		param.put("SCORE", score);
		param.put("USER_NO", Controller.loginUser.get("NUM"));
		
		int result = boardDao.insertreview(param);
		if(result > 0) {
			System.out.println("등록되었습니다.");
		} else {
			System.out.println("등록되지 않았습니다.");
		}
		
		return View.BOARD_REVIEW;
	}


	public int review_delete() {											//리뷰 삭제
		
		int result = boardDao.review_delete();
		if(0 < result) {
			System.out.println("삭제되었습니다.");
		} else {
			System.out.println("삭제되지 않았습니다.");
		}
		return View.BOARD_REVIEW;
		
	}


	public int review_update() {											//리뷰 수정
		System.out.print("제목을 입력하시오.> ");
		String title = ScanUtil.nextLine();
		System.out.print("내용을 입력하시오.> ");
		String content = ScanUtil.nextLine();
		System.out.print("별점을 입력하시오.> ");
		int score = ScanUtil.nextInt();
		
		Map<String , Object> param = new HashMap<>();
		param.put("TITLE", title);
		param.put("CONTENT", content);
		param.put("SCORE", score);
		int result = boardDao.review_update(param);
		if(0 < result) {
			System.out.println("수정되었습니다.");
		} else {
			System.out.println("수정되지 않았습니다.");
		}
		return View.BOARD_REVIEW;
	}
}
