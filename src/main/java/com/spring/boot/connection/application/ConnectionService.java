package com.spring.boot.connection.application;

import com.spring.boot.connection.domain.Connections;
import com.spring.boot.connection.domain.ConnectionRepository;
import com.spring.boot.member.domain.member.Member;
import com.spring.boot.common.error.DuplicatedException;
import com.spring.boot.common.error.NotFoundException;
import com.spring.boot.member.domain.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConnectionService {

    private final MemberRepository memberRepository;
    private final ConnectionRepository connectionRepository;

    public ConnectionService(MemberRepository memberRepository, ConnectionRepository connectionRepository) {
        this.memberRepository = memberRepository;
        this.connectionRepository = connectionRepository;
    }

    @Transactional
    public Connections follow(Long memberId, Long targetMemberId) {

        if(memberId.equals(targetMemberId)){
            throw new IllegalArgumentException("same memberId and targetMemberId error");
        }

        return memberRepository.findById(memberId)
                .map(findMember -> memberRepository
                        .findById(targetMemberId)
                        .map(targetMember->{
                            connectionRepository.findByMemberAndTargetMember(findMember, targetMember)
                                    .ifPresent(c->{
                                        throw new DuplicatedException(Connections.class,"member: "+memberId," target: "+targetMemberId);
                                    });
                            Connections connections = new Connections(findMember, targetMember);
                            return connectionRepository.save(connections);
                        })
                        .orElseThrow(()-> new NotFoundException(Member.class, targetMemberId)))
                .orElseThrow(()-> new NotFoundException(Member.class, memberId));
    }

    @Transactional
    public List<Member> getFollowing(Long memberId) {
        return memberRepository.findById(memberId)
                .map(findMember -> findMember.getFollowing()
                        .stream()
                        .map(Connections::getTargetMember)
                        .collect(Collectors.toList()))
                .orElseThrow(()-> new NotFoundException(Member.class, memberId));
    }

    @Transactional
    public List<Member> getFollowers(Long memberId) {
        return memberRepository.findById(memberId)
                .map(findMember -> findMember.getFollowers()
                        .stream()
                        .map(Connections::getMember)
                        .collect(Collectors.toList()))
                .orElseThrow(()-> new NotFoundException(Member.class, memberId));
    }

    @Transactional
    public boolean checkMemberFollowsTargetMember(Long memberId, Long targetMemberId){
        Member targetMember = memberRepository.findById(targetMemberId)
                .orElseThrow(()-> new NotFoundException(Member.class, targetMemberId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new NotFoundException(Member.class, memberId));
        return connectionRepository.findByMemberAndTargetMember(member, targetMember).isPresent();
    }
}
