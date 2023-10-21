package dev.khusanjon.docmngsys.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import dev.khusanjon.docmngsys.model.entity.Company
import dev.khusanjon.docmngsys.service.CompanyService
import dev.khusanjon.docmngsys.service.CompanyService.companyFormat

object CompanyApi {
  val route: Route =
    (path("companies") & get) {
      complete(CompanyService.getAll())
    } ~
      (path("companies" / IntNumber) & get) { id =>
        complete(CompanyService.getOne(id))
      } ~
      (path("companies") & post) {
        entity(as[Company]) { company =>
          complete(CompanyService.create(company))
        }
      } ~
      (path("companies" / IntNumber) & put) { id =>
        entity(as[Company]) { company =>
          complete(CompanyService.update(id, company))
        }
      } ~
      (path("companies" / IntNumber) & delete) { id =>
        complete(CompanyService.delete(id))
      }
}
