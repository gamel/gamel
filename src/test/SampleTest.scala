import org.scalatest._

class TestHelloWorld extends FlatSpec {

  "Hello World" should "print hello world greeting" in {
    val result = "Hello, World!"
    assert(result == "Hello, World!")
  }

}
