package kr.co.tastyroad.member.model.service;

import java.sql.SQLException;

import kr.co.tastyroad.member.model.dao.MemberDAO;
import kr.co.tastyroad.member.model.dto.Member;

public class MemberServiceImpl implements MemberService {
	private MemberDAO memberDAO;

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
    public Member getMemberByToken(String token) {
        return memberDAO.getMemberByToken(token);
    }

    public void verifyMember(String userId) {
        memberDAO.verifyMember(userId);
    }
}