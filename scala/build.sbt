name := """spark-twitter-streaming-etl"""                                                 
  
version := "1.0.0"

scalaVersion := "2.11.12"

fork := true

libraryDependencies ++= {
  val sparkVer = "2.2.0"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVer withSources(),
    "org.apache.spark" %% "spark-streaming" % sparkVer withSources(),
    "org.apache.bahir" %% "spark-streaming-twitter" % "2.2.0"
  )
}
