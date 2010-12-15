package infowall.web.spring;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@Scope("session")
public class FlashMessageImpl implements FlashMessage {

    private String info;

    public FlashMessageImpl() {
    }

    @Override
    public void putInfo(String msg){
        info = msg;
    }

    @Override
    public String consumeInfo(){
        String msg = info;
        info = null;
        return msg;
    }
}
