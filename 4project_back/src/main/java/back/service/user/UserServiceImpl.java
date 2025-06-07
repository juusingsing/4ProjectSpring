package back.service.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import back.exception.HException;
import back.mapper.user.UserMapper;
import back.model.user.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
    private UserMapper userMapper;
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

    
    @Override
	@Transactional
	public boolean validateUser(User user) {
    	
    	try {
            User dbUser = userMapper.getUserById(user.getUsersId());
            if (dbUser == null) return false;
            
            String encryptedPassword = passwordEncoder.encode(user.getUsersPassword());
            return passwordEncoder.matches(dbUser.getUsersPassword(), encryptedPassword);
        } catch (Exception e) {
        	log.error("로그인 실패", e);
			throw new HException("로그인 실패", e);
        }
	}

    @Override
	@Transactional
    public boolean registerUser(User user) {

        try {
            String password = user.getUsersPassword();
            user.setUsersPassword(password != null ? passwordEncoder.encode(password) : null);
            return userMapper.registerUser(user) > 0;
        } catch (Exception e) {
        	log.error("회원가입 실패", e);
        	e.printStackTrace();
			throw new HException("회원가입 실패", e);
        }
    }
	
    @Override
	public User getUserById(String userId) {
    	try {
            return userMapper.getUserById(userId);
        } catch (Exception e) {
        	log.error("회원가입 실패", e);
			throw new HException("회원가입 실패", e);
        }
	}
    
    @Override
	public boolean updateUser(User user) {
    	String password = user.getUsersPassword();
    	user.setUsersPassword(password != null ? passwordEncoder.encode(password) : null);
    	
    	return userMapper.updateUser(user) > 0;
	}
    
    @Override
	public boolean deleteUser(User user) {
    	try {
            String password = user.getUsersPassword();
            user.setUsersPassword(password != null ? passwordEncoder.encode(password) : null);
            return userMapper.deleteUser(user) > 0;
        } catch (Exception e) {
            log.error("사용자 탈퇴 중 오류", e);
            throw new HException("사용자 탈퇴 실패", e);
        }
	}

	@Override
	public List<User> getUserList(User user) {
		try {
			int page = user.getPage();
			int size = user.getSize();
			
			int totalCount = userMapper.getTotalUserCount(user);
			int totalPages = (int) Math.ceil((double) totalCount / size);
			
			int startRow = (page - 1) * size + 1;
			int endRow = page *size;
			
			user.setTotalCount(totalCount);
			user.setTotalPages(totalPages);
			user.setStartRow(startRow);
			user.setEndRow(endRow);
			
			List list = userMapper.getUserList(user);
			
			return list;
		} catch (Exception e) {
			log.error("유저 목록 조회 실패", e);
			throw new HException("유저 목록 조회 실패", e);
		}
	}

	@Override
	public boolean userM(User user) {
		try {
            String password = user.getUsersPassword();
            user.setUsersPassword(password != null ? passwordEncoder.encode(password) : null);
            return userMapper.userM(user) > 0;
        } catch (Exception e) {
            log.error("회원관리 중 오류", e);
            throw new HException("회원관리 실패", e);
        }
	}

	@Override
	public boolean usersIdCheck(User user) {
		try {
            int count = userMapper.usersIdCheck(user);
            return count > 0;
        } catch (Exception e) {
            log.error("아이디 중복 체크 중 오류 발생!", e);
            throw new HException("아이디 중복 체크 실패", e);
        }
	}

	@Override
	public boolean isUserIdDuplicate(String usersId) {
		try {
			int count = userMapper.checkUserIdDuplicate(usersId);
			return count > 0; // 이미 DB에 존재하면 true, 존재하지 않으면 false
		} catch (Exception e) {
			log.error("아이디 중복 체크 중 오류", e);
			throw new HException("아이디 중복 체크 실패", e);
		}
	}
	
	 @Override
	 public List<User> findUsersByInfo(String email) {
	     try {
	         return userMapper.selectUsersByEmail(email);
	     } catch (Exception e) {
	         log.error("이메일로 사용자 목록 조회 중 오류", e);
	         throw new HException("사용자 조회 실패", e);
	     }
	 }

	 @Override
	 public User findUserByUserIdAndEmail(String usersId, String usersEmail) {
	     try {
	         Map<String, Object> params = new HashMap<>();
	         params.put("usersId", usersId);
	         params.put("usersEmail", usersEmail);
	         return userMapper.findUserByUserIdAndEmail(params);
	     } catch (Exception e) {
	         log.error("아이디와 이메일로 사용자 조회 중 오류", e);
	         throw new HException("사용자 조회 실패", e);
	     }
	 }
	
	 @Override
	 public boolean updatePassword(String usersId, String encodedPassword) {
	     try {
	         return userMapper.updatePassword(usersId, encodedPassword) > 0;
	     } catch (Exception e) {
	         log.error("비밀번호 업데이트 중 오류", e);
	         throw new HException("비밀번호 업데이트 실패", e);
	     }
	 }
	
	 @Override
	 public boolean resetPassword(String usersId, String newPassword) {
	     try {
	         User user = userMapper.findByUserId(usersId);
	         if (user == null) return false;

	         String encodedPassword = passwordEncoder.encode(newPassword);
	         return userMapper.updatePassword(usersId, encodedPassword) > 0;
	     } catch (Exception e) {
	         log.error("비밀번호 재설정 중 오류", e);
	         throw new HException("비밀번호 재설정 실패", e);
	     }
	 }
	
	
	@Override
	public boolean isEmailRegistered(String email) {
		try {
		    int count = userMapper.isEmailRegistered(email);
		    return count > 0;
		} catch (Exception e) {
	    	log.error("이메일 등록 여부 중 확인 실패했습니다.");
	    	throw new HException("이메일 등록 여부 확인 실패");
	    }
	}
	@Override
	public User findByEmail(String email) {
	    try {
	        List<User> users = userMapper.selectUsersByEmail(email);
	        return users != null && !users.isEmpty() ? users.get(0) : null;
	    } catch (Exception e) {
	        log.error("이메일로 사용자 조회 중 오류", e);
	        throw new HException("이메일 조회 실패", e);
	    }
	}
}