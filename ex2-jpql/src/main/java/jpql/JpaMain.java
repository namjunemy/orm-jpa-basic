package jpql;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jpql.domain.Member;
import jpql.domain.Team;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setName("nj");
            member.setAge(27);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String inner = "select m from Member m inner join m.team t where t.name = :teamName";
            String outer = "select m from Member m left join m.team t where t.name = :teamName";
            String theta = "select count(m) from Member m, Team t where m.team t where m.name = t.name";

            List<Member> resultList = em.createQuery(inner, Member.class)
                .setParameter("teamName", team.getName())
                .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
