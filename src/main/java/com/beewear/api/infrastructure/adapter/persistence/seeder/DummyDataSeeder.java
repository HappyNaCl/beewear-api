package com.beewear.api.infrastructure.adapter.persistence.seeder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("dev")
public class DummyDataSeeder implements CommandLineRunner {

    private void seedUser() {

    }

    private void seedProduct() {

    }


    @Override
    public void run(String... args) throws Exception {

    }
}
