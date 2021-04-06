package com.patzzzcode.NBAmanager.repositories;

import java.util.Optional;

import com.patzzzcode.NBAmanager.bo.Player;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player,Long>{
    Optional<Player> findByIdentityNumber(String identityNumbr);
}
