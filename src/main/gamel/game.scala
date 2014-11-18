package gamel

import scala.collection.mutable.{Map, HashMap}

abstract class GamelGame {

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

    // check whether the scene has been initialized because we are doing lazy evaluation
    if (!destScene.loaded) {
      // load the scene and its objects
      destScene.objects foreach {
        entry => {
          var key = entry._1
          val obj = entry._2
          // check if obj is initiated
          if (obj == null) {
            if (global.entities contains key)
              destScene.objects(key) = global.entities(key)
            else
              throw new UndefinedInstanceException("instance " + key + " has not been found!")
          }
        }
      }
      // mark the scene as loaded so we don't load this again
      destScene.loaded = true
    }
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
