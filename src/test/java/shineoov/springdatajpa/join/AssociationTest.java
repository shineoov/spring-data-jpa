package shineoov.springdatajpa.join;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import shineoov.springdatajpa.domain.v1.MemberV1;
import shineoov.springdatajpa.domain.v1.MemberV1Repository;
import shineoov.springdatajpa.domain.v1.TeamV1;
import shineoov.springdatajpa.domain.v1.TeamV1Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Slf4j
@Transactional
@Commit
@SpringBootTest
@DisplayName("연관관계 테스트")
public class AssociationTest {

    @Autowired
    MemberV1Repository memberRepository;

    @Autowired
    TeamV1Repository teamRepository;

    @Test
    @DisplayName("저장")
    void save() {
        //given
        TeamV1 team = new TeamV1("LG");
        teamRepository.save(team);

        MemberV1 member = MemberV1.builder()
                .username("Oh")
                .build();
        member.setTeam(team);

        //when
        memberRepository.save(member);

        //then
        assertAll(
                () -> assertThat(team.getId()).isNotNull(),
                () -> assertThat(team).isEqualTo(member.getTeam()),
                () -> assertThat(team.getMembers()).contains(member)
        );
    }

    @Test
    @DisplayName("수정")
    void updateTeam() {
        //given
        TeamV1 teamA = new TeamV1("LG");
        teamRepository.save(teamA);

        MemberV1 member = MemberV1.builder()
                .username("Oh")
                .build();

        member.setTeam(teamA);
        memberRepository.save(member);

        TeamV1 teamB = new TeamV1("KT");
        teamRepository.save(teamB);

        //update
        member.setTeam(teamB);

        assertAll(
                () -> assertThat(teamA.getMembers()).doesNotContain(member),
                () -> assertThat(teamB.getMembers()).contains(member),
                () -> assertThat(member.getTeam()).isEqualTo(teamB)
        );
    }

    @Test
    @DisplayName("연관 엔티티 삭제")
    void deleteTeam() {
        //given
        TeamV1 team = new TeamV1("LG");
        teamRepository.save(team);

        MemberV1 member = MemberV1.builder()
                .username("Oh")
                .team(team)
                .build();
        memberRepository.save(member);

        // delete
        member.setTeam(null);
        teamRepository.delete(team);
    }

    @Test
    @DisplayName("JPQL 조회 ")
    void lookupWithJPQL() {
        //given
        TeamV1 team = new TeamV1("LG");
        teamRepository.save(team);

        MemberV1 member = MemberV1.builder()
                .username("Oh")
                .team(team)
                .build();
        memberRepository.save(member);

        // select
        MemberV1 findMember = memberRepository.findByUsername("Oh");
        log.info("findMember={}", findMember);
        log.info("findMember.teamName={}", findMember.getTeam().getName());
    }
}
