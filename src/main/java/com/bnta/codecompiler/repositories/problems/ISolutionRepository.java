package com.bnta.codecompiler.repositories.problems;

import com.bnta.codecompiler.models.dtos.SolutionDTO;
import com.bnta.codecompiler.models.problems.Solution;
import com.bnta.codecompiler.models.problems.Problem;
import com.bnta.codecompiler.models.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
public interface ISolutionRepository extends JpaRepository<Solution, Long> {

    @Query("SELECT new com.bnta.codecompiler.models.dtos.SolutionDTO(s.id, s.code, s.lang, s.correctness, s.submissionDate, u.username, u.id, p.title, p.description, p.difficulty) " +
            "FROM Solution s " +
            "JOIN s.user u " +
            "JOIN s.problem p " +
            "ORDER BY s.submissionDate DESC")
    Page<SolutionDTO> findSolutionsWithUserAndProblemDetails(Pageable pageable);
  //  Page<Solution> findAllByOrderBySubmissionDateDesc(Pageable pageable);
  @Query("SELECT new com.bnta.codecompiler.models.dtos.SolutionDTO(s.id, s.code, s.lang, s.correctness, s.submissionDate, u.username, u.id, p.title, p.description, p.difficulty) " +
          "FROM Solution s " +
          "JOIN s.user u " +
          "JOIN s.problem p " +
          "WHERE u = :user " +
          "ORDER BY s.submissionDate DESC")
  List<SolutionDTO> findAllByUser(@Param("user") User user);

    @Query("SELECT new com.bnta.codecompiler.models.dtos.SolutionDTO(s.id, s.code, s.lang, s.correctness, s.submissionDate, u.username, u.id, p.title, p.description, p.difficulty) " +
            "FROM Solution s " +
            "JOIN s.user u " +
            "JOIN s.problem p " +
            "WHERE u.id = :id " +
            "ORDER BY s.submissionDate DESC")
    List<SolutionDTO> findAllByUserId(@Param("id") Long id);

    @Query("SELECT new com.bnta.codecompiler.models.dtos.SolutionDTO(s.id, s.code, s.lang, s.correctness, s.submissionDate, u.username, u.id, p.title, p.description, p.difficulty) " +
            "FROM Solution s " +
            "JOIN s.user u " +
            "JOIN s.problem p " +
            "WHERE p = :problem " +
            "ORDER BY s.submissionDate DESC")
    List<SolutionDTO> findAllByProblem(@Param("problem") Problem problem);

    @Query("SELECT new com.bnta.codecompiler.models.dtos.SolutionDTO(s.id, s.code, s.lang, s.correctness, s.submissionDate, u.username, u.id, p.title, p.description, p.difficulty) " +
            "FROM Solution s " +
            "JOIN s.user u " +
            "JOIN s.problem p " +
            "WHERE p.id = :id " +
            "ORDER BY s.submissionDate DESC")
    List<SolutionDTO> findAllByProblemId(@Param("id") Long id);

    @Query("SELECT new com.bnta.codecompiler.models.dtos.SolutionDTO(s.id, s.code, s.lang, s.correctness, s.submissionDate, u.username, u.id, p.title, p.description, p.difficulty) " +
            "FROM Solution s " +
            "JOIN s.user u " +
            "JOIN s.problem p " +
            "WHERE p = :problem AND u = :user " +
            "ORDER BY s.submissionDate DESC")
    List<SolutionDTO> findAllByProblemAndUser(@Param("problem") Problem problem, @Param("user") User user);

  @Query("SELECT new com.bnta.codecompiler.models.dtos.SolutionDTO(s.id, s.code, s.lang, s.correctness, s.submissionDate, u.username, u.id, p.title, p.description, p.difficulty) " +
          "FROM Solution s " +
          "JOIN s.user u " +
          "JOIN s.problem p " +
          "WHERE s.id = :id")
  Optional<SolutionDTO> getDTOById(@Param("id") Long id);

    @Transactional
    public void deleteAllByProblem_id(Long id);
}
