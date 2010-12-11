package infowall.domain.persistence.sql;

/**
 *
 */
public class DaoException extends RuntimeException {

    public DaoException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DaoException(String s) {
        super(s);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DaoException(String s, Throwable throwable) {
        super(s, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public DaoException(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
