package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.ImageCreationRequest;
import africa.vote.SmartVote.datas.dtos.requests.UploadImageRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.models.UserImage;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    ApiData createImage(ImageCreationRequest imageCreationRequest) throws IOException;
    ApiData uploadImage(Long id, UploadImageRequest uploadImageRequest);
    ApiData getImageById(Long id);
    ApiData deleteImage(Long id);
    List<UserImage> getAllImages();
}
