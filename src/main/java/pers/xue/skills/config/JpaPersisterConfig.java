package pers.xue.skills.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import pers.xue.skills.enums.Events;
import pers.xue.skills.enums.States;

/**
 * @author huangzhixue
 * @date 2021/8/17 4:49 下午
 * @Description
 */
@Configuration
//@Profile("jpa")
//@Order(0)
public class JpaPersisterConfig {

    /**
     * 提供存储服务 bean
     * @param jpaStateMachineRepository
     * @return
     */
    @Bean
    public StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister(
            JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }
}
