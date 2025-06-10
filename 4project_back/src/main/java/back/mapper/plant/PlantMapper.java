package back.mapper.plant;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import back.model.plant.Plant;

@Mapper
public interface PlantMapper {
	
	public List<Map<String, Object>> selectPlantCheck(int plant_id);
	
	public int updateSunlightInfo(
	        @Param("plantId") Integer plantId,
	        @Param("sunlightStatus") String sunlightStatus,
	        @Param("sunlightMemo") String sunlightMemo
	    );
	public int saveSunlightInfo(Plant plant);

	
	public Plant selectPlantById(String plantId);
	
	//식물 등록
	public int create(Plant plant);

    // 식물 수정
    public int update(Plant plant);

    // 식물 삭제
    public int delete(@Param("plantId") int plantId);
}
