package jpql;

import jpql.domain.Member;
import jpql.domain.Team;

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
            Team teamA = new Team();
            teamA.setName("팀A");
            Team teamB = new Team();
            teamB.setName("팀B");

            em.persist(teamA);
            em.persist(teamB);

            Member member = new Member();
            member.setName("회원1");
            member.changeTeam(teamA);
            Member member2 = new Member();
            member2.setName("회원2");
            member2.changeTeam(teamA);
            Member member3 = new Member();
            member3.setName("회원3");
            member3.changeTeam(teamB);

            em.persist(member);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select t from Team t join fetch t.members";

            List<Team> result = em.createQuery(query, Team.class)
                .getResultList();

            for (Team t : result) {
                System.out.println(t.getName() + ":" + t.getMembers().size());
                for (Member m : t.getMembers()) {
                    System.out.println("--> " + t.getName() + ":" + m.getName());
                }
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
