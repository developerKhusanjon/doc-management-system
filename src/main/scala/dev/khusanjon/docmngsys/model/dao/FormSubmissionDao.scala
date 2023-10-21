package dev.khusanjon.docmngsys.model.dao

import dev.khusanjon.docmngsys.model.dto.FormSubmissionDto
import dev.khusanjon.docmngsys.model.entity.FormSubmission
import slick.jdbc.GetResult

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import java.time.{LocalDate, LocalDateTime}

object FormSubmissionDao extends BaseDao {
  def findAll(): Future[Seq[FormSubmission]] = formSubmissionTable.result

  def findById(id: Long): Future[Option[FormSubmission]] = formSubmissionTable.filter(_.id === id).result.headOption

  def create(formSubmission: FormSubmission): Future[Long] = formSubmissionTable returning formSubmissionTable.map(_.id) += formSubmission

  def update(id: Long, newFormSubmission: FormSubmission): Future[Int] = {
    formSubmissionTable.filter(_.id === id).map(formSubmission => (formSubmission.date, formSubmission.webFormId))
      .update((newFormSubmission.date, newFormSubmission.webFormId))
  }


  def delete(id: Long): Future[Int] = {
    formSubmissionTable.filter(_.id === id).delete
  }

  def findByIdWithFields(id: Long): Future[Option[FormSubmissionDto]] = {

    implicit val getResultFF: GetResult[List[Map[String, String]]] =
      GetResult[List[Map[String, String]]](r =>
        (1 to r.numColumns).map(_ => Map.apply(r.nextString() -> r.nextString())).toList)

    implicit val getResultWF: GetResult[FormSubmissionDto] =
      GetResult(r => FormSubmissionDto(r.<<, LocalDateTime.parse(r.nextString()), r.<<, r.<<))

    val formQuery =
      sql"""SELECT
            "wf".id as id,
            "wf".title as title,
            array_agg((SElECT * FROM "form_fields" AS "ff" WHERE "ff".web_form_id = "wf".id)) as fields
        FROM "web_forms" AS "wf"
        WHERE "wf".id = $id
         """.as[FormSubmissionDto]

    db.run(formQuery)
  }
}