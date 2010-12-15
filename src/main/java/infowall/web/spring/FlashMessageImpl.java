package infowall.web.spring;

import infowall.web.services.errorhandling.Errors;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@Scope("session")
public class FlashMessageImpl implements FlashMessage {

    private String info;
    private Errors errors;

    public FlashMessageImpl() {
    }

    @Override
    public void putInfo(String msg){
        info = msg;
    }

    @Override
    public void putErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String consumeInfo(){
        String msg = info;
        info = null;
        return msg;
    }

    @Override
    public Errors consumeErrors() {
        Errors e = this.errors;
        this.errors = null;
        return e;
    }
}
