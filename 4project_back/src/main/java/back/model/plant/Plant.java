package back.model.plant;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import back.model.Model;
import back.model.common.PostFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자 자동 생성
@EqualsAndHashCode(callSuper = true)
public class Plant extends Model{
	// 식물 기본 정보 (Plant 테이블과 매핑될 필드)
	private int plantId; // 식물ID
    private int fileId; // 파일ID
    private String plantName; // 식물이름
    private String plantType; // 식물 종류
    private Date plantPurchaseDate; // 식물 입수일
    private String plantSunPreference; // 햇빛/그늘 선호
    private String plantGrowStatus; // 식물 생육 상태
    
    // PLANT_SUNLIGHTING 테이블 관련 필드
    private int plantSunlightingId; // 일조기록ID
    private String sunlightStatus;
    private String sunlightMemo;
    
    // PLANT_REPOTTING 테이블 관련 필드
    private int plantRepottingId; // 분갈이 기록ID
    private Date repottingDate; // 분갈이 날짜
    private String soilCondition; // 흙 종류/선택
    private String repottingMemo; // 분갈이 메모
    
    // PLANT_PEST 테이블 관련 필드
    private Date plantPestDate;
    private String plantPestMemo;
    
    
    private List<PostFile> postFiles;
	
}
