package infowall.domain.service.scheduler;

/**
 *
 */
public class DashboardImportException extends Exception{
    public DashboardImportException() {
    }

    public DashboardImportException(String s) {
        super(s);
    }

    public DashboardImportException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DashboardImportException(Throwable throwable) {
        super(throwable);
    }
}
