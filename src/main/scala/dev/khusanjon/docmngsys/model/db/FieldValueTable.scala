package dev.khusanjon.docmngsys.model.db

import dev.khusanjon.docmngsys.model.entity.FieldValue
import slick.jdbc.PostgresProfile.api._

class FieldValueTable(tag: Tag) extends Table[FieldValue](tag, "field_values") {
  def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def inputData: Rep[String] = column[String]("input_data")

  def formFieldId: Rep[Long] = column[Long]("form_field_id")

  def formSubmissionId: Rep[Long] = column[Long]("form_submission_id")

  def * = (id.?, inputData, formFieldId, formSubmissionId) <> ((FieldValue.apply _).tupled, FieldValue.unapply)
}
