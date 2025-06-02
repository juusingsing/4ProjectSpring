package back.controller.home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import back.model.home.Home;
import back.service.home.HomeService;
import back.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/home")
@Slf4j
public class HomeController{

@Autowired
private HomeService homeService;

@PostMapping("/animal/nameList.do")
public ResponseEntity<?> getAnimalSortedByName(@RequestBody Home home) {
	log.info(home.toString());
	List<Home> animalNameList = homeService.getAnimalSortedByName(home);
	Map dataMap = new HashMap();
	dataMap.put("list", animalNameList);
	dataMap.put("home", home);
	return ResponseEntity.ok(new ApiResponse<>(true, "동물 이름순 조회 성공", dataMap));
}

@PostMapping("/animal/dateList.do")
public ResponseEntity<?> getAnimalSortedByCreateDtDesc(@RequestBody Home home) {
	log.info(home.toString());
	List<Home> animalDateList = homeService.getAnimalSortedByCreateDtDesc(home);
	Map dataMap = new HashMap();
	dataMap.put("list", animalDateList);
	dataMap.put("home", home);
	return ResponseEntity.ok(new ApiResponse<>(true, "동물 등록일 순 조회 성공", dataMap));
}

@PostMapping("/plant/nameList.do")
public ResponseEntity<?> getPlantSortedByName(@RequestBody Home home) {
	log.info(home.toString());
	List<Home> plantNameList = homeService.getPlantSortedByName(home);
	Map dataMap = new HashMap();
	dataMap.put("list", plantNameList);
	dataMap.put("home", home);
	return ResponseEntity.ok(new ApiResponse<>(true, "식물 이름순 조회 성공", dataMap));
}

@PostMapping("/plant/dateList.do")
public ResponseEntity<?> getPlantSortedByCreateDtDesc(@RequestBody Home home) {
	log.info(home.toString());
	List<Home> plantDateList = homeService.getPlantSortedByCreateDtDesc(home);
	Map dataMap = new HashMap();
	dataMap.put("list", plantDateList);
	dataMap.put("home", home);
	return ResponseEntity.ok(new ApiResponse<>(true, "식물 등록일 순 조회 성공", dataMap));
}







}