package back.service.home;

import java.io.IOException;
import java.util.List;

import back.model.home.Home;

public interface HomeService {
	public List getAnimalSortedByName(Home home);
	public List getAnimalSortedByCreateDtDesc(Home home);
	public List getPlantSortedByName(Home home);
	public List getPlantSortedByCreateDtDesc(Home home);
}