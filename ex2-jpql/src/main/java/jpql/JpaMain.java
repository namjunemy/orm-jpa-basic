package jpql;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jpql.domain.Member;
import jpql.dto.MemberDTO;

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

            em.flush();
            em.clear();

            // 스칼라 타입 다수를 DTO로 바로 주입 받는 방법
            List<MemberDTO> members =
                em.createQuery("select new jpql.dto.MemberDTO(m.name, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            System.out.println(members.get(0).getName());
            System.out.println(members.get(0).getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
