package idea.gamel

import java.awt.Point

import scala.collection.mutable.{Map, HashMap}

import scala.swing._
import scala.swing.event._

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props


/**
 * Introduction to Actors
 * Actors are blocked until it receives some 
 * message. Then it make decision according 
 * to the message it receives.
 * We use actors to implement our 
 * messaging system
 * */

abstract class MessageActor extends Actor

class KeyMessageActor extends MessageActor {

  def receive = {
    case ("KeyTyped", source: Component, char: Char, modifiers: Int, location: Key.Location.Value) => {
      println("pressed " + char)
    }
    case ("KeyPressed", source: Component, key: Key.Value, modifiers: Int, location: Key.Location.Value) => {
      println("pressed key " + key)
    }
    case ("KeyReleased", source: Component, key: Key.Value, modifiers: Int, location: Key.Location.Value) => {
      println("release key " + key)
    }
    case _       => println("huh? you pressed a key?")
  }

}

class MouseMessageActor extends MessageActor {

  // source: source of mouse action, e.g. a button
  // point: position of mouse action
  // modifiers: combination with keys
  // clicks: time of clicks
  def receive = {
    case ("MouseClicked", source: Component, point: Point, modifiers: Int, clicks: Int) => {
      println("mouse click at (" + point.x + ", " + point.y + ") " + clicks + " times")
      // dynamicall add user define actions here
      // usually the mouse action is defined by the scene
    }
    case ("MouseWheelMoved", source: Component, point: Point, modifiers: Int, rotation: Int) => {
      println("mouse wheel moved!")
    }
    case ("MouseButtonEvent")    => {
      println("mouse button event")
    }
    case ("MouseDragged", source: Component, point: java.awt.Point, modifiers: Modifiers) => {
      println("mouse draged")
    }
    case ("MouseEntered", source: Component, point: java.awt.Point, modifiers: Modifiers) => {
      println("mouse entered")
    }
    case ("MouseMotionEvent")    => {
      println("moused motion")
    }
    case ("MouseMoved", source: Component, point: java.awt.Point, modifiers: Modifiers) => {
      println("mouse moved")
    }
    case ("MousePressed", source: Component, point: java.awt.Point, modifiers: Modifiers, clicks: Int) => {
      println("mouse pressed")
    }
    case ("MouseReleased", source: Component, point: java.awt.Point, modifiers: Modifiers, clicks: Int) => {
      println("mouse released")
    }
    case _ => {
      Console.err.println("huh? are you sure you are sending the correct message to mouse listener?")
    }
  }

}

class ConsoleMessageActor extends MessageActor {
  def receive = {
    case (dev, msg) => println("[" + dev + "] " + msg)
    case msg        => println(msg)
  }
}
