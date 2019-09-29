package jpql;

import jpql.domain.Member;
import jpql.domain.Team;

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
            Team teamA = new Team();
            teamA.setName("팀A");
            Team teamB = new Team();
            teamB.setName("팀B");

            em.persist(teamA);
            em.persist(teamB);

            Member member = new Member();
            member.setName("회원1");
            member.setAge(0);
            member.changeTeam(teamA);
            Member member2 = new Member();
            member2.setName("회원2");
            member2.setAge(0);
            member2.changeTeam(teamA);
            Member member3 = new Member();
            member3.setName("회원3");
            member3.setAge(0);
            member3.changeTeam(teamB);

            em.persist(member);
            em.persist(member2);
            em.persist(member3);

            String query = "update Member m set m.age = 20";

            //이 시점에 FLUSH 자동 호출. insert, update 나간다. DB에 SQL이 나가므로 flush 자동.
            int resultCount = em.createQuery(query)
                .executeUpdate();
            
            System.out.println(resultCount);

            // flush 는 DB에만 반영하지 영속성 컨텍스트를 비우지 않는다.
            // 따라서,  벌크 연산이 나가고 영속성 컨텍스트 에는 아직도 member의 age가 0이다.
//            em.clear();
            Member resultMember = em.find(Member.class, member.getId());
            System.out.println(resultMember.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
