package idea.gamel

import scala.collection.mutable.{Map, HashMap}

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

abstract class MessageActor extends Actor

class KeyMessageActor extends MessageActor {
  def receive = {
    case _       => println("huh? you pressed a key?")
  }
}

class MouseMessageActor extends MessageActor {
  def receive = {
    case _       => println("huh? you move the mouse?")
  }
}
