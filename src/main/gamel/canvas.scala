package gamel

import java.awt.Color
import java.awt.image.BufferedImage  

import scala.swing._
import scala.collection.mutable.{Map, HashMap}

class GamelCanvas extends Panel with Runnable {
  
  // double buffer
  var offscreen: BufferedImage = null
  var running: Boolean = false

  override def run(): Unit = {
    // calculate and check the interval
    var interval = (1000.0 / global.game.fps).toInt

    if (interval <= 0) 
      throw new IllegalStateException("fps is not valid!")

    while (true) {
      val start = System.currentTimeMillis()
      repaint()
      val duration = System.currentTimeMillis() - start
      if (duration < interval) Thread.sleep(interval - duration)
    }
  }

  /**
   * an API for others to start repainting
   * */
  def start() {
    running = true
    new Thread(this).start()
  }

  def pause() {
    running = false
  }

  def resume() {
    running = true
  }

  /**
   * Rendering Function
   * @param g Graphics2D
   * @return Unit
   * */
  override def paintComponent(g: Graphics2D): Unit = {
    if (offscreen == null) {
      offscreen = new BufferedImage(size.width, size.height, BufferedImage.TYPE_4BYTE_ABGR)
    }
    var g2d = offscreen.createGraphics()
    clearBackground(g2d)

    drawScene(g2d) 
    drawEntities(g2d)

    g.drawImage(offscreen, 0, 0, size.width, size.height, null)
  }
  
  def clearBackground(g2d: Graphics2D): Unit = {
    g2d.setColor(Color.BLACK)
    g2d.fillRect(0, 0, size.width, size.height)
  }

  def drawScene(g2d: Graphics2D): Unit = {
    var scene = global.game.currentScene

    // if there is no scene, skip it
    if (scene == null) return

    // if the scene does not have a render method, skip it
    if (scene.renderer == null) return

    scene.draw(g2d)
  }

  def drawEntities(g2d: Graphics2D): Unit = {
    // var scene = global.game.currentScene
  }

}
