package dev.khusanjon.docmngsys.employee

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import dev.khusanjon.docmngsys.company.Company
import dev.khusanjon.docmngsys.configuration.PostgresConfig
import doobie.implicits._
import doobie._
import cats.effect.unsafe.implicits.global

import scala.collection.immutable

final case class Employee(name: String, role: String, company: Company)
final case class Employees(employees: immutable.Seq[Employee])

object EmployeeRegistry {

  import dev.khusanjon.docmngsys.util.Utils._

  sealed trait Command
  final case class GetEmployees(replyTo: ActorRef[Employees]) extends Command
  final case class CreateEmployee(employee: Employee, replyTo: ActorRef[ActionPerformed]) extends Command
  final case class GetEmployee(name: String, replyTo: ActorRef[GetEmployeeResponse]) extends Command
  final case class DeleteEmployee(name: String, replyTo: ActorRef[ActionPerformed]) extends Command

  final case class GetEmployeeResponse(maybeEmployee: Option[Employee])
  final case class ActionPerformed(description: String)

  def apply(pgConfig: PostgresConfig): Behavior[Command] = {
    registry(transactor(pgConfig))
  }

  def dbGetEmployees(xa: Transactor): Employees = {
    Employees(
      sql"SELECT name, role, * FROM employees".
        query[Employee].
        to[List].
        transact(xa).
        unsafeRunSync()
    )
  }

  def dbGetEmployee(xa: Transactor, name: String): Option[Employee] = {
    sql"SELECT name, role, * FROM employees WHERE name = ${name}".
      query[Employee].
      option.
      transact(xa).
      unsafeRunSync
  }

  def dbCreateEmployee(xa: Transactor, employee: Employee): Unit = {
    sql"INSERT INTO employees (name, role, *) VALUES(${employee.name}, ${employee.role}, ${employee.company}".
      update.
      run.
      transact(xa).
      unsafeRunSync
  }

  def dbDeleteEmployee(xa: Transactor, name: String): Unit = {
    sql"DELETE FROM employees WHERE name = ${name}".
      update.
      run.
      transact(xa).
      unsafeRunSync
  }

  private def registry(xa: Transactor): Behavior[Command] = {
    Behaviors.receiveMessage {
      case GetEmployees(replyTo) =>
        replyTo ! dbGetEmployees(xa)
        Behaviors.same
      case CreateEmployee(employee, replyTo) =>
        dbCreateEmployee(xa, employee)
        replyTo ! ActionPerformed(s"Employee ${employee.name} has been created.")
        Behaviors.same
      case GetEmployee(name, replyTo) =>
        replyTo ! GetEmployeeResponse(dbGetEmployee(xa,name))
        Behaviors.same
      case DeleteEmployee(name, replyTo) =>
        dbDeleteEmployee(xa, name)
        replyTo ! ActionPerformed(s"Employee $name has been deleted.")
        Behaviors.same
    }
  }
}
