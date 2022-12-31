package com.spring.boot.user.presentaion;


import static java.util.concurrent.CompletableFuture.supplyAsync;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.spring.boot.common.response.ApiResult;
import com.spring.boot.user.application.dto.UserInfoDto;
import com.spring.boot.role.domain.RoleName;
import com.spring.boot.image.domain.ImageUploader;
import com.spring.boot.image.infrastructure.UploadFile;
import com.spring.boot.user.application.UserService;
import com.spring.boot.user.presentaion.dto.request.UserRegisterRequest;
import com.spring.boot.user.presentaion.dto.response.UserResponse;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/member")
public class UserController {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final ImageUploader imageUploader;
  private final UserService userService;

  public UserController(ImageUploader imageUploader, UserService userService) {
    this.imageUploader = imageUploader;
    this.userService = userService;
  }

  private Optional<String> uploadProfileImage(UploadFile uploadFile) {
    String imagePath = null;
    if (uploadFile != null) {
      try {
        imagePath = imageUploader.upload(uploadFile);
      } catch (AmazonS3Exception exception) {
        log.warn("Amazon S3 error {}", exception.getMessage(), exception);
      }
    }
    return Optional.ofNullable(imagePath);
  }

  @PostMapping
  private ApiResult<UserResponse> register(
      @ModelAttribute @Valid UserRegisterRequest registerRequest,
      @RequestPart(required = false, name = "profileImage") MultipartFile file
  ) {

    UserInfoDto userInfoDto = userService.register(
        registerRequest.getName(),
        registerRequest.getEmail(),
        registerRequest.getPassword(),
        RoleName.MEMBER
    );

    UploadFile.toUploadFile(file)
        .ifPresent(uploadFile -> {
          supplyAsync(() -> uploadProfileImage(uploadFile))
              .thenAccept(image -> image.ifPresent(imagePath -> {
                userService.updateProfileImage(userInfoDto.getId(), imagePath);
              }));
        });


    return ApiResult.success(UserResponse.from(userInfoDto));
  }

}
