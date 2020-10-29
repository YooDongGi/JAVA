package controller;		//화면이동

import java.util.Map;

import service.BoardService;
import service.ReserService;
import service.RoomService;
import service.UserService;
import util.ScanUtil;
import util.View;

public class Controller {

	public static void main(String[] args) {

		new Controller().start();
		
	}
	
	public static Map<String, Object> loginUser;
	public static int type; 

	private UserService userService = UserService.getInstance();
	private RoomService roomService = RoomService.getInstance();
	private BoardService boardService = BoardService.getInstance();
	private ReserService reserService = ReserService.getInstance();
	
	private void start() {

		int view = View.HOME;
		
		while(true) {
			switch (view) {
			case View.HOME: view = home(); break;
			case View.LOGIN: view = userService.login(); break;
			case View.JOIN: view = userService.join(); break;
			case View.M_HOME: view = m_home(); break;
			case View.U_HOME: view = u_home(); break;
			case View.ROOM_VIEW: view = roomService.view(); break;
			case View.R_ADD: view = roomService.r_add(); break;
			case View.R_DELETE: view = roomService.r_delete(); break;
			case View.BOARD_LIST: view = board_list(); break;
			case View.BOARD_NOTICE: view = boardService.notice_view(); break;
			case View.BOARD_NOTICE_ADD: view = boardService.notice_insert(); break;
			case View.BOARD_NOTICE_LOOKUP: view = boardService.notice_lookup(); break;
			case View.BOARD_NOTICE_DELETE: view = boardService.notice_delete(); break;
			case View.BOARD_NOTICE_UPDATE: view = boardService.notice_update(); break;

			case View.BOARD_REVIEW: view = boardService.review_view(); break;
			case View.BOARD_REVIEW_LOOKUP: view = boardService.review_lookup(); break;
			case View.BOARD_REVIEW_ADD: view = boardService.review_insert(); break;
			case View.BOARD_REVIEW_DELETE: view = boardService.review_delete(); break;
			case View.BOARD_REVIEW_UPDATE: view = boardService.review_update(); break;
			
			case View.RE_HOME: view = reser_home(); break;
			case View.RE_LOOKUP: view = reserService.reser_lookup(); break;
			case View.RE_DELETE: view = reserService.reser_delete(); break;
			case View.RE_ADD: view = reserService.reser_insert(); break;
			case View.RE_LOOKUP_M: view = reserService.reser_lookup_M(); break;
			case View.RE_DELETE_M: view = reserService.reser_delete_M(); break;
			
			case View.MONEY: view = reserService.money_all(); break;
			}
		}
		
	}


	private int home() {						//홈 화면
		for (int i = 0; i < 38; i++) {
			System.out.print("🏠");
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		System.out.println();

		System.out.println("PPPPPPP   EEEEEEE   NN     NN   SSSSSSSS  IIIIII    OOOOO     NN     NN");
		System.out.println("PP    P   E         NN N   NN   SS          II     O     O    NN N   NN");
		System.out.println("PPPPPPP   EEEEEEE   NN  N  NN   SSSSSSSS    II    O       O   NN  N  NN");
		System.out.println("PP        E         NN   N NN         SS    II     O     O    NN   N NN");
		System.out.println("PP        EEEEEEE   NN     NN   SSSSSSSS  IIIIII    OOOOO     NN     NN");

		System.out.println();
		for (int i = 0; i < 38; i++) {
			System.out.print("🏠");
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		System.out.println("\t\t\t😁방문해주셔서 감사합니다😁");
		System.out.println();
		System.out.println("------------------[로그인 및 회원가입]-----------------");
		System.out.println("1.로그인 \t\t 2.회원가입\t0.프로그램 종료");
		System.out.println("--------------------------------------------------");
		System.out.print("번호입력> ");
		
		int input = ScanUtil.nextInt();
		
		switch (input) {
			case 1: return View.LOGIN;
			case 2: return View.JOIN;
			case 0: 
			System.out.println("프로그램이 종료되었습니다.");
			System.exit(0);
		}
		return View.HOME;
	}
	
	private int m_home() {						//관리자 홈 화면
		System.out.println();
		System.out.println("▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶회원 정보: 관리자◀◀◀◀◀◀◀◀◀◀◀◀◀◀◀◀◀");
		System.out.println("----------------------------------------------------------");
		System.out.println("1.방목록\t\t2.예약관리\t3.게시판\t\t0.로그아웃");
		int input = ScanUtil.nextInt();
		
		switch (input) {
		case 1:	return View.ROOM_VIEW;	
		case 2:	return View.RE_HOME;
		case 3: return View.BOARD_LIST;
		case 0:
			Controller.loginUser = null;
			return View.HOME;
		
		}
	
		return View.M_HOME;
	}
	private int u_home() {						//유저 홈 화면
		System.out.println();
		System.out.println("▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶회원 정보: 고객◀◀◀◀◀◀◀◀◀◀◀◀◀◀◀");
		System.out.println("----------------------[홈 화면]----------------------");
		System.out.println("1.예약 및 조회\t2.게시판\t\t0.로그아웃");
		int input = ScanUtil.nextInt();
		
		switch (input) {
		case 1 : return View.RE_HOME;
		case 2: return View.BOARD_LIST;
		case 0:
			Controller.loginUser = null;
			Controller.type = 100;
			return View.HOME;
		}
		return View.U_HOME;
	}
	private int board_list() {					//게시글 목록 선택
		System.out.println(); 
		System.out.println("------------------------[게시판]------------------------");
		System.out.println("1.공지사항\t2.리뷰\t\t0.뒤로가기");
		int input = ScanUtil.nextInt();
		
		if(type == 0){
			switch (input) {
			case 1: return View.BOARD_NOTICE;
			case 2: return View.BOARD_REVIEW;
			case 0: return View.M_HOME;
			}
		} else if(type == 1) {
			switch (input) {
			case 1: return View.BOARD_NOTICE;
			case 2: return View.BOARD_REVIEW;
			case 0: return View.U_HOME;
			}
		}
		return View.BOARD_LIST;
	}
	private int reser_home() {
		if(type == 0) {
			System.out.println("-----------------------[예약관리]-------------------------");
			System.out.println("1.방 예약\t\t2.예약조회\t3.총 매출\t\t0.뒤로가기");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1: return View.RE_ADD;
			case 2: return View.RE_LOOKUP_M;
			case 3: return View.MONEY;
			case 0: return View.M_HOME;
			}
		}else if(type == 1) {
			System.out.println();
			System.out.println("-----------------------[예약 및 조회]-------------------------");
			System.out.println("1.방목록\t\t2.예약하기\t3.예약조회\t0.뒤로가기");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1: return View.ROOM_VIEW;
			case 2: return View.RE_ADD;
			case 3: return View.RE_LOOKUP;
			case 0: return View.U_HOME;
			}
		}
		
		return View.RE_HOME;
	}
}

