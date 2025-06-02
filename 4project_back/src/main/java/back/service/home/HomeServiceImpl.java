package back.service.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List getAnimalSortedByName(Home home) {
		try {
            return homeMapper.getAnimalSortedByName();
        } catch (Exception e) {
            // 💡 log.error() 사용
            log.error("Failed to get animal list sorted by name: {}", e.getMessage(), e);
            throw new RuntimeException("동물 목록(이름순)을 가져오는 중 오류 발생", e);
        }
	}

	@Override
	public List getAnimalSortedByCreateDtDesc(Home home) {
		 try {
	            return homeMapper.getAnimalSortedByCreateDtDesc();
	        } catch (Exception e) {
	            // 💡 log.error() 사용
	            log.error("Failed to get animal list sorted by creation date (desc): {}", e.getMessage(), e);
	            throw new RuntimeException("동물 목록(등록일 최신순)을 가져오는 중 오류 발생", e);
	        }
	}

	@Override
	public List getPlantSortedByName(Home home) {
		  try {
	            return homeMapper.getPlantSortedByName();
	        } catch (Exception e) {
	            // 💡 log.error() 사용
	            log.error("Failed to get plant list sorted by name: {}", e.getMessage(), e);
	            throw new RuntimeException("식물 목록(이름순)을 가져오는 중 오류 발생", e);
	        }
	}

	@Override
	public List getPlantSortedByCreateDtDesc(Home home) {
		  try {
	            return homeMapper.getPlantSortedByName();
	        } catch (Exception e) {
	            // 💡 log.error() 사용
	            log.error("Failed to get plant list sorted by name: {}", e.getMessage(), e);
	            throw new RuntimeException("식물 목록(이름순)을 가져오는 중 오류 발생", e);
	        }
	}
    
	
}