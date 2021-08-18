package pers.xue.skills.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.statemachine.state.State;
import pers.xue.skills.enums.Events;
import pers.xue.skills.enums.States;

import java.util.EnumSet;

/**
 * @author huangzhixue
 * @date 2021/8/13 3:27 下午
 * @Description
 * 状态机配置
 */
@Configuration
@EnableStateMachineFactory
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<States, Events> {

    private final static Logger log = LoggerFactory.getLogger(StateMachineConfig.class);

    @Autowired
    private StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                // 监听事件，状态改变
                .withConfiguration()
                .autoStartup(true)
                .listener(listener())
                // 开启持久化，保存StateMachine Context。若不开启无法保存。
                .and()
                .withPersistence()
                .runtimePersister(stateMachineRuntimePersister);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                // 初始状态 NEW
                .initial(States.NEW)
                // 所有可达状态
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.NEW).target(States.RUNNABLE).event(Events.START)

                .and()
                .withExternal()
                .source(States.RUNNABLE).target(States.WAITING).event(Events.WAIT_NO_PARAM)

                .and()
                .withExternal()
                .source(States.RUNNABLE).target(States.TIMED_WAITING).event(Events.WAIT_PARAM)

                .and()
                .withExternal()
                .source(States.RUNNABLE).target(States.TERMINATED).event(Events.RUN_END)

                .and()
                .withExternal()
                .source(States.RUNNABLE).target(States.BLOCKED).event(Events.ENTER_SYNC)

                .and()
                .withExternal()
                .source(States.WAITING).target(States.RUNNABLE).event(Events.NOTIFY)

                .and()
                .withExternal()
                .source(States.TIMED_WAITING).target(States.RUNNABLE).event(Events.NOTIFY)

                .and()
                .withExternal()
                .source(States.BLOCKED).target(States.RUNNABLE).event(Events.NOTIFY);
    }


    /**
     * 提供监控状态变化服务
     *
     * @return
     */
    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                log.info("State from {} change to {}", from.getId(), to.getId());
            }
        };
    }


    /**
     * 提供stateMachineService 服务
     *
     * @param stateMachineFactory
     * @param stateMachineRuntimePersister
     * @return
     */
    @Bean
    public StateMachineService<States, Events> stateMachineService(
            StateMachineFactory<States, Events> stateMachineFactory,
            StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister) {
        return new DefaultStateMachineService<>(stateMachineFactory, stateMachineRuntimePersister);
    }
}
