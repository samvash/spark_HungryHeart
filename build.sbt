import sbt._
import Keys._

name := "otmu_scala"

version := "1.0"

scalaVersion := "2.10.5"

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

// additional libraries
libraryDependencies ++= Seq(
  ("org.apache.spark" % "spark-core_2.10" % "1.4.1").
    exclude("org.eclipse.jetty.orbit", "javax.mail").
    exclude("org.eclipse.jetty.orbit", "javax.activation").
    exclude("com.esotericsoftware.minlog", "minlog").
    exclude("commons-beanutils", "commons-beanutils-core").
    exclude("commons-collections", "commons-collections").
    exclude("org.jboss.netty", "commons-collections").
    exclude("com.esotericsoftware.minlog", "minlog").
  exclude("org.jboss.netty", "commons-collections"),
  ("org.apache.spark" % "spark-sql_2.10" % "1.4.1").
    exclude("org.eclipse.jetty.orbit", "javax.mail").
    exclude("org.eclipse.jetty.orbit", "javax.activation").
    exclude("com.esotericsoftware.minlog", "minlog").
    exclude("commons-beanutils", "commons-beanutils-core").
    exclude("commons-collections", "commons-collections").
    exclude("org.jboss.netty", "commons-collections").
    exclude("com.esotericsoftware.minlog", "minlog").
    exclude("org.jboss.netty", "commons-collections"),
  ("org.apache.hbase" % "hbase-common" % "0.98.6-cdh5.3.1").
     exclude("org.jboss.netty", "netty").
     exclude("org.mortbay.jetty", "javax.servlet"),
("org.apache.hbase" % "hbase-client" % "0.98.6-cdh5.3.1"). exclude("org.jboss.netty", "netty").
  exclude("org.mortbay.jetty", "javax.servlet"),
  ("org.apache.hbase" % "hbase-server" % "0.98.6-cdh5.3.1"). exclude("org.jboss.netty", "netty").
    exclude("org.mortbay.jetty", "javax.servlet")
)

//exclude("commons-beanutils", "commons-beanutils-core").
//  exclude("commons-collections", "commons-collections").
//  exclude("commons-collections", "commons-collections").
////"Twitter4J Repository" at "http://twitter4j.org/maven2/",

resolvers ++= Seq(
  "Spark-Hbase repo" at "https://repository.apache.org/content/repositories/snapshots",
  "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
  "cloudera-repo-releases" at "https://repository.cloudera.com/artifactory/repo/",
  Resolver.sonatypeRepo("public")
)

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>{
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case "about.html" => MergeStrategy.rename
  case x => old(x)
}
}