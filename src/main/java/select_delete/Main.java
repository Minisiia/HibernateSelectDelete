package select_delete;

import select_delete.entity.Author;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * Из пакета ex_002_select_where написать отдельный метод для выборки по поиску выражения
 * и в пакете ex_003_delete методы createCriteria и createCriteriaLogic переписать правильно.
 */
public class Main {

    private static final Logger LOG = Logger.getLogger(AuthorHelper.class.getName());

    public static void main(String[] args) {
        AuthorHelper ah = new AuthorHelper();
        AuthorHelper.fillTables();
        List<Author> authorList = ah.getAuthorLastNameRegEx("%ot%");
        System.out.println((char) 27 + "[34m" + "Selection by last name:" + (char) 27 + "[38m");
        for (Author author : authorList)
            LOG.info(author.getId() + " " + author.getName() + " " + author.getLastName());
        authorList = ah.getAuthorFirstLastNameRegEx("%a%");
        System.out.println((char) 27 + "[34m" + "Selection by first name:" + (char) 27 + "[38m");
        for (Author author : authorList)
            LOG.info(author.getId() + " " + author.getName() + " " + author.getLastName());
        authorList = ah.getAuthorFirstLastNameRegEx("%y%");
        System.out.println((char) 27 + "[34m" + "Selection by first name or last name:" + (char) 27 + "[38m");
        for (Author author : authorList)
            LOG.info(author.getId() + " " + author.getName() + " " + author.getLastName());

        ah.deleteCriteriaLastName("%otl%");
        authorList = ah.getAll();
        System.out.println((char) 27 + "[34m" + "Deletion by last name %otl%:" + (char) 27 + "[38m");
        for (Author author : authorList)
            LOG.info(author.getId() + " " + author.getName() + " " + author.getLastName());//       ah.deleteCriteriaFirstName("%r%");
        ah.deleteCriteriaFirstName("%ark%");
        authorList = ah.getAll();
        System.out.println((char) 27 + "[34m" + "Deletion by first name %ark%:" + (char) 27 + "[38m");
        for (Author author : authorList)
            LOG.info(author.getId() + " " + author.getName() + " " + author.getLastName());
        ah.deleteCriteriaLogic("%y%", "%y%");
        authorList = ah.getAll();
        System.out.println((char) 27 + "[34m" + "Deletion by first name and last name %y%:" + (char) 27 + "[38m");
        for (Author author : authorList)
            LOG.info(author.getId() + " " + author.getName() + " " + author.getLastName());

    }

}
