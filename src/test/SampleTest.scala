import org.scalatest._

class TestHelloWorld extends FlatSpec with Matchers {

  "Hello World" should "print hello world greeting" in {
    val result = "Hello, World!"
    result should be ("Hello, World!")
  }

}
