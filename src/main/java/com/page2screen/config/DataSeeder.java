package com.page2screen.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
  private final DataSeedService dataSeedService;

  public DataSeeder(DataSeedService dataSeedService) {
    this.dataSeedService = dataSeedService;
  }

  @Override
  public void run(String... args) throws Exception {
    dataSeedService.seedIfEmpty();
  }
}
