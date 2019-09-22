package jpql;

import jpql.domain.Member;
import jpql.domain.MemberType;
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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setName("관리자1");
            member.setAge(27);
            member.setMemberType(MemberType.ADMIN);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String query =
                "select coalesce(m.name, '이름 없는 회원') from Member m";

            String query2 =
                "select nullif(m.name, '관리자') from Member m";

            List<String> result = em.createQuery(query, String.class)
                .getResultList();

            System.out.println(result);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
