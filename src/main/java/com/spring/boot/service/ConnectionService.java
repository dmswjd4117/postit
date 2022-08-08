package com.spring.boot.service;

import com.spring.boot.domain.Connections;
import com.spring.boot.domain.Member;
import com.spring.boot.error.DuplicatedException;
import com.spring.boot.error.NotFoundException;
import com.spring.boot.repository.ConnectionsRepository;
import com.spring.boot.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConnectionService {

    private final MemberRepository memberRepository;
    private final ConnectionsRepository connectionsRepository;

    public ConnectionService(MemberRepository memberRepository, ConnectionsRepository connectionsRepository) {
        this.memberRepository = memberRepository;
        this.connectionsRepository = connectionsRepository;
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
                            connectionsRepository.findByMemberAndTargetMember(findMember, targetMember)
                                    .ifPresent(c->{
                                        throw new DuplicatedException(Connections.class,"member: "+memberId," target: "+targetMemberId);
                                    });
                            Connections connection = new Connections(findMember, targetMember);
                            return connectionsRepository.save(connection);
                        })
                        .orElseThrow(()-> new NotFoundException(Member.class, targetMemberId)))
                .orElseThrow(()-> new NotFoundException(Member.class, memberId));
    }

    @Transactional
    public List<Member> getFollowings(Long memberId) {
        return memberRepository.findById(memberId)
                .map(findMember -> findMember.getFollowings()
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

}
