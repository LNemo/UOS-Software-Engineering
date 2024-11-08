package uniCircle.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniCircle.backend.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}