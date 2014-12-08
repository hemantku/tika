/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.parser.internal;

import java.util.Properties;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.detect.Detector;
import org.apache.tika.parser.Parser;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private ServiceRegistration detectorService;

    private ServiceRegistration parserService;

    public void start(BundleContext context) throws Exception {
    	TikaConfig config = new TikaConfig(Activator.class.getClassLoader());
        detectorService = context.registerService(
                Detector.class.getName(),
                config.getDetector(),
                new Properties());
        parserService = context.registerService(
                Parser.class.getName(),
                new AutoDetectParser(config),
                new Properties());
    }

    public void stop(BundleContext context) throws Exception {
        parserService.unregister();
        detectorService.unregister();
    }

}
