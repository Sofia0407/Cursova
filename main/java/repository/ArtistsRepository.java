package repository;

import entities.artists.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistsRepository extends MongoRepository<Artist, String> {

    void deleteArtistByName(String name);

    Artist getArtistByName(String name);

}
