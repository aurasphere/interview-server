package co.aurasphere.interview.server.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.aurasphere.interview.server.model.Technology;

/**
 * Dao for {@link Technology}. Its implementation is delegated to Spring.
 * 
 * @author Donato Rimenti
 *
 */
@Repository
public interface TechnologyDao extends MongoRepository<Technology, String> {

}
