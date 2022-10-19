package dev.khusanjon.docmngsys.form

import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.util.Timeout
import dev.khusanjon.docmngsys.configuration.BasicAuthConfig
import dev.khusanjon.docmngsys.form.WebFormRegistry._

import scala.concurrent.Future

class WebFormRoutes(webFormRegistry: ActorRef[WebFormRegistry.Command], auth: BasicAuthConfig)(implicit val system: ActorSystem[_]) {

  import JsonFormats._
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  private implicit val timeout = Timeout.create(system.settings.config.getDuration("app.routes.ask-timeout"))

  def getForms(): Future[WebForms] =
    webFormRegistry.ask(GetForms)

  def getForm(title: String): Future[GetFormResponse] =
    webFormRegistry.ask(GetForm(title, _))

  def createForm(form: WebForm): Future[ActionPerformed] =
    webFormRegistry.ask(CreateForm(form, _))

  def deleteForm(title: String): Future[ActionPerformed] =
    webFormRegistry.ask(DeleteForm(title, _))


}
