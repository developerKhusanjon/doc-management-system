package dev.khusanjon.docmngsys.model.dao

import dev.khusanjon.docmngsys.config.DatabaseConfig
import dev.khusanjon.docmngsys.model.db.{CompanyTable, EmployeeTable, FieldValueTable, FormFieldTable, FormSubmissionTable, WebFormTable}
import slick.dbio.NoStream
import slick.lifted.TableQuery
import slick.sql.{FixedSqlStreamingAction, SqlAction}

import scala.concurrent.Future

trait BaseDao extends DatabaseConfig {
  val companyTable: TableQuery[CompanyTable] = TableQuery[CompanyTable]
  val employeeTable: TableQuery[EmployeeTable] = TableQuery[EmployeeTable]
  val webFormTable: TableQuery[WebFormTable] = TableQuery[WebFormTable]
  val formFieldTable: TableQuery[FormFieldTable] = TableQuery[FormFieldTable]
  val formSubmissionTable: TableQuery[FormSubmissionTable] = TableQuery[FormSubmissionTable]
  val fieldValueTable: TableQuery[FieldValueTable] = TableQuery[FieldValueTable]

  protected implicit def executeFormDb[A](action: SqlAction[A, NoStream, _ <: slick.dbio.Effect]): Future[A] = {
    db.run(action)
  }

  protected implicit def executeReadStreamFormDb[A](action: FixedSqlStreamingAction[Seq[A], A, _ <: slick.dbio.Effect]): Future[Seq[A]] = {
    db.run(action)
  }
}