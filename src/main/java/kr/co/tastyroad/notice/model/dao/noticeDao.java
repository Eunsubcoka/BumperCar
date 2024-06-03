package kr.co.tastyroad.notice.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import kr.co.tastyroad.common.PageInfo;
import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.common.DatabaseConnection;

public class noticeDao {
	private Connection con;
	private DatabaseConnection dc;
	private PreparedStatement pstmt;

	public noticeDao() {
		dc = new DatabaseConnection();
		con = dc.connDB();
	}
    public noticeDto getNotice(String postId) {
        noticeDto notice = null;
        String sql = "SELECT content, imagePath FROM notices WHERE postId = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, postId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                notice = new noticeDto();
                notice.setPostId(postId);
                notice.setContent(rs.getString("content"));
                notice.setImagePath(rs.getString("imagePath"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return notice;
    }

    public void updateNotice(String postId, String content, String imagePath) {
        String sql = "UPDATE notices SET content = ?, imagePath = ? WHERE postId = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, content);
            if (imagePath == null) {
                pstmt.setNull(2, Types.VARCHAR);
            } else {
                pstmt.setString(2, imagePath);
            }
            pstmt.setString(3, postId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	

	public ArrayList<noticeDto> getList(PageInfo pi, String category, String searchText) {
		ArrayList<noticeDto> result = new ArrayList<>();
		String query = "SELECT * FROM notice nt " + "JOIN Tasty_member m ON m.user_no = nt.user_no " + "WHERE "
				+ category + " LIKE '%'||?||'%'" + "ORDER BY nt.noticeDate DESC "
				+ "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, searchText);
			pstmt.setInt(2, pi.getOffset());
			pstmt.setInt(3, pi.getBoardLimit());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("noticeNo");
				String title = rs.getString("noticeTitle");
				String content = rs.getString("noticeContent");
				int views = rs.getInt("noticeView");
				String date = "";
				try {
					SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					date = targetFormat.format(originalFormat.parse(rs.getString("noticeDate")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				int memberNo = rs.getInt("user_no");
				String memberName = rs.getString("user_name");

				noticeDto noticeDto = new noticeDto();
				noticeDto.setNoticeNo(no);
				noticeDto.setNoticeTitle(title);
				noticeDto.setNoticeContent(content);
				noticeDto.setNoticeView(views);
				noticeDto.setNoticeDate(date);
				noticeDto.setUserNo(memberNo);
				noticeDto.setUserName(memberName);

				result.add(noticeDto);
			}

			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	 public ArrayList<noticeDto> getNoticeList() {
	        ArrayList<noticeDto> result = new ArrayList<>();
	        String query = "SELECT * FROM notice ORDER BY noticeDate DESC";

	        try {
	            pstmt = con.prepareStatement(query);
	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                noticeDto notice = new noticeDto();
	                notice.setNoticeNo(rs.getInt("noticeNo"));
	                notice.setNoticeTitle(rs.getString("noticeTitle"));
	                notice.setNoticeContent(rs.getString("noticeContent"));
	                notice.setNoticeView(rs.getInt("noticeView"));
	                notice.setNoticeDate(rs.getString("noticeDate"));
	                notice.setUserNo(rs.getInt("user_no"));
	                
	                result.add(notice);
	            }

	            rs.close();
	            pstmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return result;
	    }

	

	public int getListCount(String category, String searchText) {
		String query = "SELECT count(*) AS cnt FROM notice nt " + "JOIN Tasty_member m ON nt.user_no = m.user_no "
				+ "WHERE " + category + " LIKE '%'||?||'%'";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, searchText);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int result = rs.getInt("cnt");
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public int enroll(noticeDto noticeDto) {
		String query = "INSERT INTO notice VALUES(notice_seq.nextval,?, ?, default, default, ?)";
		int result = 0;

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, noticeDto.getNoticeTitle());
			pstmt.setString(2, noticeDto.getNoticeContent());
			pstmt.setInt(3, noticeDto.getUserNo());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public noticeDto getDetail(int noticeNo) {
		String query = "SELECT * FROM notice n LEFT OUTER JOIN notice_upload nu ON n.NOTICENO  = nu.NOTICENO  WHERE n.noticeNo = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeNo);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int ntNo = rs.getInt("noticeNo");
				String ntTitle = rs.getString("noticeTitle");
				String ntContent = rs.getString("noticeContent");
				String ntDate = rs.getString("noticeDate");
				int ntView = rs.getInt("noticeView");
				int mNo = rs.getInt("user_no");
				int nuNo = rs.getInt("nuNo");
				String nuName = rs.getString("nuName");
				String nuPath = rs.getString("nuPath");

				noticeDto noticeDto = new noticeDto();
				noticeDto.setNoticeNo(ntNo);
				noticeDto.setNoticeTitle(ntTitle);
				noticeDto.setNoticeContent(ntContent);
				noticeDto.setNoticeDate(ntDate);
				noticeDto.setNoticeView(ntView);
				noticeDto.setUserNo(mNo);
				noticeDto.setFileNo(nuNo);
				noticeDto.setFileName(nuName);
				noticeDto.setFilePath(nuPath);

				return noticeDto;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void getWriter(noticeDto noticeDto) {
		String query = "SELECT user_name FROM Tasty_member WHERE user_no = ?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeDto.getUserNo());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String name = rs.getString("user_name");
				noticeDto.setUserName(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getUserType(noticeDto noticeDto) {
		String query = "SELECT user_type FROM notice n JOIN Tasty_member tm ON n.user_no = tm.user_no WHERE n.user_no = ?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeDto.getUserNo());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String type = rs.getString("user_type");
				noticeDto.setUserType(type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int setView(int noticeNo) {
		String query = "UPDATE notice SET noticeView = noticeView+1 WHERE noticeNo = ?";
		int result = 0;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			return pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public int setEdit(noticeDto noticeDto) {
		String query = "UPDATE notice " + "SET noticeTitle = ?," + " noticeContent = ?" + "WHERE noticeNo = ?";

		try {
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, noticeDto.getNoticeTitle());
			pstmt.setString(2, noticeDto.getNoticeContent());
			pstmt.setInt(3, noticeDto.getNoticeNo());

			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public int setDelete(int noticeNo) {
		String query = "DELETE FROM notice WHERE noticeNo = ?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeNo);

			int result = pstmt.executeUpdate();

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public noticeDto selectNo(noticeDto noticeDto) {
		String query = "SELECT noticeNo FROM notice " + "WHERE noticeNo = (SELECT MAX(noticeNo) FROM notice "
				+ "WHERE user_no = ?)";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeDto.getUserNo());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int noticeNo = rs.getInt("noticeNo");
				noticeDto.setNoticeNo(noticeNo);
				return noticeDto;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int fileUpload(noticeDto noticeDto) {
	    String query = "MERGE INTO notice_upload nu " +
	                   "USING (SELECT ? AS nuPath, ? AS nuName, ? AS noticeNo FROM dual) src " +
	                   "ON (nu.noticeNo = src.noticeNo) " +
	                   "WHEN MATCHED THEN " +
	                   "  UPDATE SET nu.nuPath = src.nuPath, nu.nuName = src.nuName " +
	                   "WHEN NOT MATCHED THEN " +
	                   "  INSERT (nu.nuNo, nu.nuPath, nu.nuName, nu.noticeNo) " +
	                   "  VALUES (notice_upload_seq.nextval, src.nuPath, src.nuName, src.noticeNo)";

	    try {
	        pstmt = con.prepareStatement(query);
	        pstmt.setString(1, noticeDto.getFilePath());
	        pstmt.setString(2, noticeDto.getFileName());
	        pstmt.setInt(3, noticeDto.getNoticeNo());

	        int result = pstmt.executeUpdate();
	        return result;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}




	public void getFileName(noticeDto result) {
	    String query = "SELECT nu.nuNo, nu.nuName, nu.nuPath FROM notice n LEFT OUTER JOIN notice_upload nu ON n.noticeNo = nu.noticeNo WHERE n.noticeNo = ?";

	    try {
	        pstmt = con.prepareStatement(query);
	        pstmt.setInt(1, result.getNoticeNo());
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int no = rs.getInt("nuNo");
	            String name = rs.getString("nuName");
	            String path = rs.getString("nuPath");

	            result.setFileNo(no);
	            result.setFileName(name != null ? name : "");
	            result.setFilePath(path != null ? path : ""); // 파일 경로를 설정
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	public int setFileDelete(int noticeNo) {
	    String query = "DELETE FROM notice_upload WHERE noticeNo = ?";

	    try {
	        pstmt = con.prepareStatement(query);
	        pstmt.setInt(1, noticeNo);
	        int result = pstmt.executeUpdate();
	        return result;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}



	public boolean deleteNotice(int noticeNo) {
		boolean deleted = false;

		try {
			// 업로드된 이미지 먼저 삭제
			String query1 = "DELETE FROM notice_upload WHERE noticeNo = ?";
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, noticeNo);
			pstmt.executeUpdate();
			pstmt.close();

			// 해당 번호의 notice_upload를 삭제했으니 이제 본문 삭제
			String query2 = "DELETE FROM notice WHERE noticeNo = ?";
			pstmt = con.prepareStatement(query2);
			pstmt.setInt(1, noticeNo);

			int result = pstmt.executeUpdate();

			if (result > 0) {
				deleted = true;
			}

			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return deleted;
	}

	public noticeDto getLatestNotice() {
		String query = "SELECT * FROM notice nt JOIN Tasty_member m ON m.user_no = nt.user_no ORDER BY nt.noticeDate DESC FETCH FIRST 1 ROWS ONLY";

		try {
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int no = rs.getInt("noticeNo");
				String title = rs.getString("noticeTitle");
				String content = rs.getString("noticeContent");
				int views = rs.getInt("noticeView");
				String date = rs.getString("noticeDate");
				int memberNo = rs.getInt("user_no");
				String memberName = rs.getString("user_name");

				noticeDto latestNotice = new noticeDto();
				latestNotice.setNoticeNo(no);
				latestNotice.setNoticeTitle(title);
				latestNotice.setNoticeContent(content);
				latestNotice.setNoticeView(views);
				latestNotice.setNoticeDate(date);
				latestNotice.setUserNo(memberNo);
				latestNotice.setUserName(memberName);

				return latestNotice;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
