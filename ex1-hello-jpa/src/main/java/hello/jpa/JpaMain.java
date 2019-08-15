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

            // 역방향 연관관계 설정을 하지 않아도 JPA는 지연로딩을 통해서 member가 실제로 사용되는 시점에
            // SELECT 쿼리를 통해서 가져오기 때문에 문제는 없다.
            //team.getMembers().add(member);

            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> findMembers = findTeam.getMembers();

            for (Member m : findMembers) {
                // 팀의 Members에 넣어주지 않았지만, 조회를 할 수 있음. 이것이 지연로딩
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
