package select_delete;


import select_delete.entity.Author;
import select_delete.entity.Book;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BookHelper {

    private SessionFactory sessionFactory;

    public BookHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    List<Book> getBookList() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated
        CriteriaQuery cq = cb.createQuery(Book.class);
        Root<Author> root = cq.from(Book.class);// первостепенный, корневой entity (в sql запросе - from)
        cq.select(root);
        //этап выполнения запроса
        Query query = session.createQuery(cq);
        List<Book> bookList = query.getResultList();
        session.close();
        return bookList;
    }

    public void deleteById(long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Book book = session.get(Book.class, id);
        session.delete(book); // сгенерит ID и вставит в объект
        session.getTransaction().commit();
        session.close();

    }

    public void deleteByAuthor(Author author) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // этап подготовки запроса
        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Book> cd = cb.createCriteriaDelete(Book.class);
        Root<Book> root = cd.from(Book.class);
        cd.where(cb.equal(root.get("author"), author));
        Query query = session.createQuery(cd);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public Book getBookById(long id) {
        Session session = sessionFactory.openSession();
        Book book = session.get(Book.class, id);
        session.close();
        return book;
    }

    public void addBook(Book book) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
        session.close();
    }

    public List<Book> getAll() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Book.class);
        List<Book> list = criteria.list();
        session.close();
        return list;
    }


}
