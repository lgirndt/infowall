package infowall.domain.persistence;

import java.util.List;

/**
 *
 */
public interface BaseRepository<T> {

    T get(String id);

    void add(T t);
    void update(T t);

    List<T> getAll();
}
