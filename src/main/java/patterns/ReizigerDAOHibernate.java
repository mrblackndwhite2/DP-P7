package patterns;

import domein.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Session session;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        Transaction tx = session.beginTransaction();
        try {
            session.save(reiziger);
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
    public boolean update(Reiziger reiziger) {
        Transaction tx = session.beginTransaction();
        try {
            session.update(reiziger);
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
    public boolean delete(Reiziger reiziger) {
        Transaction tx = session.beginTransaction();
        try {
            session.delete(reiziger);
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
    public Reiziger findById(int id) {
        try {
            Query<Reiziger> q = session.createQuery("from Reiziger where id = ?1");
            q.setParameter(1, id);
            Reiziger result = q.getSingleResult();
            return result;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            Query<Reiziger> q = session.createQuery("from Reiziger where geboortedatum = ?1");
            q.setParameter(1, Date.valueOf(datum));
            List<Reiziger> result = q.getResultList();
            return result;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            Query<Reiziger> q = session.createQuery("from Reiziger");
            List<Reiziger> result = q.getResultList();
            return result;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
