package dev.khusanjon.docmngsys.config

import org.flywaydb.core.Flyway

trait MigrationConfig extends Config {

  private val flyway = new Flyway(Flyway.configure())

  def migrate(): Unit = flyway.migrate()

  def reloadSchema(): Unit = {
    flyway.clean()
    flyway.migrate()
  }
}