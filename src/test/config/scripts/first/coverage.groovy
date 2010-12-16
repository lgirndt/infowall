import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.ContentType
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import infowall.infrastructure.json.SimpleJSONBuilder

final Logger logger = LoggerFactory.getLogger(getClass())

def http = new HTTPBuilder( 'http://nemo.sonarsource.org' )
def resultBuilder = new SimpleJSONBuilder()

// perform a GET request, expecting JSON response data
http.request( Method.GET, ContentType.JSON ) {
  uri.path = '/api/resources'
  uri.query = [
          resource : 'org.codehaus.sonar:sonar',
          metrics  : 'coverage',
          scope    : 'PRJ',
          format   : 'json'
  ]

  logger.info("issuing request");

  // response handler for a success response code:
  response.success = { resp, json ->
    logger.info(resp.statusLine as String)

    logger.info(json as String)
    Number val = json.msr.val[0][0]
    logger.info("coverage:" + val)
    resultBuilder.result(value:val)

    println resultBuilder.json

    // parse the JSON response object:
    /*
    json.responseData.results.each {
      println "  ${it.titleNoFormatting} : ${it.visibleUrl}"
    }
    */
  }

  // handler for any failure status code:
  response.failure = { resp ->
    println "Unexpected error: ${resp.statusLine.statusCode} : ${resp.statusLine.reasonPhrase}"
  }
}


