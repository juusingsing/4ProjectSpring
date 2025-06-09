package back.mapper.home;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import back.model.home.Home;

@Mapper
public interface HomeMapper {
	
	public List<Home> getAnimalSortedByName();
	public List<Home> getAnimalSortedByCreateDtDesc();
	public List<Home> getPlantSortedByName();
	public List<Home> getPlantSortedByCreateDtDesc();

}
