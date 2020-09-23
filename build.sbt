name := "to-sqlite"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.0.0",
  "org.tpolecat" %% "doobie-core" % "0.9.0",
  "org.tpolecat" %% "doobie-specs2" % "0.9.0",
  "org.xerial" % "sqlite-jdbc" % "3.32.3.2"
)