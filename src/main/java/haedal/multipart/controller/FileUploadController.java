package haedal.multipart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class FileUploadController {
    @GetMapping("/")
    public String home() {
        return "upload/fileUploadForm";
    }

    // 파일 업로드 처리 (파일명 중복되지 않도록 파일 이름 변경해서 업로드)
    @PostMapping("/upload/fileUpload")
    public String fileUpload(@RequestParam("uploadFile") MultipartFile file) throws IOException {
        String savedFileName = "";
        // 1. 파일 저장 경로 설정 : 실제 서비스되는 위치(프로젝트 외부에 저장)
        String uploadPath = "C:/Users/user/Desktop/upload/";
        // 2. 원본 파일 이름 알아오기
        String originalFileName = file.getOriginalFilename();
        // 3. 파일 이름 중복되지 않게 이름 변경(서버에 저장할 이름) UUID 사용
        UUID uuid = UUID.randomUUID();
        savedFileName = uuid.toString() + "_" + originalFileName;
        // 4. 파일 생성
        File file1 = new File(uploadPath + savedFileName);
        // 5. 서버로 전송
        file.transferTo(file1);
        return "redirect:/";
    }

    // 여러 개의 파일 업로드
    @RequestMapping("/upload/fileUploadMultiple")
    public String fileUploadMultiple(@RequestParam("uploadFileMulti") ArrayList<MultipartFile> files) throws IOException {
        String savedFileName = "";
        // 1. 파일 저장 경로 설정 : 실제 서비스되는 위치(프로젝트 외부에 저장)
        String uploadPath = "C:/Users/user/Desktop/upload/";
        // 여러 개의 원본 파일을 저장할 리스트 생성
        ArrayList<String> originalFileNameList = new ArrayList<String>();
        for(MultipartFile file : files) {
            // 2. 원본 파일 이름 알아오기
            String originalFileName = file.getOriginalFilename();
            // 3. 파일 이름을 리스트에 추가
            originalFileNameList.add(originalFileName);
            // 4. 파일 이름 중복되지 않게 이름 변경(서버에 저장할 이름) UUID 사용
            UUID uuid = UUID.randomUUID();
            savedFileName = uuid.toString() + "_" + originalFileName;
            // 5. 파일 생성
            File file1 = new File(uploadPath + savedFileName);
            // 6. 서버로 전송
            file.transferTo(file1);
        }
        return"redirect:/";
    }
}
