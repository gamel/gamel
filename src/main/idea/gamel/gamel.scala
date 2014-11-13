package idea.gamel

import scala.collection.mutable.{Map, HashMap}

object gamel {

  private var window: GamelWindow = null;

  def start(): Unit = { 
    
    // in order to start the game,
    // the user must have created
    // the game object 
    if (global.game == null)
      throw new IllegalStateException("The game object has not been created yet!")

    // set up the back-end
    global.game.currentScene = global.game.startScene

    var size = global.game.windowSize

    // start the front-end
    window = new GamelWindow(global.game.name, size._1, size._2)
    window.startup(Array[String]())
  }
  

}
