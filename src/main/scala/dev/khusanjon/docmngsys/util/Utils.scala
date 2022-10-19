package dev.khusanjon.docmngsys.util

import cats.effect.IO
import dev.khusanjon.docmngsys.configuration.PostgresConfig
import doobie.Transactor

object Utils {
  type Transactor = doobie.Transactor.Aux[IO, Unit]
  def transactor(pgConfig: PostgresConfig): Transactor = {
    Transactor.fromDriverManager[IO](
      "org.postgresql.Driver", pgConfig.url, pgConfig.user, pgConfig.pass
    )
  }
}
