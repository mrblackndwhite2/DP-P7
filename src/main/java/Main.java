import domein.*;
import patterns.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 * <p>
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
        //testFetchAll();
//        testReizigerDAO();
//        testAdresDAO();
//        testOVDAO();
        testProductDAO();
    }

    private static void testReizigerDAO() {
        Session session = getSession();
        try {
            ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(session);
            AdresDAOHibernate adao = new AdresDAOHibernate(session);
            OVChipkaartDAOHibernate ovdao = new OVChipkaartDAOHibernate(session);
            ProductDAOHibernate pdao = new ProductDAOHibernate(session);

            System.out.println("=================REIZIGERDAO===================");
            System.out.println();
            System.out.println("we maken een nieuwe reiziger:");
            Reiziger r1 = new Reiziger(789, "VT", null, "Achter", Date.valueOf("2000-02-01"));
            System.out.println(r1);
            System.out.println("opgeslagen? : " + rdao.save(r1));
            System.out.println();
            System.out.println("update geboortedatum en naam");
            r1.setAchternaam("Update");
            r1.setGeboortedatum(Date.valueOf("2010-10-10"));
            System.out.println("update succes? : " + rdao.update(r1));
            System.out.println();
            System.out.println("Find reiziger by ID:");
            System.out.println(rdao.findById(789));
            System.out.println();
            System.out.println("Find reiziger by geboortedatum:");
            System.out.println(rdao.findByGbdatum("2010-10-10"));
            System.out.println();
            System.out.println("Lijst van alle reizigers:");
            for (Reiziger r : rdao.findAll()) {
                System.out.println(r);
            }
            System.out.println();
            System.out.println("Delete onze reiziger succes? : " + rdao.delete(r1));

            System.out.println("Lijst van alle reizigers na delete:");
            for (Reiziger r : rdao.findAll()) {
                System.out.println(r);
            }
        } finally {
            session.close();
        }
    }

    private static void testAdresDAO() {
        Session session = getSession();
        try {
            ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(session);
            AdresDAOHibernate adao = new AdresDAOHibernate(session);

            System.out.println("===============ADRES DAO===============");
            Reiziger r1 = new Reiziger(789, "VT", null, "Achter", Date.valueOf("2000-02-01"));
            Adres a1 = new Adres(1000, "1234ab", "teststraat", "1", "UTRECHT", r1);
            System.out.println();
            System.out.println("we have reiziger r1 with adres a1:");
            System.out.println(r1);
            System.out.println("saving adres a1: " + adao.save(a1));
            System.out.println("saving reiziger r1: " + rdao.save(r1));
            a1.setHuisnummer("26");
            a1.setWoonplaats("ROTTERDAM");
            System.out.println("try update huisnummer en woonplaats :" + adao.update(a1));
            System.out.println("Find by ID:");
            System.out.println(adao.findById(1000));
            System.out.println();
            System.out.println("Find by reiziger:");
            System.out.println(adao.findByReiziger(r1));
            System.out.println();
            System.out.println("List of all adressen");
            for (Adres a : adao.findAll()) {
                System.out.println(a);
            }

            System.out.println();
            System.out.println("Delete adres a1: " + adao.delete(a1));
            for (Reiziger r : rdao.findAll()) {
                System.out.println(r);
            }
            //System.out.println("Delete reiziger r1: " + rdao.delete(r1));

            System.out.println("List of all adressen");
            for (Adres a : adao.findAll()) {
                System.out.println(a);
            }
            System.out.println();
        } finally {
            session.close();
        }
    }

    private static void testOVDAO() {
        Session session = getSession();
        try {
            ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(session);
            OVChipkaartDAOHibernate ovdao = new OVChipkaartDAOHibernate(session);

            System.out.println("===================OVDAO TEST=====================");
            Reiziger r1 = new Reiziger(100, "voor1", null, "achter1", Date.valueOf("2000-01-01"));
            OVChipkaart ov1 = new OVChipkaart(1, Date.valueOf("2022-09-16"), 2, 5.00);
            OVChipkaart ov2 = new OVChipkaart(2, Date.valueOf("2022-11-25"), 1, 254.00);
            System.out.println("try add ov1 to r1 outcome: " + r1.tryAddOVChipkaart(ov1));
            System.out.println("try add ov2 to r1 outcome: " + r1.tryAddOVChipkaart(ov2));
            System.out.println("printing r1.getOVList:\n" + r1.getOvList());
            System.out.println();
            System.out.println("trying to save: " + rdao.save(r1));
            ovdao.save(ov1);
            ovdao.save(ov2);
            System.out.println("trying ovdao.FindByReiziger:");
            List<OVChipkaart> linkedOV = ovdao.findByReiziger(r1);
            System.out.println(linkedOV);
            System.out.println();
            System.out.println("attempting update both saldo to 50.00");
            for (OVChipkaart linked : linkedOV) {
                linked.setSaldo(50.00);
                System.out.println("update outcome: " + ovdao.update(linked));
            }
            System.out.println();
            System.out.println("now retrieving reiziger from db");
            Reiziger r2 = rdao.findById(100);
            System.out.println("retrieved reiziger ovlist:\n" + r2.getOvList());
            System.out.println();
            System.out.println("try to remove ov#2 from reiziger: " + r2.tryDeleteOVChipkaart(2));
            System.out.println("updating db: " + rdao.update(r2));
            System.out.println("OV.findbyreiziger again: ");
            System.out.println(ovdao.findByReiziger(r2));
            System.out.println();
            System.out.println("Final test retrieving reiziger again");
            Reiziger r3 = rdao.findById(100);
            System.out.println(r3);
            System.out.println(r3.getOvList());
            System.out.println("deleting reiziger: " + rdao.delete(r3));
            ovdao.delete(ov1);
            ovdao.delete(ov2);
        } finally {
            session.close();
        }
    }

    private static void testProductDAO() {
        Session session = getSession();
        try{
            ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(session);
            OVChipkaartDAOHibernate ovdao = new OVChipkaartDAOHibernate(session);
            ProductDAOHibernate pdao = new ProductDAOHibernate(session);
            System.out.println("================PRODUCT DAO TEST===================");
            Reiziger r1 = new Reiziger(100, "voor1", null, "achter1", Date.valueOf("2000-01-01"));
            OVChipkaart ov1 = new OVChipkaart(1, Date.valueOf("2022-09-16"), 2, 5.00);
            OVChipkaart ov2 = new OVChipkaart(2, Date.valueOf("2022-11-25"), 1, 254.00);
            Product p1 = new Product(50, "product1", "beschrijving1", 20.00);
            Product p2 = new Product(51, "product2", "beschrijving2", 25.00);
            ov1.tryAddProduct(p1);
            ov2.tryAddProduct(p2);
            r1.tryAddOVChipkaart(ov1);
            r1.tryAddOVChipkaart(ov2);

            System.out.println("We have 2 ov's and 2 producten:");
            System.out.println(p1);
            System.out.println(p2);
            System.out.println(ov1);
            System.out.println(ov2);

            System.out.println();
            System.out.println("We will save reiziger, which should save everything else: " + rdao.save(r1));

            System.out.println();
            System.out.println("Lastly deleting reiziger should cascade delete all associated ov");
            System.out.println("handmatig verwijderen van producten p1 en p2 erna");
            System.out.println(pdao.findAll());

            System.out.println(rdao.delete(r1));

            System.out.println(pdao.findAll());
        } finally {
            session.close();
        }
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }
}
