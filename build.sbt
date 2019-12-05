name := "poe-inventory"

version := "0.0.1"

scalaVersion := "2.11.8"

resolvers += "Lcl" at  "file:///C:/Users/jalin/.ivy2/local/default"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

// https://github.com/typesafehub/scala-logging
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"

// https://mvnrepository.com/artifact/com.melloware/jintellitype
libraryDependencies += "com.melloware" % "jintellitype" % "1.3.9"

// https://mvnrepository.com/artifact/org.ini4j/ini4j
libraryDependencies += "org.ini4j" % "ini4j" % "0.5.1"

// https://mvnrepository.com/artifact/org.mongodb.scala/mongo-scala-driver_2.11
libraryDependencies += "org.mongodb.scala" % "mongo-scala-driver_2.11" % "2.0.0"
