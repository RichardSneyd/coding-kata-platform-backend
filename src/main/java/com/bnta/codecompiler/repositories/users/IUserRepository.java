package com.bnta.codecompiler.repositories.users;

import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserRepository extends JpaRepository<User, Long> {
    //todo: add derived queries as needed
    List<User> findByCohort(String cohort);

    Page<User> findByOrderByScoreDesc(Pageable pageable);

    Page<User> findAllByOrderByIdDesc(Pageable pageable);

    List<User> findByCohort_NameOrderByScoreDesc(String cohort);
    List<User> findByCohort_IdOrderByScoreDesc(Long cohortId);


    User findByUsername(String username);

    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users_problems WHERE problem_id = :problemId", nativeQuery = true)
    void deleteProblemFromAllUsers(@Param("problemId") Long problemId);

    @Query("SELECT s.problem FROM Solution s WHERE s.user.id = :userId")
    Set<Problem> findCompletedProblemsByUserId(Long userId);
}
