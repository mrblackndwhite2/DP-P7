package patterns;

import domein.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class ProductDAOHibernate implements ProductDAO {
    private Session session;

    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product p) {
        Transaction tx = session.beginTransaction();
        try {
            session.save(p);
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
    public boolean update(Product p) {
        Transaction tx = session.beginTransaction();
        try {
            session.update(p);
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
    public boolean delete(Product p) {
        Transaction tx = session.beginTransaction();
        try {
            session.delete(p);
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
    public Product findById(int id) {
        try {
            Query<Product> q = session.createQuery("from Product where id = :id");
            q.setParameter("id", id);
            Product result = q.getSingleResult();
            return result;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            Query<Product> q = session.createQuery("from Product");
            List<Product> result = q.getResultList();
            return result;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
