package com.dauzva.dao.mybatis;

import com.dauzva.mybatis.entities.ScreeningMB;
import com.dauzva.mybatis.mappers.ScreeningMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ScreeningMyBatisDAO {

    @Inject
    private SqlSessionFactory sqlSessionFactory;

    public List<ScreeningMB> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.getMapper(ScreeningMapper.class).findAll();
        }
    }

    public void save(ScreeningMB screening) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.getMapper(ScreeningMapper.class).insert(screening);
            session.commit();
        }
    }
}