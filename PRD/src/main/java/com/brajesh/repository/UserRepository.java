package com.brajesh.repository;

import com.brajesh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Derived query – works perfectly
    List<User> findByTeamId(Long teamId);

    // Fixed @Query version
    @Query("SELECT u FROM User u WHERE u.team.id = :teamId")
    List<User> findUsersByTeam(Long teamId);

    // Users in any of the provided team ids
    @Query("SELECT u FROM User u WHERE u.team.id IN :teamIds")
    List<User> findUsersByTeamIds(@Param("teamIds") List<Long> teamIds);
}
