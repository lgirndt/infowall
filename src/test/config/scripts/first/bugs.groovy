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

import infowall.infrastructure.json.SimpleJSONBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

final Logger logger = LoggerFactory.getLogger(getClass())

def builder = new SimpleJSONBuilder()

def rndVal = new Random().nextInt(30);
logger.info("Decided, we have #${rndVal} bugs now.");

builder.result(value:rndVal);

print builder.json