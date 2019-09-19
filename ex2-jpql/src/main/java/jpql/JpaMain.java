package jpql;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jpql.domain.Member;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setName("nj" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            String jpql = "select m from Member m order by m.age desc";

            List<Member> resultList = em.createQuery(jpql, Member.class)
                .setFirstResult(1)
                .setMaxResults(20)
                .getResultList();

            System.out.println(resultList.size());
            resultList.forEach(System.out::println);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
