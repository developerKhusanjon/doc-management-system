ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

enablePlugins(FlywayPlugin)
version := "0.0.1"
name := "flyway-sbt-test1"

libraryDependencies += "org.hsqldb" % "hsqldb" % "2.5.0"

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayPassword := ""
flywayLocations += "db/migration"
flywayUrl in Test := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser in Test := "SA"
flywayPassword in Test := ""

val DoobieVersion = "1.0.0-RC1"
val NewTypeVersion = "0.4.4"

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core"     % DoobieVersion,
  "org.tpolecat" %% "doobie-postgres" % DoobieVersion,
  "org.tpolecat" %% "doobie-hikari"   % DoobieVersion,
  "io.estatico"  %% "newtype"         % NewTypeVersion
)

lazy val akkaHttpVersion = "10.2.9"
lazy val akkaVersion = "2.6.19"

fork := true

mappings in (Compile, packageDoc) := Seq()

lazy val DB_HOST=sys.env.get("POSTGRESQL_ADDON_HOST").getOrElse("localhost")
lazy val DB_PORT=sys.env.get("POSTGRESQL_ADDON_PORT").getOrElse("5432")
lazy val DB_NAME=sys.env.get("POSTGRESQL_ADDON_DB").getOrElse("postgres")
lazy val DB_USER=sys.env.get("POSTGRESQL_ADDON_USER").getOrElse("login")
lazy val DB_PASS=sys.env.get("POSTGRESQL_ADDON_PASSWORD").getOrElse("pass")

lazy val root = (project in file("."))
  .settings(
    inThisBuild(
      List(organization := "dev.khusanjon", scalaVersion := "2.13.10")
    ),
    name := "doc-management-system",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      "org.scalatest" %% "scalatest" % "3.1.4" % Test,
      // Start with this one
      "org.tpolecat" %% "doobie-core" % "1.0.0-RC1",
      // And add any of these as needed
      "org.tpolecat" %% "doobie-postgres" % "1.0.0-RC1", // Postgres driver 42.3.1 + type mappings.
    )
  )
