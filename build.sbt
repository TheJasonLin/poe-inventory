name := "poe-inventory"

version := "0.0.1"

scalaVersion := "2.11.8"

resolvers += "Lcl" at  "file:///C:/Users/j/.ivy2/local/default"

// https://mvnrepository.com/artifact/com.melloware/jintellitype
libraryDependencies += "com.melloware" % "jintellitype" % "1.3.9"

libraryDependencies += "default" % "poe-constants_2.11" % "0.0.6"

libraryDependencies += "default" % "poe-parser_2.11" % "0.0.5"