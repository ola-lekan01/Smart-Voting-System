package africa.vote.SmartVote.datas.dtos.requests;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class UploadImageRequest {
    private MultipartFile file;
}
