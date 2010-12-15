package infowall.web.spring;

/**
 *
 */
public interface FlashMessage {
    void putInfo(String msg);

    String consumeInfo();
}
