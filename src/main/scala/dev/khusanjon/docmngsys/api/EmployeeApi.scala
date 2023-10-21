package dev.khusanjon.docmngsys.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import dev.khusanjon.docmngsys.model.entity.Employee
import dev.khusanjon.docmngsys.service.EmployeeService.employeeFormat
import dev.khusanjon.docmngsys.service.EmployeeService

object EmployeeApi {
  val route: Route =
    (path("employees") & get) {
      complete(EmployeeService.getAll())
    } ~
      (path("employees" / IntNumber) & get) { id =>
        complete(EmployeeService.getOne(id))
      } ~
      (path("employees") & post) {
        entity(as[Employee]) { employee =>
          complete(EmployeeService.create(employee))
        }
      } ~
      (path("employees" / IntNumber) & put) { id =>
        entity(as[Employee]) { employee =>
          complete(EmployeeService.update(id, employee))
        }
      } ~
      (path("employees" / IntNumber) & delete) { id =>
        complete(EmployeeService.delete(id))
      }
}
