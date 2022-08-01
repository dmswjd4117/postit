package com.spring.boot.controller.member;


import com.spring.boot.controller.ApiResult;
import com.spring.boot.dto.member.MemberRegisterRequestDto;
import com.spring.boot.dto.member.MemberRegisterResponseDto;
import com.spring.boot.domain.Member;
import com.spring.boot.service.MemberService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    private ApiResult<MemberRegisterResponseDto> register(
            @ModelAttribute MemberRegisterRequestDto registerRequest
    ){
        Member member = memberService.register(registerRequest);
        MemberRegisterResponseDto response = new MemberRegisterResponseDto(member);
        return ApiResult.success(response);
    }

}
