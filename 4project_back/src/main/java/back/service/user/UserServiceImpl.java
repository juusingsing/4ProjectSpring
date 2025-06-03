package back.service.user;

import java.util.List;

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


}