logLevel := Level.Warn

resolvers += Classpaths.sbtPluginReleases

addSbtPlugin("io.spray" %% "sbt-revolver" % "0.9.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.2")
addSbtPlugin("io.github.davidmweber" % "flyway-sbt" % "5.0.0-RC2")