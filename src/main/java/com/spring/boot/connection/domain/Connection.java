package com.spring.boot.connection.domain;

import com.spring.boot.user.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Table(name = "connections")
public class Connection {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "connection_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "target_member_id")
  private Member targetMember;

  @CreatedDate
  private LocalDateTime createdDate;

  public Connection(Member member, Member targetMember) {
    this.member = member;
    this.targetMember = targetMember;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("connection_id", id)
        .append("member", member)
        .append("targetMember", targetMember)
        .append("createdDate", createdDate)
        .toString();
  }

}
