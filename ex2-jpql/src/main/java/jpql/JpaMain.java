package jpql;

import jpql.domain.Member;
import jpql.domain.MemberType;
import jpql.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
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

            String query = "select m from Member m join fetch m.team";

            List<Member> result = em.createQuery(query, Member.class)
                .getResultList();

            result.forEach(m -> System.out.println(m.getName() + ":" + m.getTeam().getName()));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
