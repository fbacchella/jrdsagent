package jrds.agent.windows.pdh;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.arkanosis.jpdh.JPDHException;

public class PdhAgentUniqueInstance extends PdhAgent {
        private static final Logger logger = Logger.getLogger("jrds.agent.windows.pdh.PdhAgentUniqueInstance");

        // Coming from configuration
        protected String object;
        protected String instance;

        public Boolean configure(String name, String instance, ArrayList<String> counters) {
            this.instance = instance;

            if (!super.configure(name)) {
                return false;
            }
            try {
                addCounterList(this.object, this.instance, counters);
            } catch (IllegalArgumentException | JPDHException e) {
                logger.error("Could not initialize all counters for probe " + this.name, e);
                try {
                    this.query.close();
                } catch (JPDHException e1) {
                    logger.error("Could not close query of " + this.name + " probe after a bad add counter", e);
                }
                this.query = null;
                return false;
            }
            return true;
        }

        @Override
        public void setProperty(String specific, String value) {
            if (specific.equals("object")) {
                object = value;
            } else {
                super.setProperty(specific, value);
            }
        }

    }
