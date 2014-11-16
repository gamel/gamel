import gamel._
import scala.swing._

import java.io.File
import java.awt.Font
import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

object SceneRenderer extends GamelRenderer {

  val scene = use image "startBackground"

  def render(self: GamelEntity, g2d: Graphics2D): Unit = {
    var size = gamel.game.resolution
    g2d.drawImage(scene, 0, 0, size._1, size._2, null)

    g2d.setFont(new Font("TimesRoman", Font.PLAIN, 48));
    g2d.setColor(Color.WHITE)
    g2d.drawString("Hello World Advanture", 20, 50)
    g2d.drawString("This is the starting scene", 20, 120)
  }

}

object EntityRenderer extends GamelRenderer {

  def render(self: GamelEntity, g2d: Graphics2D): Unit = {
    var pos = (self tell "position").asInstanceOf[Tuple2[Int, Int]]
    var color = (self tell "shirtcolor").asInstanceOf[Int]
    var name = (self tell "name").asInstanceOf[String]

    var time = gamel.time
    var size = gamel.game.resolution

    g2d.translate(pos._1, pos._2 + (Math.sin(time) * 100).toInt);
    g2d.setColor(new Color(color))
    g2d.setFont(new Font("TimesRoman", Font.PLAIN, 18));
    g2d.drawString(name, -30, -30)
    g2d.fillRect(-25, -25, 50, 50);
  }

}

object CoolGamel extends GamelApp {

  println("Hi! Welcome to IDEA GAMEL!")

  turn KeyTyped on
  turn KeyPressed on
  turn KeyReleased on
  turn MouseClicked on
  turn MouseWheelMoved on

  require image "res/scene.jpg" as "startBackground"

  define a new action {
    name = 'fall
    action = (l: List[Any]) => println("ouch")
  }

  define a new action {
    name = 'stand
    action = (l: List[Any]) => println("I'm ok... :)")
  }

  define a new entity {
    name = 'Player
    attributes += (
      "description" -> "player of the game",
      "position"    -> (0, 0),
      "hp"          -> 100,
      "sp"          -> 100
    )
    actions += (
      'fall,
      'stand,
      'say
    )
    renderer = EntityRenderer
  }

  create a new scene {
    name = 'start
    attributes += ("description" -> "this is the starting scene")
    renderer = SceneRenderer
    objects += ( 'tianyu, 'mark, 'ben )
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
    resolution  = (1024, 768)
    startScene = 'start
    fullscreen = false
  }

  create a new instance {
    name = 'tianyu
    attributes += (
      "name"        -> "Tianyu",
      "position"    -> (500,503),
      "shirtcolor"  -> 0x00ffff
    )
  } of 'Player

  create a new instance { 
    name = 'mark
    attributes += (
      "name"        -> "Mark",
      "position"    -> (203,300),
      "shirtcolor"  -> 0xffff00
    )
    objects += (
      'hat
    )
  } of 'Player

  create a new instance { 
    name = 'ben
    attributes += (
      "name"        -> "Ben",
      "position"    -> (303,400),
      "shirtcolor"  -> 0xff00ff
    )
  } of 'Player

  create a new instance {
    name = 'hat
    attributes += (
       "name"       -> "chapeau"   
    )
  }

  'mark gives 'hat to 'tianyu

  // 'hat to 'tianyu

  start game

}
