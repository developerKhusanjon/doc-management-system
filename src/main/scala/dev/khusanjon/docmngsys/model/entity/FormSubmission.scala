package dev.khusanjon.docmngsys.model.entity

import java.time.LocalDateTime

case class FormSubmission(id: Option[Long], date: LocalDateTime, webFormId: Long)
