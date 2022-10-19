package dev.khusanjon.docmngsys.company

import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Directives.pathPrefix
import akka.http.scaladsl.server.directives.Credentials
import akka.pattern.FutureRef
import akka.util.Timeout
import dev.khusanjon.docmngsys.company.CompanyRegistry.{ActionPerformed, CreateCompany, DeleteCompany, GetCompanies, GetCompany, GetCompanyResponse}
import dev.khusanjon.docmngsys.configuration.BasicAuthConfig

import scala.concurrent.Future

class CompanyRoutes(companyRegistry: ActorRef[CompanyRegistry.Command], auth: BasicAuthConfig)(implicit val system: ActorSystem[_]) {

  import JsonFormats._
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  private implicit val timeout = Timeout.create(system.settings.config.getDuration("app.routes.ask-timeout"))

  def getCompanies: Future[Companies] =
    companyRegistry.ask(GetCompanies)

  def getCompany(name: String): Future[GetCompanyResponse] =
    companyRegistry.ask(GetCompany(name, _))

  def createCompany(company: Company): Future[ActionPerformed] =
    companyRegistry.ask(CreateCompany(company, _))

  def deleteCompany(name: String): Future[ActionPerformed] =
    companyRegistry.ask(DeleteCompany(name, _))

  def myUserPassAuthenticator(credentials: Credentials): Option[String] = {
    credentials match {
      case p @ Credentials.Provided(id) if id == auth.user && p.verify(auth.password) => Some(id)
      case _ => None
    }
  }

}
