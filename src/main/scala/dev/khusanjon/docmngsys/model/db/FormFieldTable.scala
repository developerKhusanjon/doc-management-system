package dev.khusanjon.docmngsys.model.db

import dev.khusanjon.docmngsys.model.entity.FormField
import slick.jdbc.PostgresProfile.api._

class FormFieldTable(tag: Tag) extends Table[FormField](tag, "form_fields") {
  def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def title: Rep[String] = column[String]("title")

  def isRequired: Rep[Boolean] = column[Boolean]("is_required")

  def webFormId: Rep[Long] = column[Long]("web_form_id")

  def * = (id.?, title, isRequired, webFormId) <> ((FormField.apply _).tupled, FormField.unapply)
}
