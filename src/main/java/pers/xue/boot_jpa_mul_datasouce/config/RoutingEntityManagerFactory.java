package pers.xue.boot_jpa_mul_datasouce.config;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import java.util.Map;

/**
 * @author huangzhixue
 * @date 2024/9/3 11:47
 * @Description
 * 动态切换EntityManagerFactory
 * EntityManagerFactory路由类
 */

public class RoutingEntityManagerFactory implements EntityManagerFactory {
    @Nullable
    private Map<String, Object> targetEntityManagerFactorys;
    @Nullable
    private Object defaultTargetEntityManagerFactory;

    @Nullable
    public Map<String, Object> getTargetEntityManagerFactorys() {
        return targetEntityManagerFactorys;
    }

    public void setTargetEntityManagerFactorys(@Nullable Map<String, Object> targetEntityManagerFactorys) {
        this.targetEntityManagerFactorys = targetEntityManagerFactorys;
    }

    @Nullable
    public Object getDefaultTargetEntityManagerFactory() {
        return defaultTargetEntityManagerFactory;
    }

    public void setDefaultTargetEntityManagerFactory(@Nullable Object defaultTargetEntityManagerFactory) {
        this.defaultTargetEntityManagerFactory = defaultTargetEntityManagerFactory;
    }

    public String determineCurrentLookupKey() {
        // 如果不使用读写分离,这里也可以做一个简单的负载均衡策略
        String entityManagerFactoryType = EntityManagerContextHolder.getEntityManagerFactoryType();
        //System.out.println("当前使用的数据源是：" + entityManagerFactoryType);
        return entityManagerFactoryType;
    }

    public EntityManagerFactory determineTargetEntityManagerFactory() {
        Assert.notNull(this.targetEntityManagerFactorys, "targetEntityManagerFactory router not initialized");
        Object lookupKey = this.determineCurrentLookupKey();
        EntityManagerFactory entityManagerFactory = (EntityManagerFactory)this.targetEntityManagerFactorys.get(lookupKey);
        if (entityManagerFactory == null && lookupKey == null) {
            entityManagerFactory = (EntityManagerFactory)this.defaultTargetEntityManagerFactory;
        }

        if (entityManagerFactory == null) {
            throw new IllegalStateException("Cannot determine target EntityManagerFactory for lookup key [" + lookupKey + "]");
        } else {
            return entityManagerFactory;
        }
    }

    //---------------------下面的方法都是EntityManagerFactory接口的实现------------------------------------
    @Override
    public EntityManager createEntityManager() {
        return this.determineTargetEntityManagerFactory().createEntityManager();
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        return this.determineTargetEntityManagerFactory().createEntityManager(map);
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        return this.determineTargetEntityManagerFactory().createEntityManager(synchronizationType);
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        return this.determineTargetEntityManagerFactory().createEntityManager(synchronizationType,map);
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return this.determineTargetEntityManagerFactory().getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return this.determineTargetEntityManagerFactory().getMetamodel();
    }

    @Override
    public boolean isOpen() {
        return this.determineTargetEntityManagerFactory().isOpen();
    }

    @Override
    public void close() {
        this.determineTargetEntityManagerFactory().close();
    }

    @Override
    public Map<String, Object> getProperties() {
        return this.determineTargetEntityManagerFactory().getProperties();
    }

    @Override
    public Cache getCache() {
        return this.determineTargetEntityManagerFactory().getCache();
    }

    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return this.determineTargetEntityManagerFactory().getPersistenceUnitUtil();
    }

    @Override
    public void addNamedQuery(String s, Query query) {
        this.determineTargetEntityManagerFactory().addNamedQuery(s,query);
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return this.determineTargetEntityManagerFactory().unwrap(aClass);
    }

    @Override
    public <T> void addNamedEntityGraph(String s, EntityGraph<T> entityGraph) {
        this.determineTargetEntityManagerFactory().addNamedEntityGraph(s,entityGraph);
    }
}