package kr.co.tastyroad.member.model.service;

import kr.co.tastyroad.member.model.dto.Member;

public interface MemberService {
	public int register(Member member);
	
	public int userUpdate(Member member);
	
	public Member login(Member member);
	
	public int duplicateId(String id);
	
	public Member getHashPassword(String id);

	Member getMemberById(String userId);

	int updateMember(Member member);
	
}
