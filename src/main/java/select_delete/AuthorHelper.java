package select_delete;


import org.hibernate.Criteria;
import select_delete.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import select_delete.entity.Book;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


public class AuthorHelper {

    private SessionFactory sessionFactory;

    public AuthorHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Author> getAuthorList() {
        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();


        // этап подготовки запроса

        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated
        CriteriaQuery cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);// первостепенный, корневой entity (в sql запросе - from)
        Selection[] selections = {root.get("id"), root.get("name")};
        cq.select(cb.construct(Author.class, selections))
                .where(cb.like(root.<String>get("name"), "%Push%"));


        //этап выполнения запроса
        Query query = session.createQuery(cq);
        List<Author> authorList = query.getResultList();
        session.close();

        return authorList;
    }

    public Author getAuthorById(long id) {
        Session session = sessionFactory.openSession();
        Author author = session.get(Author.class, id); // получение объекта по id
        session.close();
        return author;
    }

    public Author addAuthor(Author author) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(author); // сгенерит ID и вставит в объект
        session.getTransaction().commit();
        session.close();
        return author;
    }

    public List<Author> getAuthorLastNameRegEx(String regex) {
        Session session = sessionFactory.openSession();
// этап подготовки запроса
        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated
        CriteriaQuery cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);// первостепенный, корневой entity (в sql запросе - from)
        Selection[] selections = {root.get("id"), root.get("name"), root.get("lastName")};
        cq.select(cb.construct(Author.class, selections))
                .where(cb.like(root.<String>get("lastName"), regex));
//этап выполнения запроса
        Query query = session.createQuery(cq);
        List<Author> authorList = query.getResultList();
        session.close();
        return authorList;
    }

    public List<Author> getAuthorFirstNameRegEx(String regex) {
        Session session = sessionFactory.openSession();
// этап подготовки запроса
        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated
        CriteriaQuery cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);// первостепенный, корневой entity (в sql запросе - from)
        Selection[] selections = {root.get("id"), root.get("name"), root.get("lastName")};
        cq.select(cb.construct(Author.class, selections))
                .where(cb.like(root.<String>get("name"), regex));
//этап выполнения запроса
        Query query = session.createQuery(cq);
        List<Author> authorList = query.getResultList();
        session.close();
        return authorList;
    }


    public List<Author> getAuthorFirstLastNameRegEx(String regex) {
        Session session = sessionFactory.openSession();
// этап подготовки запроса
        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated
        CriteriaQuery cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);// первостепенный, корневой entity (в sql запросе - from)
        Selection[] selections = {root.get("id"), root.get("name"), root.get("lastName")};
        cq.select(cb.construct(Author.class, selections))
                .where(cb.or(cb.like(root.<String>get("name"), regex),
                        cb.like(root.<String>get("lastName"), regex)));
//этап выполнения запроса
        Query query = session.createQuery(cq);
        List<Author> authorList = query.getResultList();
        session.close();
        return authorList;
    }


    public void deleteById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Author author = session.get(Author.class, id);
        session.delete(author); // сгенерит ID и вставит в объект
        session.getTransaction().commit();
        session.close();

    }

    public void deleteCriteriaFirstName(String regexName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
// этап подготовки запроса
        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated
        CriteriaDelete<Author> cd = cb.createCriteriaDelete(Author.class);
        Root<Author> root = cd.from(Author.class);// первостепенный, корневой entity (в sql запросе - from)
        cd.where(cb.like(root.<String>get("name"), regexName));
//этап выполнения запроса
        Query query = session.createQuery(cd);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void deleteCriteriaLastName(String regexLastName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
// этап подготовки запроса
        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated
        CriteriaDelete<Author> cd = cb.createCriteriaDelete(Author.class);
        Root<Author> root = cd.from(Author.class);// первостепенный, корневой entity (в sql запросе - from)
        cd.where(cb.like(root.<String>get("lastName"), regexLastName));
//этап выполнения запроса
        Query query = session.createQuery(cd);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void deleteCriteriaLogic(String regexName, String regexLastName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
// этап подготовки запроса
        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated
        CriteriaDelete<Author> cd = cb.createCriteriaDelete(Author.class);
        Root<Author> root = cd.from(Author.class);
        cd.where(cb.or(
                cb.and(
                        cb.like(root.get("name"), regexName),
                        cb.like(root.get("lastName"), regexLastName)
                ),
                cb.equal(root.get("name"), regexLastName)
        ));
        //этап выполнения запроса
        Query query = session.createQuery(cd);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public List<Author> getAll() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Author.class);
        List<Author> list = criteria.list();
        session.close();
        return list;
    }

    public static void fillTables() {
        AuthorHelper ah = new AuthorHelper();
        BookHelper bh = new BookHelper();
        Author author = new Author();
        Book book = new Book();

        // add authors
        author.setName("Lesya");
        author.setLastName("Ukrainka");
        ah.addAuthor(author);
        book.setAuthor_id(1);
        book.setName("Kaminnyi hospodar");
        bh.addBook(book);


        author.setName("Ivan");
        author.setLastName("Kotlyarevsky");
        ah.addAuthor(author);
        book.setAuthor_id(2);
        book.setName("Eneida");
        bh.addBook(book);
        book.setAuthor_id(2);
        book.setName("Sobor. Kniaz Volodymyr");
        bh.addBook(book);

        author.setName("Mykhailo");
        author.setLastName("Kotsiubynsky");
        ah.addAuthor(author);
        book.setAuthor_id(3);
        book.setName("Tini zabutykh predkiv");
        bh.addBook(book);
        book.setAuthor_id(3);
        book.setName("Plakhy zholti");
        bh.addBook(book);

        author.setName("Ivan");
        author.setLastName("Franko");
        ah.addAuthor(author);
        book.setAuthor_id(4);
        book.setName("Zakhar Berkut");
        bh.addBook(book);
        book.setAuthor_id(4);
        book.setName("Zahar Berkut (adapted for children)");
        bh.addBook(book);

        author.setName("Marko");
        author.setLastName("Vovchok");
        ah.addAuthor(author);
        book.setAuthor_id(5);
        book.setName("Chorna rada");
        bh.addBook(book);

        author.setName("Panteleymon");
        author.setLastName("Kulish");
        ah.addAuthor(author);
        book.setAuthor_id(6);
        book.setName("Chorna rada");
        bh.addBook(book);

        book.setAuthor_id(1);
        book.setName("Blue Rose");
        bh.addBook(book);
        book.setAuthor_id(1);
        book.setName("Pisnia pro kniazia Ihoria");
        bh.addBook(book);
    }
}
