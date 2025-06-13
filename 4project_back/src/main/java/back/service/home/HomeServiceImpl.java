package back.service.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import back.exception.HException;
import back.mapper.home.HomeMapper;
import back.model.home.Home;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeServiceImpl implements HomeService {
	@Autowired
	private final HomeMapper homeMapper;

	@Override
	public List<Home> getAnimalList(Home home) {
        try {
            // 디버그 로그 추가: home 객체와 usersId 값 확인
            log.info("getAnimalList 서비스 호출: home 객체 = {}", home);
            log.info("getAnimalList 서비스 호출: usersId = {}", home.getUsersId());

        	
        	List<Home>animalList = homeMapper.getAnimalList(home);
        	return animalList;
        } catch(Exception e) {
        	log.error("동물 목록 조회 실패", e);
        	throw new HException("동물 목록 조회 실패", e);
        }
		
	}

	@Override
	public List<Home> getPlantList(Home home) {
       try {
    	   // 디버그 로그 추가: home 객체와 usersId 값 확인
           log.info("getPlantList 서비스 호출: home 객체 = {}", home);
           log.info("getPlantList 서비스 호출: usersId = {}", home.getUsersId());

    	   List<Home>plantList = homeMapper.getPlantList(home);
    	   return plantList;
       } catch(Exception e) {
    	   log.error("식물 목록 조회 실패", e);
    	   throw new HException("식물 목록 조회 실패");
       }
	}

}