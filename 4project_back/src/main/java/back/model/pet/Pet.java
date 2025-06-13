package back.model.pet;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import back.model.Model;
import back.model.common.PostFile;
import back.model.user.User;
import lombok.Data;


@Data
public class Pet extends Model {
    private int animalId;            // ANIMAL_ID
    private int petId;            // PET_ID
    private Long fileId;              // FILE_ID
    
    private String animalName;        // ANIMAL_NAME
    private String animalSpecies;     // ANIMAL_SPECIES
    private LocalDate animalAdoptionDate; // ANIMAL_ADOPTIONDATE
    private LocalDate birthDate;      // BIRTH_DATE
    private String gender;            // GENDER
    private String animalMemo;        // ANIMAL_MEMO

    private String createDt;   // CREATE_DT
    private String updateDt;   // UPDATE_DT
    private String createId;          // CREATE_ID
    private String updateId;          // UPDATE_ID

    private String delYn;             // DEL_YN ('N' 또는 'Y')
    private Date parsedCreateDt; // DB에 실제로 넣을 Date 타입 값
    private List<MultipartFile> files;
    private List<PostFile> postFiles;
    
    
    private int walkId;    // ANIMAL_WALK_ID
    private Date walkDt;   // ANIMAL_RECORDED_DT  << SYSDATE값넣음
    private String walkTime;  // ANIMAL_ELAPSED_TIME
    

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    private User user;
    
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUsersId(String usersId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setUsersId(usersId);
    }

}