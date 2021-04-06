package com.patzzzcode.NBAmanager.repositories;


import com.patzzzcode.NBAmanager.bo.Match;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match,Long>{
    
}
