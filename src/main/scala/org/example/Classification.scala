package org.example
import org.apache.spark.sql.SparkSession

object Classification {

  case class Logging(level:String, datetime:String)

  def mapper(line:String): Logging =  {
    val fields = line.split(",")
    val logging:Logging = Logging(fields(0),fields(1))
    return logging
  }
  def main(args: Array[String]): Unit = {

//    Setting the log level to print only errors
//    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder.master("local").appName("ClassificationOfLogs").getOrCreate()

    val myList = List("WARN,2016-12-31 04:19:32","FATAL,2016-12-31 03:22:34","WARN,2016-12-31 03:21:21",
    "INFO,2015-4-21 14:32:21","FATAL,2015-4-21 19:23:20")

    val rdd1 = spark.sparkContext.parallelize(myList)
    val rdd2 = rdd1.map(mapper)

    import spark.implicits._
    val df1  = rdd2.toDF()
    df1.show()

    df1.createOrReplaceTempView("logging_table")

    spark.sql("select * from logging_table").show()
    spark.sql("select level,collect_list(datetime) from logging_table group by level order by level").show(false)
    val df2 = spark.sql("select level,date_format(datetime,'MMMM') as month from logging_table")

    df2.createOrReplaceTempView("new_logging_table")

    spark.sql("select level,month,count(1) from new_logging_table group by level, month").show(false)

    val columns = List("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")

    val results3 = spark.sql("""select level, date_format(datetime,'MMMM')as month
      from logging_table """).groupBy("Level").pivot("month",columns).count().show(false)


  }
}
