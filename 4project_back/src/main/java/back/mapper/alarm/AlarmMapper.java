package back.mapper.alarm;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import back.model.alarm.Alarm;


@Mapper
public interface AlarmMapper {
	
	public List<Alarm> OneList(Alarm alarm);
	
	public List<Alarm> list();
	
	public int create(Alarm alarm);
	
	public int update(Alarm alarm);
	
	
}
