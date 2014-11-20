package gamel

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
    case ("KeyTyped", source: Component, ch: Char, mod: Int, loc: Key.Location.Value) => {
      global.eventQueue += new GamelKeyEvent {
        name = "KeyTyped"
        char = ch
        modifiers = mod
        location = loc
      }
    }
    case ("KeyPressed", source: Component, k: Key.Value, mod: Int, loc: Key.Location.Value) => {
      global.eventQueue += new GamelKeyEvent {
        name = "KeyPressed"
        key = k
        modifiers = mod
        location = loc
      }
    }
    case ("KeyReleased", source: Component, k: Key.Value, mod: Int, loc: Key.Location.Value) => {
      global.eventQueue += new GamelKeyEvent {
        name = "KeyReleased"
        key = k
        modifiers = mod
        location = loc
      }
    }
    case _       => Console.err.println("huh? you pressed a key?")
  }

}

class MouseMessageActor extends MessageActor {

  // source: source of mouse action, e.g. a button
  // point: position of mouse action
  // mod: combination with keys
  // clicks: time of clicks
  def receive = {
    case ("MouseClicked", source: Component, pt: Point, mod: Int, clk: Int) => {
      global.eventQueue += new GamelMouseEvent {
        name = "MouseClicked"
        point = pt
        modifiers = mod
        clicks = clk
      }
    }
    case ("MouseWheelMoved", source: Component, pt: Point, mod: Int, rot: Int) => {
      global.eventQueue += new GamelMouseEvent {
        name = "MouseWheelMoved"
        point = pt
        modifiers = mod
        rotation = rot
      }
    }
    case ("MouseDragged", source: Component, pt: Point, mod: Int) => {
      global.eventQueue += new GamelMouseEvent {
        name = "MouseDragged"
        point = pt
        modifiers = mod
      }
    }
    case ("MouseEntered", source: Component, pt: Point, mod: Int) => {
      global.eventQueue += new GamelMouseEvent {
        name = "MouseEntered"
        point = pt
        modifiers = mod
      }
    }
    case ("MouseMoved", source: Component, pt: Point, mod: Int) => {
      global.eventQueue += new GamelMouseEvent {
        name = "MouseMoved"
        point = pt
        modifiers = mod
      }
    }
    case ("MousePressed", source: Component, pt: Point, mod: Int, clk: Int) => {
      global.eventQueue += new GamelMouseEvent {
        name = "MousePressed"
        point = pt
        modifiers = mod
        clicks = clk
      }
    }
    case ("MouseReleased", source: Component, pt: Point, mod: Int, clk: Int) => {
      global.eventQueue += new GamelMouseEvent {
        name = "MouseReleased"
        point = pt
        modifiers = mod
        clicks = clk
      }
    }
    case _ => {
      Console.err.println("huh? are you sure you are sending the correct message to mouse listener?")
    }
  }

}

class GameMessageActor extends MessageActor {
  def receive = {
    case "repaint" => { gamel.window.repaint() }
    case "pause"   => { gamel.running = false }
    case "resume"  => { gamel.running = true  }
    case "exit"    => { 
      gamel.exit = true 
      gamel.window.close()
    }
  }
}

class ConsoleMessageActor extends MessageActor {
  def receive = {
    case (dev, msg) => println("[" + dev + "] " + msg)
    case msg        => println(msg)
  }
}
