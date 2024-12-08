package org.studyeasy.SpringStarter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.studyeasy.SpringStarter.Model.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    // Additional query methods can be added here if needed
}
