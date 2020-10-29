package service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.ReserDao;
import dao.RoomDao;



public class ReserService {
	private ReserService() {}
	private static ReserService instance;
	public static ReserService getInstance() {
		if(instance == null) {
			instance = new ReserService();
		}
		return instance;
	}
	
	Calendar cal = Calendar.getInstance();					//날짜 계산을 위해 추가
	private ReserDao reserDao = ReserDao.getInstance();
	private RoomDao roomDao = RoomDao.getInstance();
	
	public int reser_lookup() {
		
		List<Map<String, Object>> reserlookup = reserDao.reserlookup();
		SimpleDateFormat sdf_dt = new SimpleDateFormat("yy/MM/dd");
		
		System.out.println("=====================[예약 목록]====================");
		System.out.println("호실\t예약자\t인원\t 예약일\t\t퇴실일");
		System.out.println("-------------------------------------------------");
		for(int i = 0; i < reserlookup.size(); i++) {
			String s_date = sdf_dt.format(reserlookup.get(i).get("S_DATE"));
			String e_date = sdf_dt.format(reserlookup.get(i).get("E_DATE"));
			
			System.out.println(reserlookup.get(i).get("R_NO") + "\t"
					+ reserlookup.get(i).get("NAME") + "\t"
					+ reserlookup.get(i).get("PEOPLE")+ "\t"
					+ s_date +"  ~\t"
					+ e_date);
		}
		System.out.println("=================================================");
		System.out.println("1.삭제\t0.뒤로가기");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: return View.RE_DELETE;
		case 0: return View.RE_HOME;
		}
		return View.RE_LOOKUP;
	}

	public int reser_delete() {
		System.out.print("삭제할 호실을 입력하시오.> ");
		int ho = ScanUtil.nextInt();		
		System.out.print("예약한 날짜를 입력하시오.(YYYY-MM-DD)> ");
		String s_date = ScanUtil.nextLine();
		
		Map<String, Object> param = new HashMap<>();
		param.put("HO", ho);
		param.put("S_DATE", s_date);
		int result = reserDao.reser_delete(param);
		
		if(0 < result) {
			System.out.println("삭제되었습니다.");
		} else {
			System.out.println("삭제되지 않았습니다.");
		}
		return View.RE_LOOKUP;
	}

	public int reser_insert() {
		Date today = new Date();
		String e_date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		System.out.print("방 호실 > ");
		int r_no = ScanUtil.nextInt();
		List<Map<String, Object>> r_ho = roomDao.roomho();
		
		boolean ok = true;
		ha : while(ok){
			for(int i = 0; i < r_ho.size(); i++) {
				if(r_no == Integer.parseInt(r_ho.get(i).get("R_NO").toString() ) ) {
					ok = false;
					break ha;
				}
			}
			System.out.println("존재하지 않는 방입니다.");
			return View.RE_ADD;
		}
		
		System.out.print("인원 수 > ");
		int people = ScanUtil.nextInt();
		
		Map<String, Object> r_people = roomDao.roompeople(r_no);
		if(people > Integer.parseInt(r_people.get("PERSONNEL").toString())) {
			System.out.println("최대인원을 넘어갈 수 없습니다.");
			return View.RE_ADD;
		}
		
		System.out.println("YYYY-MM-DD 형식으로 입력하시오.");
		System.out.print("예약 날짜 > ");
		String s_date = ScanUtil.nextLine();
		System.out.print("묵을 일 수 > ");
		int day = ScanUtil.nextInt();

		
		
		
		while(true) {
			try {
				Date s_d = sdf.parse(s_date);
				cal.setTime(s_d);
				cal.add(Calendar.DAY_OF_MONTH, day);
				e_date = sdf.format(cal.getTime());
				if(today.getTime() > s_d.getTime()){
					System.out.println("예약일 기준 다음날부터 예약 가능합니다.");
					return View.RE_ADD;
				} 
				else {
					break;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		
		Map<String , Object> param1 = new HashMap<>();
		param1.put("R_NO", r_no);
		
		param1.put("S_DATE", s_date);
		param1.put("E_DATE", e_date);
		List<Map<String, Object>> room_date = roomDao.roomdate(param1);
		
		
		if(room_date.isEmpty()) {
		}else{
		System.out.println("이미 예약되있는 방입니다.");
		return View.RE_ADD;
		}
				
		Map<String, Object> param = new HashMap<>();
		param.put("R_NO", r_no);
		param.put("PEOPLE", people);
		param.put("DAY",day);
		param.put("S_DATE", s_date);
		param.put("E_DATE", e_date);
		
		int result = reserDao.insertreser(param);
		
		if(0 < result) {
			System.out.println("예약되었습니다.");
		} else {
			System.out.println("예약되지 않았습니다.");
		}
		
		
		return View.RE_HOME;
	}

	public int reser_lookup_M() {
		
		List<Map<String, Object>> reserlookup = reserDao.reserlookup_M();
		SimpleDateFormat sdf_dt = new SimpleDateFormat("yy/MM/dd");
		
		System.out.println("============================[예약 목록]============================");
		System.out.println("호실\t예약자\t인원\t 전화번호\t\t 예약일\t\t퇴실일");
		System.out.println("--------------------------------------------------------------");
		for(int i = 0; i < reserlookup.size(); i++) {
			String s_date = sdf_dt.format(reserlookup.get(i).get("S_DATE"));
			String e_date = sdf_dt.format(reserlookup.get(i).get("E_DATE"));
			System.out.println(reserlookup.get(i).get("R_NO") + "\t"
					+ reserlookup.get(i).get("NAME") + "\t"
					+ reserlookup.get(i).get("PEOPLE") + "\t"
					+ reserlookup.get(i).get("TEL") +"\t"
					+ s_date + " ~ "
					+ e_date );
		}
		System.out.println("=================================================================");
		System.out.println("1.삭제\t0.뒤로가기");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: return View.RE_DELETE_M;
		case 0: return View.RE_HOME;

		
		}
		
		
		return View.RE_LOOKUP_M;
	}

	public int reser_delete_M() {

		System.out.print("삭제할 호실을 입력하시오.> ");
		int ho = ScanUtil.nextInt();		
		System.out.print("예약한 날짜를 입력하시오.(YYYY-MM-DD)> ");
		String s_date = ScanUtil.nextLine();
		
		
		Map<String, Object> param = new HashMap<>();
		param.put("HO", ho);
		param.put("S_DATE", s_date);
		
		int result = reserDao.reser_delete_M(param);
		
		
		if(0 < result) {
			System.out.println("삭제되었습니다.");
		} else {
			System.out.println("삭제되지 않았습니다.");
		}
		return View.RE_LOOKUP_M;
		
	}

	public int money_all() {
		
		Map<String, Object> money_all = reserDao.money_all();
		System.out.print("총 매출은 ");
		System.out.println(money_all.get("MONEY")+ "원 입니다.");
		
		return View.M_HOME;
	}
}
