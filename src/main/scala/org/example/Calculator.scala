package org.example
import scala.util.control.Breaks._
object Calculator {
  def main(args: Array[String]): Unit = {

    def calculate( a:Int, b:Int ) : Unit = {
      var sum = a + b
      println(sum)
      var sub = a - b
      println(sub)
      var mul = a * b
      println(mul)
      var div = a / b
      println(div)

    }

    var I = "Y"
//    I = scala.io.StdIn.readChar()

    while(I == "Y"){
      var a = scala.io.StdIn.readInt()
      var b = scala.io.StdIn.readInt()
      calculate( a:Int, b:Int )
      I = scala.io.StdIn.readLine()
      if(I == "N"){
        break
      }
    }
  }

}
