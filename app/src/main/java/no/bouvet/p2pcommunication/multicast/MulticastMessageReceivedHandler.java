package no.bouvet.p2pcommunication.multicast;

import android.os.Handler;
import android.os.Message;
import no.bouvet.p2pcommunication.listener.multicast.MulticastMessageReceivedListener;
import no.bouvet.p2pcommunication.util.NetworkUtil;


/*
    This java file handles the incoming messages from MulticastMessageReceiverService.java.
 */

public class MulticastMessageReceivedHandler extends Handler {

  public static final String RECEIVED_TEXT = "RECEIVED_TEXT";
  public static final String SENDER_IP_ADDRESS = "SENDER_IP_ADDRESS";
  private MulticastMessageReceivedListener multicastMessageReceivedListener;

  public MulticastMessageReceivedHandler(
      MulticastMessageReceivedListener multicastMessageReceivedListener) {
    this.multicastMessageReceivedListener = multicastMessageReceivedListener;
  }

  @Override
  public void handleMessage(Message messageFromMulticastMessageReceiverService) {
    MulticastMessage multicastMessage = createMulticastMessage(
        messageFromMulticastMessageReceiverService);
    multicastMessageReceivedListener.onMulticastMessageReceived(multicastMessage);
  }

  private MulticastMessage createMulticastMessage(
      Message messageFromMulticastMessageReceiverService) {
    String receivedText = getReceivedText(messageFromMulticastMessageReceiverService);
    String senderIpAddress = getSenderIpAddress(messageFromMulticastMessageReceiverService);
    MulticastMessage multicastMessage = new MulticastMessage(receivedText, senderIpAddress);
    if (senderIpAddress.equals(NetworkUtil.getMyWifiP2pIpAddress())) {
      multicastMessage.setSentByMe(true);
    }
    return multicastMessage;
  }

  private String getSenderIpAddress(Message messageFromReceiverService) {
    return messageFromReceiverService.getData().getString(SENDER_IP_ADDRESS);
  }

  private String getReceivedText(Message messageFromReceiverService) {
    return messageFromReceiverService.getData().getString(RECEIVED_TEXT);
  }
}
