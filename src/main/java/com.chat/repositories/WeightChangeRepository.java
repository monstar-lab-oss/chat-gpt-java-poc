package com.chat.repositories;

import com.chat.models.User;
import com.chat.models.WeightChangeLogItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeightChangeRepository extends JpaRepository<WeightChangeLogItem, Long> {

  Optional<WeightChangeLogItem> findFirstByUserOrderByCreatedAtDesc(User user);

  List<WeightChangeLogItem> findByProcessed(boolean processed);

  default List<WeightChangeLogItem> findNotProcessed() {
    return findByProcessed(false);
  }
}
