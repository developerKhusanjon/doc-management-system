package dev.khusanjon.docmngsys.model.db

import dev.khusanjon.docmngsys.model.entity.FormSubmission
import slick.jdbc.PostgresProfile.api._

import java.sql.Date

class FormSubmissionTable(tag: Tag) extends Table[FormSubmission](tag, "form_submissions") {
  def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def date: Rep[Date] = column[Date]("submit_date")

  def webFormId: Rep[Long] = column[Long]("web_form_id")

  def * = (id.?, date, webFormId) <> ((FormSubmission.apply _).tupled, FormSubmission.unapply)
}
