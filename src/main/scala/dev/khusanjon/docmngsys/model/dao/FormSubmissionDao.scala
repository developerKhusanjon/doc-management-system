package dev.khusanjon.docmngsys.model.dao

import dev.khusanjon.docmngsys.model.entity.FormSubmission

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

object FormSubmissionDao extends BaseDao {
  def findAll(): Future[Seq[FormSubmission]] = formSubmissionTable.result

  def findById(id: Long): Future[FormSubmission] = formSubmissionTable.filter(_.id === id).result.head

  def create(formSubmission: FormSubmission): Future[Long] = formSubmissionTable returning formSubmissionTable.map(_.id) += formSubmission

  def update(id: Long, newFormSubmission: FormSubmission): Future[Int] = {
    formSubmissionTable.filter(_.id === id).map(formSubmission => (formSubmission.date, formSubmission.webFormId))
      .update((newFormSubmission.date, newFormSubmission.webFormId))
  }


  def delete(id: Long): Future[Int] = {
    formSubmissionTable.filter(_.id === id).delete
  }
}