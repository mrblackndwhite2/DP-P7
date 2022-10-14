package patterns;

import domein.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private Session session;

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Adres adres) {
        Transaction tx = session.beginTransaction();
        try {
            session.save(adres);
            session.flush();
            tx.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        Transaction tx = session.beginTransaction();
        try {
            session.update(adres);
            session.flush();
            tx.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        Transaction tx = session.beginTransaction();
        try {
            session.delete(adres);
            session.flush();
            tx.commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            Query<Adres> q = session.createQuery("from Adres where reiziger = :r");
            q.setParameter("r", reiziger);
            Adres result = q.getSingleResult();
            return result;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Adres findById(int id) {
        try {
            Query<Adres> q = session.createQuery("from Adres where id = :id");
            q.setParameter("id", id);
            Adres result = q.getSingleResult();
            return result;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        try {
            Query<Adres> q = session.createQuery("from Adres");
            List<Adres> result = q.getResultList();
            return result;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
