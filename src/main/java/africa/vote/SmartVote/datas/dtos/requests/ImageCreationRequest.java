package africa.vote.SmartVote.datas.dtos.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageCreationRequest {
    @NotBlank(message = "This field cannot be blank")
    private String name;
    @NotBlank(message = "This field cannot be blank")
    private MultipartFile file;
}
