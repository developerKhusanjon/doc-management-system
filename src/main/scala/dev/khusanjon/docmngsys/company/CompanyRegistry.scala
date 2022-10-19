package dev.khusanjon.docmngsys.company

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import dev.khusanjon.docmngsys.configuration.PostgresConfig
import doobie.implicits._
import doobie._

import scala.collection.immutable

//#company-case-classes
final case class Company(name: String, address: String)

final case class Companies(companies: immutable.Seq[Company])

object CompanyRegistry {

  import dev.khusanjon.docmngsys.util.Utils._

  // protocol
  sealed trait Command
  final case class GetCompanies(replyTo: ActorRef[Companies]) extends Command
  final case class CreateCompany(company: Company, replyTo: ActorRef[ActionPerformed]) extends Command
  final case class GetCompany(name: String, replyTo: ActorRef[GetCompanyResponse]) extends Command
  final case class DeleteCompany(name: String, replyTo: ActorRef[ActionPerformed]) extends Command
  final case class GetCompanyResponse(maybeCompany: Option[Company])
  final case class ActionPerformed(description: String)

  def apply(pgConfig: PostgresConfig): Behavior[Command] = {
    registry(transactor(pgConfig))
  }

  def dbGetCompanies(xa: Transactor): Companies = {
    Companies(
      sql"SELECT name, address FROM companies".
        query[Company].
        to[List].
        transact(xa).
        unsafeRunSync
    )
  }

  def dbGetCompany(xa: Transactor, name: String): Option[Company] = {
    sql"SELECT name, address FROM companies WHERE name = ${name}".
      query[Company].
      option.
      transact(xa).
      unsafeRunSync
  }

  def dbCreateCompany(xa: Transactor, company: Company): Unit = {
    sql"INSERT INTO companies (name, address) VALUES(${company.name}, ${company.address}".
      update.
      withUniqueGeneratedKeys[Int]("id").
      transact(xa).
      unsafeRunSync
  }

  def dbDeleteCompany(xa: Transactor, name: String): Unit = {
    sql"DELETE FROM companies WHERE name = ${name}".
      update.
      run.
      transact(xa).
      unsafeRunSync
  }

  private def registry(xa: Transactor): Behavior[Command] =
    Behaviors.receiveMessage {
      case GetCompanies(replyTo) =>
        replyTo ! dbGetCompanies(xa)
        Behaviors.same
      case CreateCompany(company, replyTo) =>
        dbCreateCompany(xa, company)
        replyTo ! ActionPerformed(s"Company ${company.name} is being created.")
        Behaviors.same
      case GetCompany(name, replyTo) =>
        replyTo ! GetCompanyResponse(dbGetCompany(xa, name))
        Behaviors.same
      case DeleteCompany(name, replyTo) =>
        dbDeleteCompany(xa, name)
        replyTo ! ActionPerformed(s"Company $name is being deleted")
        Behaviors.same
    }

}
