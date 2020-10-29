package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.RoomDao;




public class RoomService {
	private RoomService() {}
	private static RoomService instance;
	public static RoomService getInstance() {
		if(instance == null) {
			instance = new RoomService();
		}
		return instance;
	}
	
	private RoomDao roomDao = RoomDao.getInstance();
	
	public int view() {															//방 목록 조회
		List<Map<String, Object>> roomlist = roomDao.selectRoomList();
		
		System.out.println("=====================[방 목록]======================");
		System.out.println("호실\t방이름\t최대인원\t가격\t소개");
		for(int i = 0; i < roomlist.size(); i++) {
			System.out.println(roomlist.get(i).get("R_NO") + "\t"
					+ roomlist.get(i).get("R_NAME") + "\t"
					+ roomlist.get(i).get("PERSONNEL") + "\t"
					+ roomlist.get(i).get("PRICE") + "\t"
					+ roomlist.get(i).get("INTRODUCE"));
		}
		System.out.println("==================================================");
		
//		int tp = Integer.parseInt(Controller.loginUser.get("TYPE").toString());	//Object를 int로 형변환
		
		if(Controller.type == 0) {
			System.out.println("1.방등록\t\t2.방삭제\t\t0.뒤로가기");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 1: return View.R_ADD;
			case 2: return View.R_DELETE;
			case 0: return View.M_HOME;
			}
			return View.ROOM_VIEW;
			
		} else if(Controller.type == 1){
			System.out.println("0.뒤로가기");
			int input = ScanUtil.nextInt();
			switch (input) {
			case 0:	return View.RE_HOME;
			}
			return View.ROOM_VIEW;
		}
		return View.ROOM_VIEW;
	}

	public int r_add() {														//방 추가
		
		System.out.print("방번호를 입력하시오> ");
		int no = ScanUtil.nextInt();
		System.out.print("방이름을 입력하시오> ");
		String name = ScanUtil.nextLine();
		System.out.print("최대인원을 입력하시오> ");
		int num = ScanUtil.nextInt();
		System.out.print("가격을 입력하시오> ");
		int money = ScanUtil.nextInt();
		System.out.print("소개를 입력하시오> ");
		String text = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put("R_NO", no);
		param.put("R_NAME", name);
		param.put("PERSONNEL", num);
		param.put("PRICE", money);
		param.put("INTRODUCE", text);
		
		int result = roomDao.insertRoom(param);
		
		if(0 < result) {
			System.out.println("등록되었습니다.");
		} else {
			System.out.println("등록되지 않았습니다.");
		}
		
		return View.ROOM_VIEW;
	}

	public int r_delete() {														//방 삭제
		
		System.out.println("삭제할 방번호를 입력하시오> ");
		int input = ScanUtil.nextInt();
		
		int result = roomDao.deleteRoom(input);
		
		if(0 < result) {
			System.out.println("삭제가 완료되었습니다.");
		} else {
			System.out.println("삭제되지 않았습니다.");
		}
		return View.ROOM_VIEW;
	}

	
}
