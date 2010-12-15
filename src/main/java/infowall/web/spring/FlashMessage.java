package infowall.web.spring;

import infowall.web.services.errorhandling.Errors;

/**
 *
 */
public interface FlashMessage {
    void putInfo(String msg);
    void putErrors(Errors errors);

    String consumeInfo();
    Errors consumeErrors();
}
