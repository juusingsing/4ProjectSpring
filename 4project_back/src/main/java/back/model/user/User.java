package back.model.user;

import back.model.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자 자동 생성
@EqualsAndHashCode(callSuper = true)
public class User extends Model {
	private String userId;
    private String username;
    private String password;
    private String email;
    
    //검색필터
  	private String searchText;
  	private String startDate;
  	private String endDate;
  	
  	//페이징
  	private int rn;
  	
  	private int startRow;
  	private int endRow;
  	private int page = 1;   // 기본 페이지 1
  	private int size = 10;  //기본페이지 크기 10
  	
  	private int totalCount;
  	private int totalPages;
  	private String sortField = "CREATE_DT";
  	private String sortOrder = "DESC";
  	
  	private String delYn;

}