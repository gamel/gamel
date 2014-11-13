import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class HelloActor extends Actor {
  def receive = {
    case "hello" => println("hello back at you")
    case "buenos dias" => println("no comprenddo")
    case _       => println("huh?")
  }
}

object Main extends App {
  val system = ActorSystem("HelloSystem")
  val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
  helloActor ! "hello"
  helloActor ! "buenos dias"
  helloActor ! "buenos dias"
  helloActor ! "buenos dias"
  helloActor ! "buenos dias"
  helloActor ! "buenos dias"
  helloActor ! "buenos dias"
  helloActor ! "buenos dias"
  helloActor ! "buenos dias"
  helloActor ! "buenos dias"
  helloActor ! "buenos dias"
  helloActor ! "buenos dias"
  helloActor ! "buenos dias"
  helloActor ! "buenos dias"
}
