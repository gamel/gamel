name := "GAMEL"

version := "1.0"

scalaSource in Compile := baseDirectory.value / "src/main"

scalaSource in Test := baseDirectory.value / "src/test"

libraryDependencies ++= Seq(
        "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test", 
        "junit" % "junit" % "4.11" % "test"
)

initialCommands in console := """println("GAMEL Project by Tianyu Cheng, Mark Mansi and Benjamin Lin!!!")"""

scalacOptions += "-deprecation"
