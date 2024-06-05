package kr.co.tastyroad.member.model.service;

import java.sql.SQLException;
import java.util.UUID;

import kr.co.tastyroad.common.EmailUtil;
import kr.co.tastyroad.member.model.dao.MemberDAO;
import kr.co.tastyroad.member.model.dto.Member;

public class MemberServiceImpl implements MemberService {
	private static MemberDAO memberDAO;

	public MemberServiceImpl() {
		memberDAO = new MemberDAO();
	}

	@Override
	public int register(Member member) throws SQLException {
	    return memberDAO.register(member);
	}
	@Override
	public int userUpdate(Member member) {
		return memberDAO.userUpdate(member);
	}

	@Override
	public Member login(Member member) {
		return memberDAO.login(member);
	}

	@Override
	public int duplicateId(String id) {
		return memberDAO.duplicateId(id);
	}
	
	@Override
	public Member getHashPassword(String id) {
		return memberDAO.getHashPassword(id);
	}
	@Override
	public Member getMemberById(String userId) {
		return null;
	}
	@Override
	public int updateMember(Member member) {
		return 0;
	}
	@Override
    public Member getMemberByToken(String token) {
        return memberDAO.getMemberByToken(token);
    }
	@Override
    public void verifyMember(String userId) {
        memberDAO.verifyMember(userId);
    }
	@Override
	public void updateUserProfile(String userId, String userName, String userEmail, String userAddress,
			String userPhone, String fileName) {
	}
	@Override
    public Member getUserProfile(int userNo) {
        return memberDAO.getUserProfile(userNo);
	}
	@Override
	public void updateUserProfile(Member member) {
		memberDAO.updateUserProfile(member);
	}
	@Override
    public String findIdByEmail(String email) {
        return memberDAO.findIdByEmail(email);
    }
//	@Override
//    public boolean sendPasswordResetEmail(String userId, String email) {
//        Member member = memberDAO.findMemberByIdAndEmail(userId, email);
//        if (member != null) {
//            // 이메일 발송 로직 구현
//            String token = generateToken(); // 토큰 생성 로직 구현
//            String resetLink = "http://yourwebsite.com/resetPassword?token=" + token;
//            EmailUtil.sendEmail(email, "비밀번호 재설정", "다음 링크를 통해 비밀번호를 재설정하세요: " + resetLink);
//            memberDAO.savePasswordResetToken(userId, token);
//            return true;
//        }
//        return false;
//    }
//	private String generateToken() {
//        // 토큰 생성 로직 구현
//        return UUID.randomUUID().toString();
    @Override
    public boolean sendPasswordResetEmail(String userId, String userEmail) {
        Member member = memberDAO.findMemberByIdAndEmail(userId, userEmail);
        if (member != null) {
            String token = generateToken();
            String resetLink = "http://localhost/views/member/resetPassword.jsp";
            boolean emailSent = EmailUtil.sendEmail(userEmail, "비밀번호 재설정", "다음 링크를 통해 비밀번호를 재설정하세요: " + resetLink);
            
            if (emailSent) {
                memberDAO.savePasswordResetToken(userId, token);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

	private String generateToken() {
	    return UUID.randomUUID().toString();
	}

    @Override
    public boolean verifyResetToken(String token) {
        return memberDAO.verifyToken(token);
    }

//    @Override
//    public boolean resetPassword(String token, String newPassword) {
//        return memberDAO.updatePassword(token, newPassword);
    @Override
    public boolean resetPassword(String token, String newPassword) {
        // 토큰으로 사용자 정보를 가져오는지 확인
        Member member = memberDAO.getMemberByToken(token);
        if (member == null) {
            return false; // 토큰에 해당하는 사용자가 없을 때
        }
        // 비밀번호 업데이트 로직
        boolean isUpdated = memberDAO.updatePassword(member.getUserId(), newPassword);
        return isUpdated;
    }
	
}