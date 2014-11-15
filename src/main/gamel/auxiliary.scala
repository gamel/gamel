package gamel

import java.io.File
import java.awt.Image
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import scala.swing._

/**
 * Gamel Auxiliary Object
 * 
 * This is a collection of all gamel
 * auxiliary objects, including the
 * resource object, logic object, etc
 * */

abstract class GamelResource {

  var resource: Any = null

  def as(name: String): Unit = {
    if (global.resources contains name) {
      // throw new ItemAlreadyExistsException(name + " already exits in resources")
      throw new IllegalStateException(name + " already exits in resources")
    }
    global.resources(name) = this
  }

}

case class GamelImage(uri: String) extends GamelResource {
  resource = ImageIO.read(new File(uri));
}

case class GamelSound(uri: String) extends GamelResource {
  // not support yet
}

trait GamelRenderer {
  def render(g2d: Graphics2D): Unit
}
