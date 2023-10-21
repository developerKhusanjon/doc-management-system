package dev.khusanjon.docmngsys

import akka.http.scaladsl.server.Directives._
import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.logRequestResult
import akka.stream.ActorMaterializer
import dev.khusanjon.docmngsys.api.{CompanyApi, EmployeeApi, WebFormApi}
import dev.khusanjon.docmngsys.config.{Config, MigrationConfig}

import scala.concurrent.ExecutionContext

object Main extends App with Config with MigrationConfig {
  private implicit val system: ActorSystem = ActorSystem()
  protected implicit val executor: ExecutionContext = system.dispatcher
  protected val log: LoggingAdapter = Logging(system, getClass)
  protected implicit val materializer: ActorMaterializer = ActorMaterializer()

  migrate()

  val bindingFuture = Http()
    .bindAndHandle(handler = logRequestResult("log")(CompanyApi.route ~ EmployeeApi.route ~ WebFormApi.route)
      , interface = httpInterface, port = httpPort)

  println(s"Server online at $httpInterface:$httpPort\nPress RETURN to stop...")
  scala.io.StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}