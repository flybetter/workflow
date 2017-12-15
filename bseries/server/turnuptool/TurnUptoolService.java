package com.calix.bseries.server.turnuptool;

import com.calix.bseries.server.turnuptool.socket.VNETcpSocketService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;

public final class TurnUptoolService
{
  private static final Logger log = Logger.getLogger(TurnUptoolService.class);
  private static final int DEFAULT_SOCKET_PORT = 9000;
  private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
  private static VNETcpSocketService service;
  private static int socketPort = DEFAULT_SOCKET_PORT;

  public static void startService()
  {
    startSocketService(0, socketPort);
  }

  public static void restartSocketService() {
    log.info("[TurnUptool][restart socket service] protocol:tcp ,port:" + socketPort);

    stopSocketService();
    startSocketService(0, socketPort);
  }

  private static void stopSocketService() {
    log.info("[TurnUptool] stop socket service start");
    if (service != null) {
      service.stopService();
    }
    service = null;
    log.info("[TurnUptool] stop socket service successful");
  }

  private static void startSocketService(int protocol, int port) {
    log.info("[TurnUpTool] start socket service protocol:" + protocol + " port:" + port);

    socketPort = port;
    boolean ssl = protocol != 0;
    service = new VNETcpSocketService(ssl, socketPort);
    cachedThreadPool.execute(service);
  }

  public void stopService()
  {
    stopSocketService();
  }
}