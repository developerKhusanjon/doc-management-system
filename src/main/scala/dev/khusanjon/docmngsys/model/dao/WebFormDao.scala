package dev.khusanjon.docmngsys.model.dao

import dev.khusanjon.docmngsys.model.entity.WebForm

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

object WebFormDao extends BaseDao {
  def findAll(): Future[Seq[WebForm]] = webFormTable.result

  def findById(id: Long): Future[Option[WebForm]] = webFormTable.filter(_.id === id).result.headOption

  def create(webForm: WebForm): Future[Long] = webFormTable returning webFormTable.map(_.id) += webForm

  def update(id: Long, newWebForm: WebForm): Future[Int] = {
    webFormTable.filter(_.id === id).map(webForm => (webForm.title))
      .update((newWebForm.title))
  }


  def delete(id: Long): Future[Int] = {
    webFormTable.filter(_.id === id).delete
  }
}
