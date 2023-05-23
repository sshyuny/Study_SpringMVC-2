package hello.upload.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import hello.upload.domain.UploadFile;

@Component
public class FileStore {
    
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IllegalStateException, IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename)));

        return new UploadFile(originalFilename, storeFilename);
    }

    /**
     * 서버에 저장하는 파일명을 구합니다.
     */
    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }
    
    /**
     * 확장자를 파싱해줍니다.
     * 
     * 파일을 서버에 저장할 때 확장자를 같이 저장해주면
     * 파일 이름을 보고도 파일 종류를 알 수 있기 때문에 관리가 쉬워지기 때문에 넣은 부분.
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
