package haedal.multipart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class FileDownloadController {

    // 파일 다운로드 폼으로 이동
    @GetMapping("download/fileDownloadList")
    public ModelAndView fileDownloadList() {
        ModelAndView mv = new ModelAndView();
        // 폴더에 있는 전체 파일 목록 가져오기
        File path = new File("C:/Users/user/Desktop/upload/");
        String[] fileList = path.list();

        mv.addObject("fileList", fileList);
        mv.setViewName("download/fileDownloadForm");
        return mv;
    }

    // 파일 다운로드 처리
    @GetMapping("/download/{file}")
    public String fileDownload(@PathVariable String file,
                             HttpServletResponse response) throws IOException {
        File f = new File("C:/Users/user/Desktop/upload/", file);
        // file 다운로드 설정
        response.setContentType("application/download");
        response.setContentLength((int)f.length());
        response.setHeader("Content-disposition", "attachment;filename=\"" + file + "\"");
        // response 객체를 통해서 서버로부터 파일 다운로드
        OutputStream os = response.getOutputStream();
        // 파일 입력 객체 생성
        FileInputStream fis = new FileInputStream(f);
        FileCopyUtils.copy(fis, os);
        fis.close();
        os.close();

        return "redirect:/";
    }
}
