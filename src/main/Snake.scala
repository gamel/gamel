import gamel._

import scala.util._
import scala.swing._
import scala.swing.event._

import java.awt.Font
import java.awt.Color

object GlobalSettings {
  val gridSize = 10
  val speed = 10

  var grid: Array[Array[Int]] = null
  var size: Tuple2[Int, Int] = null
  var xMax: Int = -1
  var yMax: Int = -1
  var snake: List[Tuple2[Int, Int]] = null
  var direction: String = null

  val EMPTY = 0
  val FOOD  = 1
  val SNAKE = 2

  var gameEnd = false
  var lastTime: Double = 0.0
}

object SnakeBackgroundRenderer extends GamelRenderer {

  override def init() {
    GlobalSettings.size = gamel.game.resolution
    GlobalSettings.xMax = (GlobalSettings.size._1 / GlobalSettings.gridSize)
    GlobalSettings.yMax = (GlobalSettings.size._2 / GlobalSettings.gridSize)
    GlobalSettings.grid = Array.ofDim[Int](GlobalSettings.xMax, GlobalSettings.yMax)
  }

  def render(self: GamelEntity, g2d: Graphics2D): Unit = {
    val size = GlobalSettings.size
    val gridSize = GlobalSettings.gridSize
    // draw background
    g2d.setColor(Color.BLACK)
    g2d.fillRect(0, 0, size._1, size._2)

    for (i <- 0 to GlobalSettings.xMax - 1) {
      for (j <- 0 to GlobalSettings.yMax - 1) {
        GlobalSettings.grid(j)(i) match {
          case GlobalSettings.SNAKE => g2d.setColor(Color.GRAY)
          case GlobalSettings.FOOD => g2d.setColor(Color.GREEN)
          case GlobalSettings.EMPTY => g2d.setColor(Color.BLACK)
        }
        g2d.fillRect(i * gridSize, j * gridSize, gridSize, gridSize)
      }
    }

    if (GlobalSettings.gameEnd) {
      g2d.setFont(new Font("TimesRoman", Font.PLAIN, 48));
      g2d.setColor(Color.WHITE)
      g2d.drawString("Game Ends", 20, 50)
    }
  }

}

object FoodRenderer extends GamelRenderer {

  def render(self: GamelEntity, g2d: Graphics2D): Unit = {
    var foodPos = (self tell "foodPos").asInstanceOf[Tuple2[Int, Int]]
    val gridSize = GlobalSettings.gridSize
    // draw food
    g2d.setColor(Color.YELLOW)
    g2d.fillRect(foodPos._1 * gridSize, foodPos._2 * gridSize, gridSize, gridSize)
  }

}

object SnakeRenderer extends GamelRenderer {

  override def init(): Unit = {
    val snake = use instance 'snake
    GlobalSettings .snake = (snake tell "body").asInstanceOf[List[Tuple2[Int, Int]]]
    GlobalSettings.direction = (snake tell "direction").asInstanceOf[String]
  }

  def render(self: GamelEntity, g2d: Graphics2D): Unit = {
    val gridSize = GlobalSettings.gridSize
    // draw body of snake
    g2d.setColor(Color.WHITE)
    GlobalSettings.snake foreach {
      pos => {
        g2d.fillRect(pos._1 * gridSize, pos._2 * gridSize, gridSize, gridSize)
      }
    }
  }

}

object Snake extends GamelApp {

  println("Hi! Welcome to Snake GAMEL!")

  turn KeyTyped off
  turn KeyPressed on
  turn KeyReleased off
  turn MouseClicked off
  turn MouseWheelMoved off

  define a new action {
    name = 'grow
    action = (l: List[Any]) => {
      val pos = l(0).asInstanceOf[Tuple2[Int, Int]]
      GlobalSettings.snake = GlobalSettings.snake :+ pos
    }
  }

