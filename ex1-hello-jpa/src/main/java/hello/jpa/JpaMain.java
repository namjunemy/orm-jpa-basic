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
            Member member = new Member();
            member.setUsername("NJ");

            System.out.println("기본키 IDENTITY 전략에서는 DB에 들리지 않으면 ID를 알 수 없다.");
            em.persist(member);
            System.out.println(member.getId());
            System.out.println("persist시에 바로 DB 쿼리 날려서 ID값을 가져온다.");
            System.out.println("ID값을 가져올 때 select문으로 가져오지 않는다. jdbc api가 주는 insert문의 리턴 값 가져옴");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
