import advancedmapping.Item;
import advancedmapping.Movie;
import hello.jpa.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("creator");

            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.getReference(Member.class, member.getId());
            System.out.println("findMember = " + findMember.getClass());
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.username = " + findMember.getUsername());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
