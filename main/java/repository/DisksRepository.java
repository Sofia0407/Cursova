package repository;

import entities.disks.Disk;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisksRepository extends MongoRepository<Disk, String> {

    void deleteDiskByName(String name);

}
