package back.mapper.plant;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import back.model.plant.Plant;

@Mapper
public interface PlantMapper {
	//식물 등록
	public int create(Plant plant);
	
	// 식물 단건 조회
    public Plant getPlantById(@Param("plantId") String plantId);

    // 사용자 ID로 식물 리스트 조회
    public List<Plant> getPlantList(@Param("userId") String userId);

    // 식물 수정
    public int update(Plant plant);

    // 식물 삭제 (soft delete)
    public int delete(@Param("plantId") String plantId);
}
