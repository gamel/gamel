package idea.gamel

import akka.actor.ActorSystem
import akka.actor.Props

import scala.collection.mutable.{Map, HashMap}

object gamel {

  var time: Long = 0    // game time since game start, created for convinience
  var running = true
  var exit    = false

  var window: GamelWindow = null;
  val system: ActorSystem = ActorSystem("GamelSystem")
  val keyboard = system.actorOf(Props[KeyMessageActor], name = "keyActor")
  val mouse    = system.actorOf(Props[MouseMessageActor], name = "mouseActor")
  val console  = system.actorOf(Props[ConsoleMessageActor], name = "consoleActor")
  val gameMsg  = system.actorOf(Props[GameMessageActor], name = "gameActor")

  def start(): Unit = { 
    // in order to start the game,
    // the user must have created
    // the game object 
    if (global.game == null)
      throw new IllegalStateException("The game object has not been created yet!")

    // set up the back-end
    global.game.currentScene = global.game.startScene

    var size = global.game.resolution

    // start the front-end
    window = new GamelWindow(global.game.name, size._1, size._2, global.game.fullscreen)
    window.startup(Array[String]())

    // Debugger functionality
    console ! ("tianyu", "Starting game")

    mainLoop
  }
  
  def mainLoop(): Unit = {
    // calculate and check the interval
    var interval = (1000.0 / global.game.fps).toInt

    if (interval <= 0)
      throw new IllegalStateException("fps is not valid!")

    while (!exit) {
      if (running) {
        val start = System.currentTimeMillis()
        val duration = System.currentTimeMillis() - start

        gamel.time += duration
        if (duration < interval) Thread.sleep(interval - duration)
      }
    }
  }

}
