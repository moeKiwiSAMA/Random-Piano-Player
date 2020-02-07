
import javax.sound.midi.MidiSystem
import javax.sound.midi.MidiChannel

import scala.collection.generic.{GenericSeqCompanion, SeqFactory}
import scala.collection.mutable.ListBuffer
import scala.util.Random


object Main {

  val synth = MidiSystem.getSynthesizer
  synth.open()
  val channels: Array[MidiChannel] = synth.getChannels

  def main(args: Array[String]): Unit = {
    val c1 = new Thread(new Runnable {
      def run() {
        val note = ListBuffer[Iterable[Int]]()
        note.append(ListBuffer(36,43,48,128))
        note.append(ListBuffer(31,38,43,128))
        note.append(ListBuffer(33,40,45,128))
        note.append(ListBuffer(29,36,41,128))
        while (true) {
          note.flatMap(x => x.map(simplePress(_,500)))
        }
      }
    })
    c1.start()
    val c2 = new Thread(new Runnable {
      def run() {
        val note = ListBuffer[Iterable[Int]]()
        note.append(ListBuffer(60,64,65,67,128))
        while (true) {
          simplePress(note.flatten.toList(new Random().nextInt(note.flatten.length)),500,100)
        }
      }
    })
    c2.start()
//    val c3 = new Thread(new Runnable {
//      def run() {
//        val note = ListBuffer[Iterable[Int]]()
//        note.append(ListBuffer(72,72,74,76,76,76,79,128))
//        while (true) {
////          if (new Random().nextInt(3) == 1) {
//            simplePress(note.flatten.toList(new Random().nextInt(note.flatten.length)),250,70)
////          }
//        }
//      }
//    })
//    c3.start()
    Thread.sleep(10000000)
  }

  def simplePress(note: Int, latency:Int = 500,velocity:Int = 127): Unit ={
    if (note>127) {
      Thread.sleep(latency)
      return
    }
    channels(0).noteOn(note,velocity)
    Thread.sleep(latency)
    channels(0).noteOff(note,64)
  }

}


