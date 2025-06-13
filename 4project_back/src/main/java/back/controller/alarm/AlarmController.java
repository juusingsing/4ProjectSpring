package back.controller.alarm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import back.model.alarm.Alarm;
import back.model.combo.Combo;
import back.model.common.CustomUserDetails;
import back.model.user.User;
import back.service.alarm.AlarmService;
import back.util.ApiResponse;
import back.util.SecurityUtil;


@RestController
@RequestMapping("/api/alarm")
@Slf4j
public class AlarmController{
	
@Autowired
private AlarmService alarmService;


@PostMapping("/list.do")
public ResponseEntity<?> getAlarmList(@RequestBody Alarm alarm) {
	log.info(alarm.toString());
	List alarmList = alarmService.getList(alarm);

	return ResponseEntity.ok(new ApiResponse<>(true, "목록 조회 성공", alarmList));
}

@PostMapping("/oneList.do")
public ResponseEntity<?> getOneAlarmList(@RequestBody Alarm alarm) {
	log.info("petId, 카테고리 : 1111111111", alarm.getPetId()+ " / " + alarm.getCategory());
	List alarmList = alarmService.getOneList(alarm);

	return ResponseEntity.ok(new ApiResponse<>(true, "목록 조회 성공", alarmList));
}

@PostMapping("/create.do")
public ResponseEntity<?> createAlarm (@RequestBody Alarm alarm) {
	CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();
		
		SecurityUtil.checkAuthorization(userDetails);
		alarm.setUsersId(userDetails.getUser().getUsersId());
		boolean isCreated = alarmService.create(alarm);
		return ResponseEntity.ok(new ApiResponse<>(isCreated, isCreated ? "알람 등록 성공" : "알람 등록 실패", null));
}

@PostMapping("/update.do")
public ResponseEntity<?> updateAlarm (@RequestBody Alarm alarm) {
	CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();
		
		SecurityUtil.checkAuthorization(userDetails);
		alarm.setUsersId(userDetails.getUser().getUsersId());
		boolean isCreated = alarmService.update(alarm);
		return ResponseEntity.ok(new ApiResponse<>(isCreated, isCreated ? "알람 수정 성공" : "알람 수정 실패", null));
}




}