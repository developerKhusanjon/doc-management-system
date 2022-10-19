package dev.khusanjon.docmngsys.form

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import cats.effect.unsafe.implicits.global
import dev.khusanjon.docmngsys.configuration.PostgresConfig
import doobie.implicits._

import scala.collection.immutable


final case class WebForm(title: String, description: String)
final case class WebForms(forms: immutable.Seq[WebForm])

object WebFormRegistry {

  import dev.khusanjon.docmngsys.util.Utils._

  sealed trait Command
  final case class GetForms(replyTo: ActorRef[WebForms]) extends Command
  final case class CreateForm(form: WebForm, replyTo: ActorRef[ActionPerformed]) extends Command
  final case class GetForm(title: String, replyTo: ActorRef[GetFormResponse]) extends Command
  final case class DeleteForm(title: String, replyTo: ActorRef[ActionPerformed]) extends Command

  final case class GetFormResponse(maybeForm: Option[WebForm])
  final case class ActionPerformed(description: String)

  def apply(pgConfig: PostgresConfig): Behavior[Command] = {
    registry(transactor(pgConfig))
  }

  def dbGetForms(xa: Transactor): WebForms = {
    WebForms(
      sql"SELECT title, description FROM web_forms".
        query[WebForm].
        to[List].
        transact(xa).
        unsafeRunSync
    )
  }

  def dbGetForm(xa: Transactor, title: String): Option[WebForm] = {
    sql"SELECT title, description FROM web_forms WHERE title = ${title}".
      query[WebForm].
      option.
      transact(xa).
      unsafeRunSync
  }

  def dbCreateForm(xa: Transactor, form: WebForm): Unit = {
    sql"INSERT INTO web_forms (title, description) VALUES(${form.title}, ${form.description}".
      update.
      withUniqueGeneratedKeys[Int]("id").
      transact(xa).
      unsafeRunSync
  }

  def dbDeleteForm(xa: Transactor, title: String): Unit = {
    sql"DELETE FROM web_forms WHERE title = ${title}".
      update.
      run.
      transact(xa).
      unsafeRunSync
  }

  private def registry(xa: Transactor): Behavior[Command] =
    Behaviors.receiveMessage {
      case GetForms(replyTo) =>
        replyTo ! dbGetForms(xa)
        Behaviors.same
      case CreateForm(form, replyTo) =>
        dbCreateForm(xa, form)
        replyTo ! ActionPerformed(s"WebForm ${form.title} has been deleted.")
        Behaviors.same
      case GetForm(title, replyTo) =>
        replyTo ! GetFormResponse(dbGetForm(xa, title))
        Behaviors.same
      case DeleteForm(title, replyTo) =>
        dbDeleteForm(xa, title)
        replyTo ! ActionPerformed(s"WebForm ${title} has been deleted.")
        Behaviors.same
    }
}
