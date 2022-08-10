package com.spring.boot.controller.member;


import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.spring.boot.aws.S3Client;
import com.spring.boot.controller.ApiResult;
import com.spring.boot.util.UploadFile;
import com.spring.boot.dto.member.MemberDto;
import com.spring.boot.dto.member.MemberRegisterRequestDto;
import com.spring.boot.dto.member.MemberRegisterResponseDto;
import com.spring.boot.domain.member.Member;
import com.spring.boot.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final S3Client s3Uploader;
    private final MemberService memberService;

    public MemberController(S3Client s3Uploader, MemberService memberService) {
        this.s3Uploader = s3Uploader;
        this.memberService = memberService;
    }

    private Optional<String> uploadProfileImage(UploadFile uploadFile) {
        String imagePath = null;
        if(uploadFile != null){
            try {
                imagePath = s3Uploader.upload(uploadFile);
            }catch (AmazonS3Exception exception){
                log.warn("Amazon S3 error {}", exception.getMessage(), exception);
            }
        }
        return Optional.ofNullable(imagePath);
    }

    @PostMapping
    private ApiResult<MemberRegisterResponseDto> register(
            @ModelAttribute MemberRegisterRequestDto registerRequest,
            @RequestPart(required = false, name = "profileImage") MultipartFile file
    ){

        Member member = memberService.register(registerRequest);

        UploadFile.toUploadFile(file)
                .ifPresent(uploadFile ->{
                    supplyAsync(()-> uploadProfileImage(uploadFile))
                            .thenAccept(image -> image.ifPresent(imagePath->{
                                memberService.updateProfileImage(member.getId(), imagePath);
                            }));
                });

        return ApiResult.success(new MemberRegisterResponseDto(
                MemberDto.from(member)
        ));
    }

}
