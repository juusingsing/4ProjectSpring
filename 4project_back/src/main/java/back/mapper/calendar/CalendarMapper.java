package back.mapper.calendar;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import back.model.calendar.Calendar;

@Mapper
public interface CalendarMapper {
	public List<Calendar> selectCountByDate(Calendar calendar);
	public List <Calendar> selectLogsByDate(Calendar calendar);
}