  define a new action {
    name = 'newFood
    condition = (unit) => {
      val head = GlobalSettings.snake head
      val food = use instance 'food
      val pos = (food tell "foodPos").asInstanceOf[Tuple2[Int, Int]]
      pos._1 == head._1 && pos._2 == head._2
    }
    action = (l: List[Any]) => {
      val self = l(0).asInstanceOf[GamelEntity]
      val rand = new Random()

      val food = use instance 'food
      val oldPos = (food tell "foodPos").asInstanceOf[Tuple2[Int, Int]]

      var foodPos = (rand.nextInt(GlobalSettings.xMax), rand.nextInt(GlobalSettings.yMax))
      while (GlobalSettings.grid(foodPos._2)(foodPos._1) != GlobalSettings.EMPTY)
        foodPos = (rand.nextInt(GlobalSettings.xMax), rand.nextInt(GlobalSettings.yMax))

      GlobalSettings.grid(oldPos._2)(oldPos._1) = GlobalSettings.EMPTY
      GlobalSettings.grid(foodPos._2)(foodPos._1) = GlobalSettings.FOOD
      self.set("foodPos", foodPos)
      'snake does 'grow using (oldPos)
    }
  }

  define a new action {
    name = 'gameEnd
    condition = (unit) => {
      val head = GlobalSettings.snake head
      val grid = GlobalSettings.grid
      var ret = false
      // out of bounds
      if (head._1 < 0 || head._2 < 0 || head._1 >= GlobalSettings.yMax || head._2 >= GlobalSettings.xMax)
        ret = true
      // crash itself
      else if (!ret && grid(head._2)(head._1) == GlobalSettings.SNAKE)
        ret = true
      ret
    }
    action = (l: List[Any]) => {
      GlobalSettings.gameEnd = true
      gamel.gameMsg ! "pause"
    }
  }

  define a new action {
    name = 'move
    condition = (unit) => {
      var ret = false
      val diff = gamel.time - GlobalSettings.lastTime
      if (diff > (1.0 / GlobalSettings.speed)) {
        GlobalSettings.lastTime = gamel.time
        ret = true
      }
      ret
    }
    action = (l: List[Any]) => {
      val init = GlobalSettings.snake init
      val head = GlobalSettings.snake head
      val tail = GlobalSettings.snake last
      var npos: Tuple2[Int, Int] = null
      GlobalSettings.direction match {
        case "up"    => npos = (head._1, head._2 - 1)
        case "down"  => npos = (head._1, head._2 + 1)
        case "left"  => npos = (head._1 - 1, head._2)
        case "right" => npos = (head._1 + 1, head._2)
      }
      GlobalSettings.snake = npos :: init
      GlobalSettings.grid(tail._2)(tail._1) = GlobalSettings.EMPTY
      GlobalSettings.grid(head._2)(head._1) = GlobalSettings.SNAKE
    }
  }

  define a new action {
    name = 'turn
    condition = (unit) => (detect KeyPressed) != null
    action = (l: List[Any]) => {
      val currentDir = GlobalSettings.direction
      val event = (detect KeyPressed).asInstanceOf[GamelKeyEvent]
      event.key match {
        case Key.Up     => { if(currentDir != "down")  GlobalSettings.direction = "up" }
        case Key.Down   => { if(currentDir != "up")    GlobalSettings.direction = "down" }
        case Key.Left   => { if(currentDir != "right") GlobalSettings.direction = "left" }
        case Key.Right  => { if(currentDir != "left")  GlobalSettings.direction = "right" }
        case _            => { println("You have to press the arrow key") }
      }
    }
  }

  create a new scene {
    name = 'background
    renderer = SnakeBackgroundRenderer
    objects += ('food, 'snake)
    actions += ('gameEnd)
  }

  create a new instance {
    name = 'food
    renderer = FoodRenderer
    attributes += ( "foodPos"   -> (20, 30) )
    actions += ('newFood )
  }

  create a new instance {
    name = 'snake
    renderer = SnakeRenderer
    actions += ('move, 'turn, 'grow)
    attributes += (
      "body" -> List((20, 20), (20, 21), (20, 22), (20, 23)),
      "direction" -> "down"
    )
  }

  create a new game {
    name = "Snake"
    startScene = 'background
    resolution  = (600, 600)
  }

  start game

}
