package gamel

import scala.swing._

/**
 * Drawable represent a set of items that 
 * is drawable. It is for rendering items 
 * on the screen when the object is in the
 * current scene
 * */
trait Drawable {
  def draw(g: Graphics2D): Unit
}
