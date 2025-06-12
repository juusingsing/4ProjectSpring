package back.service.calendar;

import java.util.List;

import back.model.calendar.Calendar;

public interface CalendarService {
	public List<Calendar> selectCountByDate(Calendar calendar);
	public List<Calendar> selectLogsByDate(Calendar calendar);
}
