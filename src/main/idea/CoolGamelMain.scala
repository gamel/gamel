import idea.gamel._
import scala.swing._

import java.io.File
import java.awt.Font
import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

object SceneRenderer {

  var scene: BufferedImage = null

  def init(): Unit = {
    scene = ImageIO.read(new File("res/scene.jpg"));
  }

  def drawStartScene(g2d: Graphics2D): Unit = {
    var size = global.game.windowSize
    g2d.drawImage(scene, 0, 0, size._1, size._2, null)

    g2d.setFont(new Font("TimesRoman", Font.PLAIN, 48)); 
    g2d.setColor(Color.WHITE)
    g2d.drawString("Hello World Advanture", 20, 50)
    g2d.drawString("This is the starting scene", 20, 120)
  }

}

object CoolGamel extends GamelApp {

  println("Hi! Welcome to IDEA GAMEL!")

  SceneRenderer.init()

  define <= new entity {
    name = 'Player
    attributes += ( 
      "description" -> "player of the game",
      "name"        -> "Tianyu",
      "position"    -> (0, 0),
      "hp"          -> 100,
      "sp"          -> 100
    )
    renderer = (g2d: Graphics2D) => {
      println("painting Player")
    }
  }

  create <= new scene {
    name = 'start
    attributes += (("description", "this is the starting scene"))
    renderer = SceneRenderer.drawStartScene
  }

  create <= new scene {
    name = 'room1
    attributes += ("description" -> "this is the first room")
  }

  create <= new scene {
    name = 'room2
    attributes += ("description" -> "this is the second room")
  }

  create <= new scene {
    name = 'end
    attributes += ("description" -> "this is the ending scene")
  }

  create <= new game {
    name = "Hello World Advanture"
    description = "Hey, World! How are you? Hello World Advanture is a demo of GAMEL"
    windowSize = (800, 600)
    startScene = 'start
    fullscreen = false
  }

  gamel.start()

}
