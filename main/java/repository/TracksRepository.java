package repository;

import entities.disks.Disk;
import entities.tracks.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TracksRepository extends MongoRepository<Track, String> {

    void deleteTrackByName(String name);

}
