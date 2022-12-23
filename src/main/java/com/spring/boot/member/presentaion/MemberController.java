package com.spring.boot.member.presentaion;


import static java.util.concurrent.CompletableFuture.supplyAsync;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.spring.boot.common.response.ApiResult;
import com.spring.boot.member.domain.role.RoleName;
import com.spring.boot.common.imageUploader.ImageUploader;
import com.spring.boot.common.imageUploader.UploadFile;
import com.spring.boot.member.application.MemberService;
import com.spring.boot.member.domain.Member;
import com.spring.boot.member.presentaion.dto.MemberRegisterRequestDto;
import com.spring.boot.member.presentaion.dto.MemberRegisterResponseDto;
import com.spring.boot.member.presentaion.dto.MemberResponse;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/member")
public class MemberController {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final ImageUploader imageUploader;
  private final MemberService memberService;

  public MemberController(ImageUploader imageUploader, MemberService memberService) {
    this.imageUploader = imageUploader;
    this.memberService = memberService;
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
  private ApiResult<MemberRegisterResponseDto> register(
      @ModelAttribute MemberRegisterRequestDto registerRequest,
      @RequestPart(required = false, name = "profileImage") MultipartFile file
  ) {

    Member member = memberService.register(
        MemberRegisterRequestDto.toEntity(registerRequest),
        RoleName.MEMBER
    );

    UploadFile.toUploadFile(file)
        .ifPresent(uploadFile -> {
          supplyAsync(() -> uploadProfileImage(uploadFile))
              .thenAccept(image -> image.ifPresent(imagePath -> {
                memberService.updateProfileImage(member.getId(), imagePath);
              }));
        });

    return ApiResult.success(new MemberRegisterResponseDto(
        MemberResponse.from(member)
    ));
  }

}
