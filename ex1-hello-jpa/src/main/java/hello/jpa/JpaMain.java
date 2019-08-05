package hello.jpa;

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
            // 비영속
            Member member = new Member();
            member.setId(104L);
            member.setName("NJ");

            // 영속
            System.out.println("--- BEFORE ---");
            em.persist(member);
            System.out.println("--- AFTER ---");

            // DB에 셀렉트 쿼리가 나가지 않는다. 영속성 컨텍스트의 1차 캐시에 저장되어 있기 때문에 캐시에서 바로 가져온다.
            Member findMember = em.find(Member.class, 104L);

            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
