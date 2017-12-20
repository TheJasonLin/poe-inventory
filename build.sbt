name := "poe-inventory"

version := "0.0.1"

scalaVersion := "2.11.8"

resolvers += "Lcl" at  "file:///C:/Users/jalin/.ivy2/local/default"


// https://github.com/typesafehub/scala-logging
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"

// https://mvnrepository.com/artifact/com.melloware/jintellitype
libraryDependencies += "com.melloware" % "jintellitype" % "1.3.9"

libraryDependencies += "default" % "poe-constants_2.11" % "0.0.6"

libraryDependencies += "default" % "poe-parser_2.11" % "0.1.10"