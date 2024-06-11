package kr.co.tastyroad.reservation.service;

import java.util.List;

import kr.co.tastyroad.reservation.dao.ReservationDao;
import kr.co.tastyroad.reservation.dto.ReservationDto;

public class ReservationServiceImpl implements ReservationService {

	ReservationDao resDao = new ReservationDao();
	
	@Override
	public int resEnroll(ReservationDto resDto) {
		return resDao.resEnroll(resDto);
	}

	@Override
	public List<ReservationDto> getReservations(int userNo) {
		return resDao.getReservations(userNo);
	}
	
	

}
