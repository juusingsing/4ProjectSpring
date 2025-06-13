package back.service.alarm;

import java.util.List;

import back.model.alarm.Alarm;


public interface AlarmService {
	
	
	public List getOneList(Alarm alarm);
    
    public List getList(Alarm alarm);
    
    public boolean create(Alarm alarm);
    
    public boolean update(Alarm alarm);
    
    public boolean AllUpdate(Alarm alarm);

}