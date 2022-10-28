package dev.khusanjon.docmngsys.model.dao

import dev.khusanjon.docmngsys.model.entity.FormField

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

object FormFieldDao extends BaseDao {
  def findAll(): Future[Seq[FormField]] = formFieldTable.result

  def findById(id: Long): Future[FormField] = formFieldTable.filter(_.id === id).result.head

  def create(formField: FormField): Future[Long] = formFieldTable returning formFieldTable.map(_.id) += formField

  def update(id: Long, newFormField: FormField): Future[Int] = {
    formFieldTable.filter(_.id === id).map(formField => (formField.title, formField.isRequired, formField.webFormId))
      .update((newFormField.title, newFormField.isRequired, newFormField.webFormId))
  }


  def delete(id: Long): Future[Int] = {
    formFieldTable.filter(_.id === id).delete
  }
}
