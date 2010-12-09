package infowall.domain.persistence.couchdb;

import infowall.domain.model.DashboardItem;
import infowall.domain.persistence.DashboardItemRepository;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public final class CouchDbDashboardItemRepository
        extends CouchDbRepositorySupport<DashboardItem>
        implements DashboardItemRepository {

    @Autowired
    public CouchDbDashboardItemRepository(CouchDbConnector db) {
        super(DashboardItem.class, db);
    }
}
