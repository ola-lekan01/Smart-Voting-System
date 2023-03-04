package africa.vote.SmartVote.controllers;

import africa.vote.SmartVote.datas.dtos.requests.ImageCreationRequest;
import africa.vote.SmartVote.datas.dtos.requests.TokenRequest;
import africa.vote.SmartVote.datas.dtos.requests.UploadImageRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiResponse;
import africa.vote.SmartVote.services.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.hibernate.dialect.H2DurationIntervalSecondJdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/userImage/")
public class UserImageController {
    private final ImageService imageService;
    @Autowired
    public UserImageController(ImageService imageService){
        this.imageService = imageService;
    }
    @PostMapping("create")
    public ResponseEntity<?> createImage(@Valid @RequestBody ImageCreationRequest imageCreationRequest,
                                         HttpServletRequest request) throws IOException {
        var data = imageService.createImage(imageCreationRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("upload/{imageId}")
    public ResponseEntity<?> uploadImage(@PathVariable Long imageId, @RequestBody UploadImageRequest uploadImageRequest,
                                         HttpServletRequest request) throws IOException {
        var data = imageService.uploadImage(imageId, uploadImageRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("getAllImage")
    public ResponseEntity<?> getImage(HttpServletRequest request) throws IOException {
        var data = imageService.getAllImages();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("deleteImage/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId, HttpServletRequest request){
        var data = imageService.deleteImage(imageId);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(data)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
