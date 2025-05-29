package back.service.user;

import java.util.List;

import back.model.user.User;

public interface UserService {
    /**
     * 사용자 회원가입
     */
	public boolean registerUser(User user);
    
	public boolean validateUser(User user);
	
	public boolean updateUser(User user);
	
	public boolean deleteUser(User user);
    
    public User getUserById(String userId);
    
    public List<User> getUserList(User user);
    
    public boolean userM(User user);
    
    public int userIdCheck(User user);

}