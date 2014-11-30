package gamel

import scala.collection.mutable.{Map, HashMap}

protected[gamel] abstract class GamelGame {

  var name: String = null

  var description: String = null

  var resolution: (Int, Int) = null

  var startScene: Symbol = null

  var currentScene: GamelScene = null

  var fps = 30

  var fullscreen = false       // not supported yet

  /**
   * switchToScene will initialize the scene if
   * this scene has not been initialized
   * This is due to the fact that GAMEL does
   * not require forward declaration
   * */
  def switchToScene(sce: Symbol): Unit = {
    // sanity check for argument
    if (sce == null)
      throw new IllegalArgumentException("The scene to switch to cannot be null!")
    // sanitity check for existence of this scene
    if (!(global.scenes contains sce))
      throw new UndefinedInstanceException("the scene " + sce + " has not been created")

    // get the scene
    val destScene = global.scenes(sce)

    // load the scene
    destScene load

    // switching to the dest scene
    gamel.game.currentScene = destScene
  }
}

/**
 * This is a syntactic sugar for client
 * code to be able to write
 *  create new entity ...
 * */
class game extends GamelGame {}
