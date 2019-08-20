import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import hello.jpa.Member;
import hello.jpa.Team;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("njkim");
            em.persist(member);

            member.changeTeam(team);

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> findMembers = findTeam.getMembers();

            for (Member m : findMembers) {
                System.out.println(m.getUsername());
            }
            System.out.println("-------------------------");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
