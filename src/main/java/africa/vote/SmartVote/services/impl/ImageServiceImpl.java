package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.ImageCreationRequest;
import africa.vote.SmartVote.datas.dtos.requests.UploadImageRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.models.UserImage;
import africa.vote.SmartVote.datas.repositories.UserImageRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class ImageServiceImpl implements ImageService {
    private final UserImageRepository userImageRepository;
    @Autowired
    public ImageServiceImpl(UserImageRepository userImageRepository){
        this.userImageRepository = userImageRepository;
    }
    @Override
    public ApiData createImage(ImageCreationRequest imageCreationRequest) throws IOException {
        UserImage userImage = UserImage.builder()
                .name(imageCreationRequest.getName())
                .image(imageCreationRequest.getFile().getBytes())
                .build();

        userImageRepository.save(userImage);

        return ApiData.builder()
                .data("Image created Successfully")
                .build();
    }

    @Override
    public ApiData uploadImage(Long id, UploadImageRequest uploadImageRequest) {
        try{
            UserImage foundImage =  userImageRepository.findById(id).orElseThrow(() -> new GenericException("Image not found"));
            foundImage.setImage(uploadImageRequest.getFile().getBytes());
            userImageRepository.save(foundImage);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

        return ApiData.builder()
                .data("Image uploaded Successfully")
                .build();
    }

    @Override
    public ApiData getImageById(Long id) {
        userImageRepository.findById(id);

        return ApiData.builder()
                .data("Success")
                .build();
    }

    @Override
    public ApiData deleteImage(Long id) {
        userImageRepository.findById(id);

        return ApiData.builder()
                .data("Image deleted successfully")
                .build();
    }

    @Override
    public List<UserImage> getAllImages() {
        return userImageRepository.findAll();
    }
}