/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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


