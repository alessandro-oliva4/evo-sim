package evo_sim.view.swing.monadic

import java.awt.event.{ActionEvent, ActionListener}

import cats.effect.IO
import javax.swing.JButton

class JButtonIO(override val component: JButton) extends ComponentIO(component){
  //procedural event listener description (from API user point of view)
  def actionListenerAdded(l:ActionListener): IO[Unit] = IO {component.addActionListener(l)}
  //event listener that doesn't leverage action event parameter
  def actionListenerAddedFromUnit(l: => Unit): IO[Unit] = IO {component.addActionListener(_ => l)}

  def actionListenerRemoved(l:ActionListener): IO[Unit] = IO {component.removeActionListener(l)}
  def textSet(text: String): IO[Unit] = IO {component.setText(text)}
  def textGot(): IO[String] = IO {component.getText}
  def enabledSet(b: Boolean): IO[Unit] = IO { component.setEnabled(b) }

  //enabling event listener description by monad
  def actionListenerAdded(l:ActionEvent => IO[Unit]): IO[Unit] =
    IO {component.addActionListener( e => l(e).unsafeRunSync() )}
  //event listener that doesn't leverage action event parameter
  def actionListenerAdded(l: => IO[Unit]): IO[Unit] =
    IO {component.addActionListener( _ => l.unsafeRunSync() )}
}

//companion object with utilities
object JButtonIO {
  def apply(text:String): IO[JButtonIO] = IO { new JButtonIO(new JButton(text)) }
}
