package back.service.write;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import back.exception.HException;
import back.mapper.board.BoardMapper;
import back.mapper.file.FileMapper;
import back.model.board.Board;
import back.model.board.Comment;
import back.model.common.PostFile;
import back.util.FileUploadUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class WriteServiceImpl implements BoardService {
    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private FileMapper fileMapper;
    

    
    @Override
	public Board getBoardById(String boardId) {
    	
    	try {
			Board board = boardMapper.getBoardById(boardId);
			
			//파일 목록 조회
			board.setPostFiles(fileMapper.getFilesByBoardId(boardId));
			
			//댓글 목록 조회
			board.setComments(boardMapper.getCommentsByBoardId(boardId));
			return board;
    	} catch (Exception e) {
            log.error("실패", 0);
            throw new HException("실패", e);
        }
				
	}
	
    @Override
	@Transactional
	public boolean createBoard(Board board) throws NumberFormatException, IOException {
       
        	boolean result = boardMapper.create(board) > 0;
	
			if (result) {
				//업로드된 파일들을 처리하여 PostFile 객체 리스트 반환
				List<PostFile> fileList = FileUploadUtil.uploadFiles(board.getFiles(), "board",
							Integer.parseInt(board.getBoardId()), board.getCreateId());
						
				for (PostFile PostFile : fileList) {
					boolean createResult = fileMapper.insertFile(PostFile) > 0;
					if (!createResult) throw new HException("파일 추가 실패");
				}
			}
			return result;
    
    }
	
	@Override
	@Transactional
	public boolean updateBoard(Board board) {
        try {
            boolean result = boardMapper.update(board) > 0;
            
            if (result) {
            	
            	List<MultipartFile> files = board.getFiles();
            	String remainingFileIds = board.getRemainingFileIds();
            	
            	List<PostFile> existingFiles = fileMapper.getFilesByBoardId(board.getBoardId());
            	

            	for (PostFile existing : existingFiles) {
            		if (!remainingFileIds.contains(String.valueOf(existing.getFileId()))) {
            			existing.setUpdateId(board.getUpdateId());
            			boolean deleteResult = fileMapper.deleteFile(existing) > 0 ;
            			if (!deleteResult) throw new HException("파일 삭제 실패");
            		}
            	}
            	
            	if (files != null) {
            		List<PostFile> uploadedFiles = FileUploadUtil.uploadFiles(files, "board",
            				Integer.parseInt(board.getBoardId()), board.getUpdateId());
            		for (PostFile postFile : uploadedFiles) {
            			boolean insertResult = fileMapper.insertFile(postFile) > 0;
            			if (!insertResult) throw new HException("파일 추가 실패");
            		}
            	}
            }
            
            return result;
        } catch (Exception e) {
            log.error("게시글 수정 실패", 0);
            throw new HException("게시글 수정 실패", e);
        }
    }
	
	@Override
	@Transactional
	public boolean deleteBoard(Board board) {
		try {
			return boardMapper.delete(board) > 0;
		} catch (Exception e) {
			log.error("게시글 삭제 실패", e);
			throw new HException("게시글 삭제 실패", e);
		}
    }


	@Override
	@Transactional
	public List getBoardList(Board board) {
		
		int page = board.getPage();
		int size = board.getSize();
		
		int totalCount = boardMapper.getTotalBoardCount(board);
		int totalPages = (int) Math.ceil((double) totalCount / size);
		
		int startRow = (page - 1) * size + 1;
		int endRow = page *size;
		
		board.setTotalCount(totalCount);
		board.setTotalPages(totalPages);
		board.setStartRow(startRow);
		board.setEndRow(endRow);
		
		List list = boardMapper.getBoardList(board);
		
		return list;
	}
	
	@Override
	@Transactional
	public boolean createComment(Comment comment) {
		try {
			return boardMapper.insertComment(comment) > 0;
		} catch (Exception e) {
			log.error("댓글 생성 실패", e);
			throw new HException("댓글 생성 실패", e);
		}
	}
	
	@Override
	@Transactional
	public boolean updateComment(Comment comment) {
		try {
			return boardMapper.updateComment(comment) > 0;
		} catch (Exception e) {
			log.error("댓글 수정 실패", e);
			throw new HException("댓글 수정 실패", e);
		}
	}
	
	@Override
	@Transactional
	public boolean deleteComment(Comment comment) {
		try {
			return boardMapper.deleteComment(comment) > 0;
		} catch (Exception e) {
			log.error("댓글 삭제 실패", e);
			throw new HException("댓글 삭제 실패", e);
		}
	}
    


}