package utils

import akka.actor.ActorSystem
import akka.dispatch.MessageDispatcher
import javax.inject.{Inject, Singleton}

@Singleton
class Contexts @Inject()(akkaSystem: ActorSystem, configuration: play.api.Configuration) {
  implicit val cpuLookup: MessageDispatcher = akkaSystem.dispatchers.lookup("contexts.cpu-operations")
}