package com.dauzva.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import java.io.InputStream;

@ApplicationScoped
public class MyBatisUtil {

    private SqlSessionFactory sqlSessionFactory;

    // @PostConstruct runs once right after this CDI bean is created
    @PostConstruct
    public void init() {
        try {
            InputStream config = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize MyBatis", e);
        }
    }

    @Produces
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}