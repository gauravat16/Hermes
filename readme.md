# Hermes
Hermes allows you to create a pipeline for sending FCM messages by using metrics from your application. It can be iOS, Android or Desktop app. Data can be queried using a combination of GraphQL and Custom metadata query.

### Queries

**Query**

```query {
    getByQuery(metadata: "metadata.isUDPluginEnabled,=,true;metadata.dateForNotif,=,'2020-08-16'") {
      deviceName
      cloudMessagingId
    }
  }
```

**Response** 

````{
    "data": {
      "getByQuery": [
        {
          "deviceName": "Android SDK built for x86_64",
          "cloudMessagingId": "eOAbH3rkWfUxgaGLLm:APA91bH1x82YnuNwJPW_Z_ZjA41Ovya0uGkGRUmmJsVeAIGdS1eHL1vzerUHoF8TlfIm8l6pe_uLrixZ0Az1uAjq7t-umiyCqIsik1kPN4CVPiSYBJsYhEFRw4K2KD89OEA8G"
        }
      ]
    }
  }
````

#### Metadata Query

**Structure**

```<key1>,<operation1>,<value1>;<key2>,<operation2>,<value2>```

```val1,=,23;val2,=,ABC```
