package idea.gamel

import scala.collection.mutable.{Map, HashMap}

abstract class GamelGame {

  var name: String = null

  var description: String = null

  var windowSize: (Int, Int) = null

  var startScene: GamelScene = null

  var currentScene: GamelScene = null

  var fps = 30

  // var fullscreen = false       // not supported yet
  
}

/**
 * This is a syntactic sugar for client
 * code to be able to write
 *  create new entity ...
 * */
class game extends GamelGame {}
