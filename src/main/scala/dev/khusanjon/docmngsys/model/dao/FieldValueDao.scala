package dev.khusanjon.docmngsys.model.dao

import dev.khusanjon.docmngsys.model.entity.FieldValue

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

object FieldValueDao extends BaseDao {
  def findAll(): Future[Seq[FieldValue]] = fieldValueTable.result

  def findById(id: Long): Future[Option[FieldValue]] = fieldValueTable.filter(_.id === id).result.headOption

  def create(fieldValue: FieldValue): Future[Long] = fieldValueTable returning fieldValueTable.map(_.id) += fieldValue

  def update(id: Long, newFieldValue: FieldValue): Future[Int] = {
    fieldValueTable.filter(_.id === id).map(fieldValue => (fieldValue.inputData, fieldValue.formFieldId, fieldValue.formSubmissionId))
      .update((newFieldValue.inputData, newFieldValue.formFieldId, newFieldValue.formSubmissionId))
  }


  def delete(id: Long): Future[Int] = {
    fieldValueTable.filter(_.id === id).delete
  }
}
