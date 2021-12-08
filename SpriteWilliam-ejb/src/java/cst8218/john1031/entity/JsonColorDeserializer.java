package cst8218.john1031.entity;

import java.awt.Color;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser.Event;

/**
 * JsonColorDeserializer class 
 * turns a string into a color object
 * @author William
 */
public class JsonColorDeserializer implements JsonbDeserializer<Color> {

    /**
     * goes bit by bit to parse json into a color object
     * @param parser parser 
     * @param ctx context 
     * @param rtType type
     * @return new color object based on the input 
     */
    @Override
    public Color deserialize(javax.json.stream.JsonParser parser, javax.json.bind.serializer.DeserializationContext ctx, java.lang.reflect.Type rtType) {
        String keyname = "";
        int value = 0;
        int red = 0;
        int green = 0;
        int blue = 0;
        while (parser.hasNext()) {
            Event event = parser.next();
            switch (event) {
                case KEY_NAME: {
                    keyname = parser.getString();
                    break;
                }
                case VALUE_NUMBER: {
                    value = parser.getInt();
                    if (keyname.equals("red")) {
                        red = value;
                    } else if (keyname.equals("green")) {
                        green = value;
                    } else if (keyname.equals("blue")) {
                        blue = value;
                    }
                    break;
                }
            }
        }
        return new Color(red, green, blue);
    }
}
