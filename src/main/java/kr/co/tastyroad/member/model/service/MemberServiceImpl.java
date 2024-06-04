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

	@Override
    public boolean sendPasswordResetEmail(String userId, String email) {
        Member member = memberDAO.findMemberByIdAndEmail(userId, email);
        if (member != null) {
            // 이메일 발송 로직 구현
            String token = generateToken(); // 토큰 생성 로직 구현
            String resetLink = "http://yourwebsite.com/resetPassword?token=" + token;
            EmailUtil.sendEmail(email, "비밀번호 재설정", "다음 링크를 통해 비밀번호를 재설정하세요: " + resetLink);
            memberDAO.savePasswordResetToken(userId, token);
            return true;
        }
        return false;
    }

    private String generateToken() {
        // 토큰 생성 로직 구현
        return UUID.randomUUID().toString();
    }
	
}