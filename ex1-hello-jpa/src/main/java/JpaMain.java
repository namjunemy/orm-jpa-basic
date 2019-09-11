import hello.jpa.Member;
import hello.jpa.Team;
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

            Team team = new Team();
            team.setName("Team A");
            em.persist(team);

            Member member = new Member();
            member.setUsername("nj");
            member.changeTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("nj2");
            member2.changeTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

            System.out.println("================== START");
            Member findMember = em.find(Member.class, member.getId());
            Member findMember2 = em.find(Member.class, member2.getId());

            System.out.println("Member1 TEAM : " + findMember.getTeam().getClass());
            System.out.println("Member2 TEAM : " + findMember2.getTeam().getClass());

            // JPA는 동일 트랜잭션에서 같은 객체의 동일성을 보장함. 따라서 프록시 객체의 == 도 true
            System.out.println("== : " + (findMember.getTeam().getClass() == findMember2.getTeam().getClass()));

            // intellij에서 자동생성 해주는 equals, hashcode는 위의 JPA 매커니즘을 위배함
            System.out.println("equals : " + findMember.getTeam().equals(findMember2.getTeam()));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
