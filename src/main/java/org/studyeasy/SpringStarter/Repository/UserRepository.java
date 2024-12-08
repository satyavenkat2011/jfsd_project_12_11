package org.studyeasy.SpringStarter.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.studyeasy.SpringStarter.Model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findByRole(String role); // This will fetch users with the given role
  
}
