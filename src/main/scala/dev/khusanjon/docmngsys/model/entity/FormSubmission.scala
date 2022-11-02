package dev.khusanjon.docmngsys.model.entity

import java.sql.Date

case class FormSubmission(id: Option[Long], date: Date, webFormId: Long)
