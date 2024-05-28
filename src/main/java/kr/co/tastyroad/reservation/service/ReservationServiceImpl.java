package kr.co.tastyroad.reservation.service;

import kr.co.tastyroad.reservation.dao.ReservationDao;
import kr.co.tastyroad.reservation.dto.ReservationDto;

public class ReservationServiceImpl	implements ReservationService {
	
	ReservationDao resDao = new ReservationDao();
	
	public int resEnroll(ReservationDto resDto) {
		return resDao.resEnroll(resDto);
	}

}
