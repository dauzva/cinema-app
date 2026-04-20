package com.dauzva.mybatis.mappers;

import com.dauzva.mybatis.entities.ScreeningMB;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ScreeningMapper {

    // MyBatis executes this SQL and maps the columns to ScreeningMB fields
    @Select("""
        SELECT
            s.id,
            s.start_time  AS startTime,
            s.price,
            m.title       AS movieTitle,
            m.duration_minutes AS movieDuration,
            h.name        AS hallName,
            c.name        AS cinemaName
        FROM screening s
        JOIN movie   m ON s.movie_id = m.id
        JOIN hall    h ON s.hall_id  = h.id
        JOIN cinema  c ON h.cinema_id = c.id
        ORDER BY s.start_time
""")
    @Results({
            @Result(property = "startTime",     column = "startTime"),
            @Result(property = "movieTitle",    column = "movieTitle"),
            @Result(property = "movieDuration", column = "movieDuration"),
            @Result(property = "hallName",      column = "hallName"),
            @Result(property = "cinemaName",    column = "cinemaName")
    })
    List<ScreeningMB> findAll();

    @Insert("""
        INSERT INTO screening (start_time, price, movie_id, hall_id)
        VALUES (#{startTime}, #{price}, #{movieId}, #{hallId})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ScreeningMB screening);
}