package com.patzzzcode.NBAmanager.repositories;

import java.util.Optional;

import com.patzzzcode.NBAmanager.bo.Team;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long>{
    Optional<Team> findByName(String name);
}
