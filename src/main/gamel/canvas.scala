package gamel

import java.awt.Color
import java.awt.image.BufferedImage

import scala.swing._
import scala.collection.mutable.{Map, HashMap}

protected[gamel] class GamelCanvas extends Panel {

  // double buffer
  protected[gamel] var offscreen: BufferedImage = null

  /**
   * Rendering Function
   * @param g Graphics2D
   * @return Unit
   * */
  protected[gamel] override def paintComponent(g: Graphics2D): Unit = {

    if (offscreen == null) {
      offscreen = new BufferedImage(size.width, size.height, BufferedImage.TYPE_4BYTE_ABGR)
    }

    var g2d = offscreen.createGraphics()

    clearBackground(g2d)
    drawScene(g2d)
    drawEntities(g2d)

    g.drawImage(offscreen, 0, 0, size.width, size.height, null)
  }

  protected[gamel] def clearBackground(g2d: Graphics2D): Unit = {
    g2d.setColor(Color.BLACK)
    g2d.fillRect(0, 0, size.width, size.height)
  }

  protected[gamel] def drawScene(g2d: Graphics2D): Unit = {
    var scene = gamel.game.currentScene
    // if there is no scene, skip it
    if (scene == null) return
    // if the scene does not have a render method, skip it
    if (scene.renderer == null) return

    scene.draw(g2d)
  }

  protected[gamel] def drawEntities(g2d: Graphics2D): Unit = {
    var scene = gamel.game.currentScene
    scene.objects foreach {
      entry => {
        val ent = entry._2
        ent.draw(g2d)
      }
    }
  }

}
