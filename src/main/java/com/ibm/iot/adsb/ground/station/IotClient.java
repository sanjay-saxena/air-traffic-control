/**
 * Copyright 2016, IBM Corporation. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.iot.adsb.ground.station;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.ibm.iotf.client.device.DeviceClient;

public class IotClient {
    private static final String PROPERTIES_FILE_NAME = "/device.properties";
    private static final Properties DEVICE_PROPERTIES = loadProperties();

    private final Logger logger = LoggerFactory.getLogger(IotClient.class);

    private DeviceClient deviceClient;

    public IotClient( ) {
        if (logger.isDebugEnabled()) {
            logger.debug(DEVICE_PROPERTIES.toString());
        }

    }

    public void connect() throws IOException {
        try {
            deviceClient = new DeviceClient(DEVICE_PROPERTIES);
            deviceClient.connect();

            if (logger.isDebugEnabled()) {
                logger.debug("Connected to Watson Iot Platform: " + deviceClient.isConnected());
            }
        } catch (Exception e) {
            logger.error("Failed to connect to Watson IoT Platform", e.getMessage());
            throw new IOException("Failed to connect to Watson Iot Platform", e);
        }
    }

    public void disconnect() {
        deviceClient.disconnect();
        deviceClient = null;
    }

    public boolean isConnected() {
        return (deviceClient != null) ? deviceClient.isConnected() : false;
    }

    public void publishEvent(Flight flight) throws IOException {
        if ((flight == null) || !flight.isReadyForTracking()) {
            return;
        }

        if ((deviceClient == null) || !deviceClient.isConnected()) {
            logger.info("Reconnecting to IBM Bluemix...");
            connect();
            logger.info("Reconnected successfully to IBM Bluemix...");
        }

        if (logger.isDebugEnabled()) {
            logger.debug(flight.toString());
        }

        JsonObject jsonObject = flight.getJsonObject();
        logger.info("Publishing MQTT message");
        deviceClient.publishEvent("flight", jsonObject);
        logger.info("Published MQTT message");
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try {
            props.load(IotClient.class.getResourceAsStream(PROPERTIES_FILE_NAME));
        } catch (IOException ex) {
            System.out.println("Not able to read the properties file!");
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        return props;
    }
}
