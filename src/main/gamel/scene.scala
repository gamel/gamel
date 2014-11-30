package gamel

import scala.swing._
import scala.collection.mutable.{Map, HashMap}

protected[gamel] abstract class GamelScene extends GamelEntity {

  protected[gamel] var loaded = false

  /**
   * Every child class of Entity will want to
   * override this method if it hopes to
   * actually render some stuff on the screen
   * */
  override def draw(g: Graphics2D): Unit = {
    // call parent draw method
    super.draw(g)
  }

  /**
   * Initializes objects, renderers, and actions.
   * Does nothing after the first call.
   * */
  def load(): Unit = {
    // check whether the scene has been initialized because we are doing lazy evaluation
    if(loaded) return

    // load the scene and its objects
    initObjects

    // initializing renderers
    initRenderers

    // load scene's actions
    initActions

    // mark the scene as loaded so we don't load this again
    loaded = true
  }

}

/**
 * This is a syntactic sugar for client
 * code to be able to write
 *
 *    create a new scene ...
 *
 * */
class scene extends GamelScene { }
