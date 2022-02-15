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
@DisplayName("단방향 연관관계 테스트")
public class OneWayJoinTest {

    @Autowired
    MemberV1Repository memberRepository;

    @Autowired
    TeamV1Repository teamRepository;

    @Test
    @DisplayName("기본 저장")
    void save() {
        //given
        TeamV1 team = new TeamV1("LG");
        teamRepository.save(team);

        MemberV1 memberV1 = MemberV1.builder()
                .username("Oh")
                .team(team)
                .build();

        //when
        memberRepository.save(memberV1);

        //then
        assertAll(
                () -> assertThat(team.getId()).isNotNull(),
                () -> assertThat(team).isEqualTo(memberV1.getTeam())
        );
    }

    @Test
    @DisplayName("수정")
    void updateTeam() {
        //given
        TeamV1 team = new TeamV1("LG");
        teamRepository.save(team);

        MemberV1 member = MemberV1.builder()
                .username("Oh")
                .team(team)
                .build();
        memberRepository.save(member);

        TeamV1 newTeam = new TeamV1("KT");
        teamRepository.save(newTeam);

        //update
        member.setTeam(newTeam);
        log.info("member.team={}", member.getTeam());
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
}
