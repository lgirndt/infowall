package infowall.domain.persistence.couchdb;

import infowall.domain.model.Dashboard;
import infowall.domain.persistence.DashboardRepository;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@View( name = "all", map = "function(doc) { if (doc.type == 'dashboard' ) emit( null, doc._id )}")
public final class CouchDbDashboardRepository
        extends CouchDbRepositorySupport<Dashboard>
        implements DashboardRepository, InitializingBean {

    public void afterPropertiesSet() throws Exception {
        initStandardDesignDocument();
    }

    @Autowired
    public CouchDbDashboardRepository(CouchDbConnector db) {
        super(Dashboard.class, db);
    }
}
