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
    var size = global.game.resolution
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

  turn KeyTyped on
  turn KeyPressed on
  turn KeyReleased on
  turn MouseClicked on
  turn MouseWheelMoved on

  define a new entity {
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

  create a new scene {
    name = 'start
    attributes += (("description", "this is the starting scene"))
    renderer = SceneRenderer.drawStartScene
  }

  create a new scene {
    name = 'room1
    attributes += ("description" -> "this is the first room")
  }

  create a new scene {
    name = 'room2
    attributes += ("description" -> "this is the second room")
  }

  create a new scene {
    name = 'end
    attributes += ("description" -> "this is the ending scene")
  }

  create a new game {
    name = "Hello World Advanture"
    description = "Hey, World! How are you? Hello World Advanture is a demo of GAMEL"
    resolution  = (800, 600)
    startScene = 'start
    fullscreen = false
  }

  start game

}
