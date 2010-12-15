package infowall.web.services.errorhandling;

import com.google.common.collect.Lists;

import java.util.List;

/**
 *
 */
public class Errors implements ErrorNotifier{

    private List<String> messages;

    public Errors() {
        this.messages = Lists.newArrayList();
    }

    @Override
    public void addError(String msg) {
        messages.add(msg);
    }

    public boolean haveOccurred(){
        return !messages.isEmpty();
    }

    public List<String> getMessages(){
        return messages;
    }
}
