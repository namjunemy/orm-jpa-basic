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
            em.persist(member);

            // 연관관계 편의 메소드를 생성해서 양방향 매핑의 값 설정을 세트화 시키자.
            member.setTeam(team);
            
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
