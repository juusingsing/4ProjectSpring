package back.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import back.model.user.User;

@Mapper
public interface UserMapper {
	
	public int registerUser(User user);
	
	public User getUserById(String usersId);
	
	public int updateUser(User user);
	
	public int deleteUser(User user);
	
	public int getTotalUserCount(User user);
	
	public List<User> getUserList(User user);
	
	public int userM(User user);
	
	public int usersIdCheck(User user);
}
