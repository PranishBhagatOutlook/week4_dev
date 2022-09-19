package com.example.week4;

import com.example.week4.model.Team;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class Week4ApplicationTests {

    @Autowired
    private TestEntityManager entityManager;

// entityManagerntityManager   @Test
//    void contextLoads() {
//    }

    @Test
    public void demoTest(){
        Team team = new Team();
        team.setName("Test team");
        team.setRank(10);
        
        team.setState(Team.State.PENDING);
        entityManager.persist(team);
        Team dbTeam = (Team)entityManager.getEntityManager().createQuery("SELECT t FROM Team t WHERE t.name LIKE '%team%' ").getResultList().get(0);
        assertThat(team.getName()).isEqualToIgnoringCase(dbTeam.getName());
    }
}
