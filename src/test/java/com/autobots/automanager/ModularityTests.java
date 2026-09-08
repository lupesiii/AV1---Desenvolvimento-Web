package com.autobots.automanager;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTests {
  @Test
  void verificaModulos() {
    ApplicationModules modules = ApplicationModules.of(AutobotsApplication.class);
    modules.verify();
  }
}