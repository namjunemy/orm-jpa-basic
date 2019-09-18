package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jpql.domain.Member;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setName("nj1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query =
                em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 =
                em.createQuery("select m.name from Member m", String.class);
            Query query3 =
                em.createQuery("select m.name, m.age from Member m");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
