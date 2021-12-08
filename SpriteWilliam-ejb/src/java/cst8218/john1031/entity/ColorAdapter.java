/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.john1031.entity;

import java.awt.Color;
import javax.faces.convert.FacesConverter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * color converter class for XML 
 * @author William
 */
@FacesConverter(forClass=Sprite.class)
class ColorAdapter extends XmlAdapter<ColorAdapter.ColorValueType, Color> {

    /**
     * returns a color value based on the given colorvaluetype
     * @param v given color value type
     * @return new color 
     * @throws Exception 
     */
    @Override
    public Color unmarshal(ColorValueType v) throws Exception {
        return new Color(v.red, v.green, v.blue);
    }

    /**
     * returns a color value type based on a color
     * @param v Color to convert
     * @return colorvaluetype based on param v
     * @throws Exception 
     */
    @Override
    public ColorValueType marshal(Color v) throws Exception {
        return new ColorValueType(v.getRed(), v.getGreen(), v.getBlue());
    }

    /**
     * class within class to get the color value for XML type
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ColorValueType {

        private int red;
        private int green;
        private int blue;
        
        //constructor
        public ColorValueType() {
        }

        //constructor based on color values
        public ColorValueType(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }
}
