import cascade.Child;
import cascade.Parent;
import hello.jpa.Member;
import hello.jpa.Team;
import hello.jpa.embedded.Address;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Address address = new Address("city", "street", "10000");

            Member member = new Member();
            member.setUsername("nj");
            member.setHomeAddress(address);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("kim");
            member2.setHomeAddress(address);
            em.persist(member2);

            member.getHomeAddress().setCity("new city");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
