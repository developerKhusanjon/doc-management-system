package dev.khusanjon.docmngsys.config

trait DatabaseConfig extends Config {
  val driver = slick.jdbc.PostgresProfile

  import driver.api._

  val db: Database = Database.forConfig("database")

  implicit val session: Session = db.createSession()
}