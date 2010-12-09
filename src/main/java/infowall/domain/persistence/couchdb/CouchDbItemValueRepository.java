package infowall.domain.persistence.couchdb;

import infowall.domain.model.ItemValue;
import infowall.domain.persistence.ItemValueRepository;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class CouchDbItemValueRepository
        extends CouchDbRepositorySupport<ItemValue>
        implements ItemValueRepository {

    @Autowired
    protected CouchDbItemValueRepository(
            CouchDbConnector db) {

        super(ItemValue.class, db);
    }
}
