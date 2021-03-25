package com.patzzzcode.NBAmanager.repositories;

import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match,Long>{
    
}
