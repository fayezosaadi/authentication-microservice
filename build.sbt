name := "auth-app"

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(postgres, jbcrypt, slick, playSlick, playSlickEvolutions, guice)

val slickV = "3.2.1"
val h2V = "1.4.193"
val postgresV = "42.2.1"
val playSlickV = "3.0.1"
val jbcryptV = "0.4"

val slick = "com.typesafe.slick" %% "slick" % slickV
val h2 = "com.h2database" % "h2" % h2V
val postgres = "org.postgresql" % "postgresql" % postgresV
val playSlick = "com.typesafe.play" %% "play-slick" % playSlickV
val playSlickEvolutions = "com.typesafe.play" %% "play-slick-evolutions" % playSlickV
val jbcrypt = "org.mindrot" % "jbcrypt" % jbcryptV