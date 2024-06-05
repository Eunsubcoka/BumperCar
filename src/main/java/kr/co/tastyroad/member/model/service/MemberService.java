package kr.co.tastyroad.member.model.service;

import java.sql.SQLException;

import kr.co.tastyroad.member.model.dto.Member;

public interface MemberService {
	public int register(Member member) throws SQLException;
	
	public int userUpdate(Member member);
	
	public Member login(Member member);
	
	public int duplicateId(String id);
	
	public Member getHashPassword(String id);

	Member getMemberById(String userId);

	int updateMember(Member member);

	Member getMemberByToken(String token);

	void verifyMember(String userId);

	void updateUserProfile(String userId, String userName, String userEmail, String userAddress, String userPhone,
			String profileImageUrl);

	Member getUserProfile(int userId);

	void updateUserProfile(Member member);

	String findIdByEmail(String email);

    boolean sendPasswordResetEmail(String userId, String userEmail);
    
    boolean verifyResetToken(String token);
    
    boolean resetPassword(String token, String newPassword);
	
}
