package back.model.plant;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import back.model.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자 자동 생성
@EqualsAndHashCode(callSuper = true)
public class Plant extends Model{
	
	private int plantId; // 식물ID
    private String usersId; // 사용자ID
    private int fileId; // 파일ID
    
    private String plantName; // 식물이름
    private String plantType; // 식물 종류
    private Date plantPurchaseDate; // 식물 입수일
    private String plantSunPreference; // 햇빛/그늘 선호
    private String plantGrowStatus; // 식물 생육 상태
    
    private MultipartFile file;
	
}
