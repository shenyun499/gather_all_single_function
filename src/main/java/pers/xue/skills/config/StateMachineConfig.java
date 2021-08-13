package pers.xue.skills.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
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
@EnableStateMachine
public class StateMachineConfig
        extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states
                .withStates()
                .initial(States.NEW)
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
                .source(States.RUNNABLE).target(States.TERMINATED).event(Events.WAIT_PARAM)

                .and()
                .withExternal()
                .source(States.RUNNABLE).target(States.TERMINATED).event(Events.RUN_END)

                .and()
                .withExternal()
                .source(States.WAITING).target(States.RUNNABLE).event(Events.NOTIFY)

                .and()
                .withExternal()
                .source(States.TIMEDWAIT).target(States.RUNNABLE).event(Events.NOTIFY);
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }
}
