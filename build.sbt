ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

version := "0.0.1"
name := "doc-management-system"

fork := true

lazy val DB_HOST=sys.env.getOrElse("POSTGRESQL_ADDON_HOST", "localhost")
lazy val DB_PORT=sys.env.getOrElse("POSTGRESQL_ADDON_PORT", "5432")
lazy val DB_NAME=sys.env.getOrElse("POSTGRESQL_ADDON_DB", "postgres")
lazy val DB_USER=sys.env.getOrElse("POSTGRESQL_ADDON_USER", "login")
lazy val DB_PASS=sys.env.getOrElse("POSTGRESQL_ADDON_PASSWORD", "pass")

lazy val root = (project in file("."))
  .settings(
    inThisBuild(
      List(organization := "dev.khusanjon", scalaVersion := "2.13.10")
    ),
    libraryDependencies ++= {
      val akkaVersion = "2.6.19"
      val akkaHttpVersion = "10.2.9"
      val scalaTestVersion = "3.2.13"
      val scalaMockV = "3.5.0"
      val slickVersion = "3.4.1"
      Seq(
        "com.typesafe.akka" %% "akka-actor" % akkaVersion,
        "com.typesafe.akka" %% "akka-stream" % akkaVersion,

        "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,

        "com.typesafe.slick" %% "slick" % slickVersion,
        "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
        "org.slf4j" % "slf4j-nop" % "1.7.25",
        "org.postgresql" % "postgresql" % "42.1.4",
        "org.flywaydb" % "flyway-core" % "9.5.0",

        "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
        "org.scalamock" %% "scalamock-scalatest-support" % scalaMockV % Test
      )
    }
  )

enablePlugins(FlywayPlugin)

lazy val CustomConfig = config("custom") extend Runtime
lazy val customSettings: Seq[Def.Setting[_]] = Seq(
  flywayUser := "docker",
  flywayPassword := "docker",
  flywayUrl := "jdbc:postgresql://localhost:5432/docdb",
  flywayLocations += "db/migration"
)

lazy val flyWay = (project in file("."))
  .settings(inConfig(CustomConfig)(FlywayPlugin.flywayBaseSettings(CustomConfig) ++
    customSettings): _*)
