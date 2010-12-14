import infowall.infrastructure.json.SimpleJSONBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

final Logger logger = LoggerFactory.getLogger(getClass())

def builder = new SimpleJSONBuilder()

def rndVal = new Random().nextInt(30);
logger.info("Decided, we have #${rndVal} bugs now.");

builder.result(value:rndVal);

print builder.json