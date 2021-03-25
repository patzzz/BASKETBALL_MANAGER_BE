package com.patzzzcode.NBAmanager.repositories;

import com.patzzzcode.NBAmanager.bo.TeamPlayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamPlayerRepository extends JpaRepository<TeamPlayer,Long> {
    
}
