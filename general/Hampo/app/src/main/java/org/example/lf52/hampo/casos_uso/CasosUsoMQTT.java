package org.example.lf52.hampo.casos_uso;

import android.util.Log;

/*
public class CasosUsoMQTT implements MqttCallback {

    public static MqttClient client = null;

    public CasosUsoMQTT() {
    }

    //Crear conexion con el broker MQTT
    public void crearConexionMQTT(String clientId) {
        try {
            Log.i(MQTT.TAG, "Conectando al broker " + MQTT.broker);
            client = new MqttClient(MQTT.broker, clientId,
                    new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(60);
            connOpts.setWill(topicRoot + "WillTopic", "App desconectada".getBytes(), MQTT.qos, false);
            client.connect(connOpts);
        } catch (MqttException e) {
            Log.e(MQTT.TAG, "Error al conectar.", e);
        }
    }

    //Publicar mensaje en un topic
    public void enviarMensajeMQTT(String data, String subTopic) {
        try {
            Log.i(MQTT.TAG, "Publicando mensaje: " + data);
            MqttMessage message = new MqttMessage(data.getBytes());
            message.setQos(MQTT.qos);
            message.setRetained(false);
            client.publish(topicRoot + subTopic, message);
        } catch (
                MqttException e) {
            Log.e(MQTT.TAG, "Error al publicar.", e);
        }
    }

    //Suscribirse a un topic
    public void escucharDeTopicMQTT(String subTopic){
        try {
            Log.i(MQTT.TAG, "Suscrito a " + topicRoot + subTopic);
            client.subscribe(topicRoot + subTopic, MQTT.qos);
            client.setCallback(this);
        } catch (MqttException e) {
            Log.e(MQTT.TAG, "Error al suscribir.", e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.d(MQTT.TAG, "Conexión perdida");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws
            Exception {
        String payload = new String(message.getPayload());
        Log.d(MQTT.TAG, "Recibiendo: " + topic + "->" + payload);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.d(MQTT.TAG, "Entrega completa");
    }

}*/