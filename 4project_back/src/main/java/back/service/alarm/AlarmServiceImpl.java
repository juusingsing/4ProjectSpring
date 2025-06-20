package back.service.alarm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import back.mapper.alarm.AlarmMapper;
import back.model.alarm.Alarm;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AlarmServiceImpl implements AlarmService {
	
    @Autowired
    private AlarmMapper alarmMapper;
	
    @Override
	public List getOneList(Alarm alarm) {
    	
    	return alarmMapper.OneList(alarm);
	}
    

	@Override
	public List<Alarm> getList(Alarm alarm) {
		log.info("list 호출!!!!!!!!");
		return alarmMapper.list(alarm);
	}



	@Override
	public boolean create(Alarm alarm) {
		boolean result = alarmMapper.create(alarm) > 0;
		return result;
	}


	
	@Override
	public boolean update(Alarm alarm) {
		boolean result = alarmMapper.update(alarm) > 0;
		return result;
	}
	
	@Override
	public boolean AllUpdate(Alarm alarm) {
		boolean result = alarmMapper.AllUpdate(alarm) > 0;
		return result;
	}
	
	@Override
	public boolean delete(Alarm alarm) {
		boolean result = alarmMapper.delete(alarm) > 0;
		return result;
	}


	@Override
	public boolean petDelete(Alarm alarm) {
		boolean result = alarmMapper.petDelete(alarm) > 0;
		return result;
	}


	@Override
	public List<Alarm> alarmIdList(Alarm alarm) {
		return alarmMapper.alarmIdList(alarm);
	}
    


}