package patterns;

import domein.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private Session session;

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ovchipkaart) {
        Transaction tx = session.beginTransaction();
        try {
            session.save(ovchipkaart);
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
    public boolean update(OVChipkaart ovchipkaart) {
        Transaction tx = session.beginTransaction();
        try {
            session.update(ovchipkaart);
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
    public boolean delete(OVChipkaart ovchipkaart) {
        Transaction tx = session.beginTransaction();
        try {
            session.delete(ovchipkaart);
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
    public OVChipkaart findByKaartnummer(int kaartnummer) {
        try {
            Query<OVChipkaart> q = session.createQuery("from OVChipkaart where kaart_nummer = :nummer");
            q.setParameter("nummer", kaartnummer);
            OVChipkaart result = q.getSingleResult();
            return result;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            Query<OVChipkaart> q = session.createQuery("from OVChipkaart where reiziger = :r");
            q.setParameter("r", reiziger);
            List<OVChipkaart> result = q.getResultList();
            return result;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        try {
            Query<OVChipkaart> q = session.createQuery("from OVChipkaart");
            List<OVChipkaart> result = q.getResultList();
            return result;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
