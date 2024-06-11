package kr.co.tastyroad.reservation.service;

import java.util.List;

import kr.co.tastyroad.reservation.dto.ReservationDto;

public interface ReservationService {
	public int resEnroll(ReservationDto resDto);
	public List<ReservationDto> getReservations(int userNo);
	
    public boolean deleteReservation(int resNo);

	
}
