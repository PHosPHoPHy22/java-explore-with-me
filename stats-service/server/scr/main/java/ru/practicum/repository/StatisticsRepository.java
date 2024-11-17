package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.StatisticResponse;
import ru.practicum.model.Statistics;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    @Query("SELECT new ru.practicum.StatisticResponse(st.app, st.uri, COUNT(st.ip)) " +
           "FROM Statistics AS st " +
           "WHERE st.uri IN ?1 " +
           "AND st.timestamp BETWEEN ?2 AND ?3 " +
           "GROUP BY st.app, st.uri " +
           "ORDER BY COUNT(st.ip) DESC ")
    List<StatisticResponse> findByUriInAndStartBetween(Iterable<String> uri, LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.StatisticResponse(st.app, st.uri, COUNT(DISTINCT st.ip)) " +
           "FROM Statistics AS st " +
           "WHERE st.uri IN ?1 " +
           "AND st.timestamp BETWEEN ?2 AND ?3 " +
           "GROUP BY st.app, st.uri " +
           "ORDER BY COUNT(st.ip) DESC ")
    List<StatisticResponse> findByUriInAndStartBetweenUniqueIp(Iterable<String> uri, LocalDateTime start,
                                                               LocalDateTime end);

    @Query("SELECT new ru.practicum.StatisticResponse(st.app, st.uri, COUNT(DISTINCT st.ip)) " +
           "FROM Statistics AS st " +
           "WHERE st.timestamp BETWEEN ?1 AND ?2 " +
           "GROUP BY st.app, st.uri " +
           "ORDER BY COUNT(st.ip) DESC ")
    List<StatisticResponse> findStartBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.StatisticResponse(st.app, st.uri, COUNT(st.ip)) " +
           "FROM Statistics AS st " +
           "WHERE st.timestamp BETWEEN ?1 AND ?2 " +
           "GROUP BY st.app, st.uri " +
           "ORDER BY COUNT(st.ip) DESC ")
    List<StatisticResponse> findStartBetweenUniqueIp(LocalDateTime start, LocalDateTime end);

}