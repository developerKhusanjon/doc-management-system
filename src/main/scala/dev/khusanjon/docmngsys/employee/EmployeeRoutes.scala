package dev.khusanjon.docmngsys.employee

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.actor.typed.scaladsl.AskPattern._
import akka.http.scaladsl.server.directives.Credentials
import akka.util.Timeout
import dev.khusanjon.docmngsys.configuration.BasicAuthConfig
import dev.khusanjon.docmngsys.employee.EmployeeRegistry._

import scala.concurrent.Future

class EmployeeRoutes(employeeRegistry: ActorRef[EmployeeRegistry.Command], auth: BasicAuthConfig)(implicit val system: ActorSystem[_]) {
  import JsonFormats._
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  private implicit val timeout = Timeout.create(system.settings.config.getDuration("app.routes.ask-timeout"))

  def getEmployees: Future[Employees]  =
    employeeRegistry.ask(GetEmployees)

  def getEmployee(name: String): Future[GetEmployeeResponse] =
    employeeRegistry.ask(GetEmployee(name, _))

  def createEmployee(employee: Employee): Future[ActionPerformed] =
    employeeRegistry.ask(CreateEmployee(employee, _))

  def deleteEmployee(name: String): Future[ActionPerformed] =
    employeeRegistry.ask(DeleteEmployee(name, _))

  def userPassAuthenticator(credentials: Credentials): Option[String] = {
    credentials match {
      case p @ Credentials.Provided(id) if id == auth.user && p.verify(auth.password) => Some(id)
      case _ => None
    }
  }
}
