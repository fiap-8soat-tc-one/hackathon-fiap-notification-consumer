package com.fiap.hackathon.application.usecases;

import com.fiap.hackathon.application.gateways.NotifyUserSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyUserUseCase {
    private final NotifyUserSpec notifyUserSpec;

    public void execute(String id, String status) {
        notifyUserSpec.execute(id, status);
    }
}
