package com.patzzzcode.NBAmanager.repositories;

import java.util.List;
import java.util.Optional;

import com.patzzzcode.NBAmanager.bo.Player;
import com.patzzzcode.NBAmanager.bo.Team;
import com.patzzzcode.NBAmanager.bo.TeamPlayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long> {
    Optional<TeamPlayer> findByPlayerAndTeam(Player player, Team team);

    List<TeamPlayer> findByTeam(Team team);
}
