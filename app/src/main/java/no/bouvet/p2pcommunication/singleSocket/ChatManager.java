package no.bouvet.p2pcommunication.singleSocket;

import android.os.Handler;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import no.bouvet.p2pcommunication.P2PCommunicationActivity;

/**
 * Handles reading and writing of messages with socket buffers. Uses a Handler
 * to post messages to UI thread for UI updates.
 */
public class ChatManager implements Runnable {

  private static final String TAG = "ChatHandler";
  private Socket socket = null;
  private Handler handler;
  private InputStream iStream;
  private OutputStream oStream;
  public ChatManager(Socket socket, Handler handler) {
    this.socket = socket;
    this.handler = handler;
  }

  @Override
  public void run() {
    try {

      iStream = socket.getInputStream();
      oStream = socket.getOutputStream();
      byte[] buffer = new byte[1024];
      int bytes;
      handler.obtainMessage(P2PCommunicationActivity.MY_HANDLE, this)
          .sendToTarget();

      while (true) {
        try {
          // Read from the InputStream
          bytes = iStream.read(buffer);
          if (bytes == -1) {
            break;
          }

          // Send the obtained bytes to the UI Activity
          Log.d(TAG, "Rec:" + String.valueOf(buffer));
          handler.obtainMessage(P2PCommunicationActivity.MESSAGE_READ,
              bytes, -1, buffer).sendToTarget();
        } catch (IOException e) {
          Log.e(TAG, "disconnected", e);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void write(byte[] buffer) {
    try {
      oStream.write(buffer);
    } catch (IOException e) {
      Log.e(TAG, "Exception during write", e);
    }
  }

}