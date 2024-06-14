package kr.co.tastyroad.member.model.service;

import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

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
    public Member getMemberByToken(String token) throws SQLException {
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
    public boolean sendPasswordResetEmail(String userId, String userEmail) {
        try {
            Member member = memberDAO.findMemberByIdAndEmail(userId, userEmail);
            if (member != null) {
                String token = generateToken();
                String resetLink = "http://localhost/views/member/resetPassword.jsp?token=" + token;
                boolean emailSent = EmailUtil.sendEmail(userEmail, "비밀번호 재설정", "다음 링크를 통해 비밀번호를 재설정하세요: " + resetLink);
                if (emailSent) {
                    memberDAO.savePasswordResetToken(userId, token);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
    
    public boolean verifyResetToken(String token) {
        try {
            return memberDAO.verifyToken(token);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean resetPassword(String token, String newPassword) {
        try {
            if (!validatePassword(newPassword)) {
                return false;
            }

            Member member = memberDAO.getMemberByToken(token);
            if (member == null) {
            	System.out.println("2");
                return false;
            }

            String hashedPassword = hashPassword(newPassword);
            return memberDAO.updatePassword(member.getUserId(), hashedPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    private boolean validatePassword(String password) {
        // 최소 8자, 대문자, 소문자, 숫자, 특수 문자 포함 여부 검사
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$";
        return Pattern.matches(passwordPattern, password);
    }
    @Override
    public Member findOrCreateMember(String googleId, String email, String name) {
        Member member = memberDAO.getMemberByGoogleId(googleId);
        if (member == null) {
            member = new Member();
            member.setGoogleId(googleId);
            member.setUserEmail(email);
            member.setUserName(name);
            member.setUserId("user_" + googleId); // 고유한 userId 생성
            member.setUserPwd(""); // Google 사용자에 대한 비밀번호는 비워둠
            member.setUserType("google"); // 사용자 유형을 Google로 설정
            memberDAO.insertMember(member);
        }
        return member;
    }
    
//    public Member findOrCreateMember(String googleId, String email, String name) {
//        Member member = memberDAO.findByGoogleId(googleId);
//        if (member == null) {
//            // Google ID로 찾지 못하면 이메일로 기존 사용자 확인
//            member = memberDAO.findByEmail(email);
//            if (member == null) {
//                // 사용자 정보가 없으면 새로 생성
//                member = new Member();
//                member.setUserId("user_" + googleId); // 고유한 userId 설정
//                member.setUserName(name);
//                member.setUserEmail(email);
//                member.setUserPwd(""); // 초기 비밀번호 설정 (필요에 따라 처리)
//                member.setUserAddress("");
//                member.setUserPhone("");
//                member.setToken(""); // 토큰 초기화
//                member.setVerified(true); // Google 로그인 사용자는 기본적으로 인증된 것으로 설정
//                member.setGoogleId(googleId);
//                memberDAO.insertMember(member);
//            } else {
//                // 기존 사용자의 google_id 업데이트
//                member.setGoogleId(googleId);
//                memberDAO.updateGoogleId(member.getUserId(), googleId);
//            }
//        }
//        return member;
//    }
//    public Member findOrCreateMember(String userId, String email, String name) {
//        Member member = memberDAO.getMemberByUserId(userId);
//        if (member == null) {
//            member = new Member();
//            member.setUserId(userId);
//            member.setUserEmail(email);
//            member.setUserName(name);
//            member.setUserType("Google"); // 예: 사용자 유형을 'Google'로 설정
//            memberDAO.insertMember(member);
//        }
//        return member;
//    }
}