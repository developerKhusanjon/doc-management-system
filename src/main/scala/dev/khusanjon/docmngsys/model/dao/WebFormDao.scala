package dev.khusanjon.docmngsys.model.dao

import dev.khusanjon.docmngsys.model.dto.WebFormDto
import dev.khusanjon.docmngsys.model.entity.WebForm
import slick.jdbc.GetResult

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

  def findByIdWithFields(id: Long): Future[Option[WebFormDto]] = {

    implicit val getResultFF: GetResult[List[Map[String, String]]] =
      GetResult[List[Map[String, String]]](r =>
        (1 to r.numColumns).map(_ => Map.apply(r.nextString() -> r.nextString())).toList)

    implicit val getResultWF: GetResult[WebFormDto] = GetResult(r => WebFormDto(r.<<, r.<<, r.<<))

    val formQuery =
      sql"""SELECT
            "wf".id as id,
            "wf".title as title,
            array_agg((SElECT * FROM "form_fields" AS "ff" WHERE "ff".web_form_id = "wf".id)) as fields
        FROM "web_forms" AS "wf"
        WHERE "wf".id = $id
         """.as[WebFormDto]

    db.run(formQuery)
  }
}
