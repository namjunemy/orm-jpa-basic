import hello.jpa.AddressEntity;
import hello.jpa.Member;
import hello.jpa.embedded.Address;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("nj");
            member.setHomeAddress(new Address("city", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("city1", "street1", "10001"));
            member.getAddressHistory().add(new AddressEntity("city2", "street2", "10002"));
            member.getAddressHistory().add(new AddressEntity("city3", "street3", "10003"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("================== START");
            Member findMember = em.find(Member.class, member.getId());
//
//            findMember.getAddressHistory().remove(new AddressEntity("city1", "street1", "10001"));
//            findMember.getAddressHistory().add(new AddressEntity("new city1", "street1", "10001"));


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
