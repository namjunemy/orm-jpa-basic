package hello.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("njkim");
            member.setTeam(team);
            em.persist(member);

            // 결론은 양방향 매핑일 경우 양쪽에 모두 값을 넣어주자.
            team.getMembers().add(member);


            Team findTeam = em.find(Team.class, team.getId());
            List<Member> findMembers = findTeam.getMembers();

            for (Member m : findMembers) {
                System.out.println(m.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
