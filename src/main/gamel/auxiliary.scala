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

protected[gamel] abstract class GamelResource {

  var resource: Any = null

  def as(name: String): Unit = {
    if (global.resources contains name) {
      // throw new ItemAlreadyExistsException(name + " already exits in resources")
      throw new IllegalStateException(name + " already exits in resources")
    }
    global.resources(name) = this
  }

}

protected[gamel] case class GamelImage(uri: String) extends GamelResource {
  resource = ImageIO.read(new File(uri))
}

protected[gamel] case class GamelSound(uri: String) extends GamelResource {
  throw new UnsupportedOperationException("Not implemented yet")
}

trait GamelRenderer {
  def render(self: GamelEntity, g2d: Graphics2D): Unit
}
