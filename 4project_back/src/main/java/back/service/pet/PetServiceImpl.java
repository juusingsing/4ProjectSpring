
package back.service.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import back.exception.HException;
import back.mapper.file.FileMapper;
import back.mapper.pet.PetMapper;
import back.mapper.pet_training_and_action.PetTrainingAndActionMapper;
import back.model.alarm.Alarm;
import back.model.common.PostFile;
import back.model.pet.Pet;
import back.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PetServiceImpl implements PetService {

	@Autowired
	private PetMapper petMapper;
	@Autowired
	private FileMapper fileMapper;
	@Autowired
	private PetTrainingAndActionMapper petTrainingAndActionMapper;

	// 반려동물 등록 처리
	@Override
	@Transactional
	public boolean registerPet(Pet pet) {
		try {
			if (pet.getCreateDt() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				Date parsedDate = sdf.parse(pet.getCreateDt());
				// 변환만 해두고 사용 안함
			}

			boolean result = petMapper.insertPet(pet) > 0;

			List<MultipartFile> files = pet.getFiles();
			int animalId = pet.getAnimalId();

			if (result && files != null && !files.isEmpty() && animalId > 0) {

				List<PostFile> fileList = FileUploadUtil.uploadFiles(files, "pet", animalId, "ANI", // 카테고리
						pet.getCreateId());

				if (!fileList.isEmpty()) {
					PostFile uploadedFile = fileList.get(0);

					boolean insertResult = fileMapper.insertFile(uploadedFile) > 0;
					if (!insertResult)
						throw new HException("파일 추가 실패");

					Long latestFileId = (long) uploadedFile.getPostFileId();

					if (latestFileId != null) {
						petMapper.updatePetFileId(latestFileId, animalId);
					}
				}

			}

			return result;
		} catch (Exception e) {
			log.error("반려동물 등록 실패 animalId={}, animalName={}", pet.getAnimalId(), pet.getAnimalName(), e);
			throw new HException("반려동물 등록 실패", e);
		}
	}

	@Override
	@Transactional
	public boolean updatePet(Pet pet) {
		boolean updated = petMapper.updatePet(pet) > 0;
		boolean finalUpdateResult = updated;

		if (updated && pet.getFiles() != null && !pet.getFiles().isEmpty()) {
			try {

				List<PostFile> fileList = FileUploadUtil.uploadFiles(pet.getFiles(), "pet", pet.getAnimalId(), "ANI",
						pet.getUpdateId());

				if (!fileList.isEmpty()) {
					PostFile newFileMetadata = fileList.get(0);

					boolean insertNewFileResult = fileMapper.insertFile(newFileMetadata) > 0;
					if (!insertNewFileResult) {
						throw new HException("새 파일 메타데이터 DB 저장 실패");
					}

					Long newFileId = (long) newFileMetadata.getPostFileId();
					if (newFileId != null) {
						finalUpdateResult = petMapper.updatePetFileId(newFileId, pet.getAnimalId()) > 0;
					} else {
						throw new HException("새로 삽입된 파일 ID 조회 실패");
					}
				} else {
					log.warn("Pet ID {} 에 대한 파일 업로드는 요청되었으나, 실제 업로드된 파일 없음.", pet.getAnimalId());
				}

			} catch (HException e) {
				log.error("반려동물 ID {} 수정 중 파일 처리 실패: {}", pet.getAnimalId(), e.getMessage(), e);
				throw e;
			} catch (Exception e) {
				log.error("반려동물 ID {} 수정 중 예상치 못한 오류 발생", pet.getAnimalId(), e);
				throw new HException("반려동물 수정 실패", e);
			}
		} else {
			// 파일이 첨부되지 않았거나 식물 기본 정보 업데이트가 실패한 경우
			// finalUpdateResult는 updated 값을 그대로 유지
		}

		return finalUpdateResult;
	}

	@Override
	@Transactional
	public boolean deletePet(int animalId, String usersId) {
		try {
			// ❌ 자식 삭제 제거
			// petTrainingAndActionMapper.logicalDeleteByAnimalId(animalId, usersId);

			// ✅ 부모(동물)만 논리 삭제
			return petMapper.deletePetByIdAndUser(animalId, usersId) > 0;
		} catch (Exception e) {
			log.error("반려동물 삭제 실패", e);
			throw new HException("삭제 실패", e);
		}
	}

	@Override
	public Pet getPetById(int animalId, String usersId, String category) {
		return petMapper.getPetByIdAndUsername(animalId, usersId, category);
	}

	@Override
	public List<Pet> petIdList(Pet pet) {
		return petMapper.petIdList(pet);
	}
}
