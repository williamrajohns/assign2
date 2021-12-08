package cst8218.john1031.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import javax.json.bind.annotation.JsonbTypeDeserializer;
import javax.json.bind.annotation.JsonbTypeSerializer;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Sprite class defines sprites and their values and setters/getters also
 * contains the updates method
 *
 * @author William
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Sprite implements Serializable {

    //variables
    private static final long serialVersionUID = 1L;
    public final static Random random = new Random();

    final static int SIZE = 10;
    final static int MAX_SPEED = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column
    private Integer panelWidth; //int for this and below
    @Column
    private Integer panelHeight;

    @Column
    @Min(value = 0, message = "X should be equal or more than 0")
    private Integer x;
    @Column
    @Min(value = 0, message = "Y should be equal or more than 0")
    private Integer y;
    @Column
    private Integer dx;
    @Column
    private Integer dy;

    //colour
    @XmlElement
    @XmlJavaTypeAdapter(ColorAdapter.class)
    @JsonbTypeDeserializer(JsonColorDeserializer.class)
    @JsonbTypeSerializer(JsonColorSerializer.class)
    @Column
    private Color color = Color.BLUE;

    public Sprite() {
    }

    public Sprite(Integer height, Integer width) {
        this.panelWidth = width;
        this.panelHeight = height;
        x = random.nextInt(width);
        y = random.nextInt(height);
        dx = random.nextInt(2 * MAX_SPEED) - MAX_SPEED;
        dy = random.nextInt(2 * MAX_SPEED) - MAX_SPEED;
    }

    public Sprite(int height, int width, Color color) {
        this(height, width);
        this.color = color;
    }

    public Integer getPanelWidth() {
        return panelWidth;
    }

    public void setPanelWidth(Integer panelWidth) {
        this.panelWidth = panelWidth;
    }

    public Integer getPanelHeight() {
        return panelHeight;
    }

    public void setPanelHeight(Integer panelHeight) {
        this.panelHeight = panelHeight;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        if (x < 0) {
            x = 0; //set the new x so that it is equal to 0
        }
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        if (y < 0) {
            y = 0; //set the new y so that it is equal to 0
        }
        this.y = y;
    }

    public Integer getDx() {
        return dx;
    }

    public void setDx(Integer dx) {
        this.dx = dx;
    }

    public Integer getDy() {
        return dy;
    }

    public void setDy(Integer dy) {
        this.dy = dy;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, SIZE, SIZE);
    }

    public void move() {

        // check for bounce and make the ball bounce if necessary
        //
        if (x < 0 && dx < 0) {
            //bounce off the left wall 
            x = 0;
            dx = -dx;
        }
        if (y < 0 && dy < 0) {
            //bounce off the top wall
            y = 0;
            dy = -dy;
        }
        if (x > panelWidth - SIZE && dx > 0) {
            //bounce off the right wall
            x = panelWidth - SIZE;
            dx = -dx;
        }
        if (y > panelHeight - SIZE && dy > 0) {
            //bounce off the bottom wall
            y = panelHeight - SIZE;
            dy = -dy;
        }

        //make the ball move
        x += dx;
        y += dy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprite)) {
            return false;
        }
        Sprite other = (Sprite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Sprite[ id=" + id + " ]";
    }

    /**
     * updates a sprite based another sprite (entity) and if entity doesn't
     * exist, keeps the old values
     *
     * @param entity Sprite with the new values 
     */
    public void updates(Sprite entity) {
        //check for same ID elsewhere
        if (entity.getX() != null && !Objects.equals(entity.getX(), x)) {
            x = entity.getX();
            if (x < 0) { //extra validation 
                x = 0;
            }
        }
        if (entity.getY() != null && !Objects.equals(entity.getY(), y)) {
            y = entity.getY();
            if (y < 0) { //extra validation 
                y = 0;
            }
        }
        if (entity.getDx() != null && !Objects.equals(entity.getDx(), dx)) {
            dx = entity.getDx();
        }
        if (entity.getDy() != null && !Objects.equals(entity.getDy(), dy)) {
            dy = entity.getDy();
        }
        if (entity.getPanelWidth() != null && !Objects.equals(entity.getPanelWidth(), panelWidth)) {
            panelWidth = entity.getPanelWidth();
        }
        if (entity.getPanelHeight() != null && !Objects.equals(entity.getPanelHeight(), panelHeight)) {
            panelHeight = entity.getPanelHeight();
        }
        if (entity.getColor() != color) { //come back to this later
            setColor(entity.getColor());
        }
    }
}
