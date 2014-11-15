name := "GAMEL"

version := "1.0"

scalaVersion := "2.11.4"

scalaSource in Compile := baseDirectory.value / "src/main" 

scalaSource in Test := baseDirectory.value / "src/test"

compileOrder := CompileOrder.JavaThenScala

libraryDependencies ++= Seq(
        "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test", 
        "junit" % "junit" % "4.11" % "test",
        "org.scala-lang" % "scala-swing" % "2.11.0-M7",
        "com.typesafe.akka" % "akka-actor_2.11" % "2.3.2"
)

initialCommands in console := """println("GAMEL Project by Tianyu Cheng, Mark Mansi and Benjamin Lin!!!")"""

scalacOptions ++= Seq(
        "-deprecation",
        "-feature",
        "-language:implicitConversions",
        "-language:postfixOps"
        )
