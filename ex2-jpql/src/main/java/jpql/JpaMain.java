package jpql;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jpql.domain.Member;
import jpql.domain.MemberType;
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
            member.setMemberType(MemberType.ADMIN);
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // JPQL 타입 표현 : 문자열, boolean, ENUM
            String query = "select m.name, 'HELLO', TRUE From Member m " +
                "where m.memberType = :memberType";

            List<Object[]> resultList = em.createQuery(query)
                .setParameter("memberType", MemberType.ADMIN)
                .getResultList();

            for(Object[] objects : resultList) {
                System.out.println(objects[0]);
                System.out.println(objects[1]);
                System.out.println(objects[2]);
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
