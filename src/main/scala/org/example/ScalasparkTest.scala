package org.example
import org.apache.spark.sql.SparkSession

object ScalasparkTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.master("local").appName("test").getOrCreate()
    print(spark)
  }
}
