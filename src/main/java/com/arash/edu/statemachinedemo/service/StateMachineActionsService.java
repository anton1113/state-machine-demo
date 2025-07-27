package com.arash.edu.statemachinedemo.service;

import com.arash.edu.statemachinedemo.domain.MyUser;
import com.arash.edu.statemachinedemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@WithStateMachine
public class StateMachineActionsService {

    private final UserRepository userRepository;

    @OnTransition(source = "ASK_NAME")
    public void onNameAsked(StateContext<String, String> stateContext) {
        MyUser myUser = getUser(UUID.fromString(stateContext.getStateMachine().getId()));
        myUser.setName(stateContext.getExtendedState().getVariables().get("message").toString());
        userRepository.save(myUser);
    }

    @OnTransition(source = "ASK_AGE")
    public void onAgeAsked(StateContext<String, String> stateContext) {
        MyUser myUser = getUser(UUID.fromString(stateContext.getStateMachine().getId()));
        myUser.setAge(stateContext.getExtendedState().getVariables().get("message").toString());
        userRepository.save(myUser);
    }

    private MyUser getUser(UUID sessionId) {
        return userRepository.findById(sessionId).orElseGet(() -> {
            MyUser usr = new MyUser();
            usr.setId(sessionId);
            return usr;
        });
    }
}
