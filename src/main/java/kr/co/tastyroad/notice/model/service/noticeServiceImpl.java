package kr.co.tastyroad.notice.model.service;

import kr.co.tastyroad.notice.model.dao.noticeDao;
import kr.co.tastyroad.notice.model.dto.noticeDto;

import java.util.ArrayList;

import kr.co.tastyroad.common.PageInfo;

public class noticeServiceImpl implements noticeService {

    noticeDao noticeDao;

    public noticeServiceImpl() {
        noticeDao = new noticeDao();
    }

    @Override
    public ArrayList<noticeDto> getList(PageInfo pi, String category, String searchText) {
        return noticeDao.getList(pi, category, searchText);
    }

    @Override
    public int getListCount(String category, String searchText) {
        return noticeDao.getListCount(category, searchText);
    }

    @Override
    public int enroll(noticeDto noticeDto) {
        return noticeDao.enroll(noticeDto);
    }

    @Override
    public noticeDto getDetail(int noticeNo) {
        noticeDto result = noticeDao.getDetail(noticeNo);

        noticeDao.getWriter(result);
        noticeDao.getUserType(result);
        noticeDao.getFileName(result);

        int resultView = noticeDao.setView(result.getNoticeNo());

        if (resultView == 1) {
            return result;
        }

        return null;
    }

    @Override
    public noticeDto getEditForm(int noticeNo) {
        noticeDto result = noticeDao.getDetail(noticeNo);
        noticeDao.getWriter(result);
        noticeDao.getFileName(result);

        return result;
    }

    @Override
    public int setEdit(noticeDto noticeDto) {
        return noticeDao.setEdit(noticeDto);
    }

    @Override
    public int setDelete(int noticeNo) {
        return noticeDao.setDelete(noticeNo);
    }

    @Override
    public noticeDto selectNo(noticeDto noticeDto) {
        return noticeDao.selectNo(noticeDto);
    }

    @Override
    public int fileUpload(noticeDto noticeDto) {
        return noticeDao.fileUpload(noticeDto);
    }

    @Override
    public void getFileName(noticeDto result) {
        if (result.getFileName() != null && !result.getFileName().isEmpty()) {
            result.setFilePath("/assets/uploads/notice/");
            result.setFileName("boardNo_" + result.getNoticeNo() + ".jpg");
        } else {
            result.setFilePath(null);
            result.setFileName(null);
        }
    }

    @Override
    public int setFileDelete(int fileNo) {
        return noticeDao.setFileDelete(fileNo);
    }

    @Override
    public boolean deleteNotice(int noticeNo) {
        return noticeDao.deleteNotice(noticeNo);
    }

    @Override
    public noticeDto getLatestNotice() {
        return noticeDao.getLatestNotice();
    }

    @Override
    public ArrayList<noticeDto> getNoticeList() {
        return noticeDao.getNoticeList();
    }
}