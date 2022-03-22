package com.my.project.drools_detector;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Drools_detector {
    public static void main(String[] args) {
        System.out.println("Helloworld");

        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();

        execute(kc);
    }
    public static void execute(KieContainer kc) {
        Logger log = LoggerFactory.getLogger(Drools_detector.class);

        log.info("New KeySession");
        KieSession ksession = kc.newKieSession("ksession1");

        log.info("set global");
        ksession.setGlobal("list", new ArrayList<Object>());

        log.info("add Event Listener");
        ksession.addEventListener(new DebugAgendaEventListener());
        ksession.addEventListener(new DebugRuleRuntimeEventListener());

        log.info("Set message");
        final Message message = new Message();
        message.setMessage("Hello World");
        message.setStatus(Message.HELLO);
        ksession.insert(message);

        log.info("fire all rules");
        ksession.fireAllRules();

        log.info("dispose");
        ksession.dispose();
    }

    public static class Message {
        public static final int HELLO = 0;
        public static final int GOODBYE = 1;

        private String message;
        private int status;

        public Message() { }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(final int status) {
            this.status = status;
        }

        public static Message doSomething(Message message) {
            return message;
        }

        public boolean isSomething(String msg, List<Object> list) {
            list.add(this);
            return this.message.equals(msg);
        }
    }
}
