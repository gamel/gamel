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

}

/**
 * This is a syntactic sugar for client
 * code to be able to write
 *
 *    create a new scene ...
 *
 * */
class scene extends GamelScene { }
