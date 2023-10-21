package dev.khusanjon.docmngsys.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import dev.khusanjon.docmngsys.model.entity.WebForm
import dev.khusanjon.docmngsys.service.WebFormService.webFormFormat
import dev.khusanjon.docmngsys.service.WebFormService

object WebFormApi {
  val route: Route =
    (path("web-forms") & get) {
      complete(WebFormService.getAll())
    } ~
      (path("web-forms" / IntNumber) & get) { id =>
        complete(WebFormService.getOne(id))
      } ~
      (path("web-forms") & post) {
        entity(as[WebForm]) { webForm =>
          complete(WebFormService.create(webForm))
        }
      } ~
      (path("web-forms" / IntNumber) & put) { id =>
        entity(as[WebForm]) { webForm =>
          complete(WebFormService.update(id, webForm))
        }
      } ~
      (path("web-forms" / IntNumber) & delete) { id =>
        complete(WebFormService.delete(id))
      }
}
