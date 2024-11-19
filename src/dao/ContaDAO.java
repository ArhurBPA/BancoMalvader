package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import model.Conta; // Assuming there is a model package with a Conta class

public class ContaDAO {
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("example-unit");

    public void persist(Conta conta) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(conta);
        em.getTransaction().commit();
        em.close();
    }

    public Conta findById(Long id) {
        EntityManager em = factory.createEntityManager();
        Conta conta = em.find(Conta.class, id);
        em.close();
        return conta;
    }

    public void update(Conta conta) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.merge(conta);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Long id) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Conta conta = em.find(Conta.class, id);
        if (conta != null) {
            em.remove(conta);
        }
        em.getTransaction().commit();
        em.close();
    }

}
