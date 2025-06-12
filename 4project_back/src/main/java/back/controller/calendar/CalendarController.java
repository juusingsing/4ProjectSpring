package back.controller.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import back.model.calendar.Calendar;
import back.model.diary.Diary;
import back.service.calendar.CalendarService;
@RestController
@RequestMapping("/api/calendar")
@Slf4j
public class CalendarController {
	@Autowired
	private CalendarService calendarService;
	
	@PostMapping("/dot.do")
	public ResponseEntity<?> selectCountByDate(@RequestBody Calendar calendar){
	    return ResponseEntity.ok(calendarService.selectCountByDate(calendar));
	}
	@PostMapping("/log.do")
	public ResponseEntity<?> selectLogsByDate(@RequestBody Calendar calendar){
		return ResponseEntity.ok(calendarService.selectLogsByDate(calendar));
	}
}
