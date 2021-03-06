import javax.swing.JLabel;
import java.awt.Rectangle;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javafx.scene.shape.Circle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class GameObject{
    // public fields //
    public Graphics g;
    // private fields //
    private JLabel object;
    private ImageIcon img;
    private double xVel;
    private double yVel;
    private int xPos;
    private int yPos;
    private int size;
    // protected constants //
    protected static final int screenwidth = 1000;
    protected static final int screenheight = 600;
    protected static final double dt = 0.7;
    protected static final double gravity = 0.5;
    protected static final int playerStartSpeed = 7;
    protected static final int playerMaxSpeed = 20;
    protected static final int jumpspeed = 10;
    protected static final int playerground = 420;
    protected static final int ground = 450;
    protected static final int minVel = 3;
    protected static final double bouncefriction = 0.7;
    protected static final double slidefriction = 2;
    // protected fields //
    protected static Rectangle goalCrossBar1 = new Rectangle(0, 345, 105, 3);
    protected static Rectangle goalCrossBar2 = new Rectangle(898,345, 102, 3);
    protected static Rectangle goal1 = new Rectangle(0, 351, 75, 130);
    protected static Rectangle goal2 = new Rectangle(915, 351, 75, 125);
    protected static HashMap<String, GameObject> gameObjects = new HashMap<String, GameObject>(3);
    protected Circle collisionArea; // this circle will be used for collision detection
    protected double sumOfRadii;
    protected String id;
    protected boolean jump = false;
    protected boolean leftSlide = false;
    protected boolean rightSlide = false;
    protected boolean kick = false;
    protected int rotations = 0;

    public GameObject(ImageIcon img, int x, int y, int size, String key){
        this.img = img;
        this.object = new JLabel(img);
        this.size = size;
        this.xPos = x;
        this.yPos = y;
        this.id = key;
        this.getLabel().setBounds(xPos, yPos, size, size);
    }

    public boolean isCircleCollision(Circle A, Circle B){ // this method determines whether or not two objects are colliding
        double distance = Math.sqrt(Math.pow(A.getCenterX() - B.getCenterX(), 2)  // this calculates the distance between the centers of the two objects
                + Math.pow(A.getCenterY() - B.getCenterY(),2));
        sumOfRadii = A.getRadius() + B.getRadius();
        return distance <= sumOfRadii;
    }


    public boolean rectangleCircleCollision(Circle circle, Rectangle rect) {
        Rectangle inscribed = new Rectangle((int) (circle.getCenterX() - circle.getRadius()),
                (int) (circle.getCenterY() - circle.getRadius()), (int) (2 * circle.getRadius()),
                (int) (2 * circle.getRadius()));
        return inscribed.intersects(rect);
    }

    public void rotate(double theta){
    	rotations++;
        BufferedImage buffer = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D)buffer.getGraphics();
        g2d.rotate(Math.PI/180*theta*rotations, size/2, size/2);
        g2d.drawImage(img.getImage(), 0, 0, null);
        object.setIcon(new ImageIcon(buffer));
    }

    public void resetPosition() {
        gameObjects.get("ball").setXPos(500);
        gameObjects.get("ball").setYPos(50);
        gameObjects.get("ball").setXVel(0);
        gameObjects.get("ball").setYVel(0);
        gameObjects.get("player1").setXPos(200);
        gameObjects.get("player1").setYPos(playerground);
        gameObjects.get("player2").setXPos(800);
        gameObjects.get("player2").setYPos(playerground);
    }
    
    //abstract methods
    public abstract void updatePos();

    public abstract void events();

    public abstract void init();
    
    // getters
    public ImageIcon getImg(){
        return this.img;
    }

    public int getSize(){
        return this.size;
    }
    
    public JLabel getLabel() {
        return this.object;
    }

    public int getXPos(){
        return this.xPos;
    }

    public int getYPos() {
        return this.yPos;
    }

    public double getXVel(){
        return this.xVel;
    }

    public double getYVel() {
        return this.yVel;
    }

    // Setters 

    public void setJump(boolean x){
        this.jump = x;
    }
    
    public void setLeftSlide(boolean x) {
        this.leftSlide = x;
    }

    public void setRightSlide(boolean x) {
        this.rightSlide = x;
    }

    public void setKick(boolean x){
        this.kick = x;
    }

    public void setSize(int size){
        this.size = size;
    }

    public void setXPos(int x) {
        this.xPos = x;
        object.setBounds(this.xPos,this.yPos,size,size);
    }

    public void setYPos(int y) {
        this.yPos = y;
        object.setBounds(this.xPos, this.yPos, size, size);

    }

    public void changeXPos(int dx) {
        this.xPos += dx;
        object.setBounds(this.xPos, this.yPos, size, size);

    }

    public void changeYPos(int dy) {
        this.yPos += dy;
        object.setBounds(this.xPos, this.yPos, size, size);

    }

    public void setXVel(double xVel) {
        this.xVel = xVel;
    }

    public void setYVel(double yVel) {
        this.yVel = yVel;
    }

    public void changeXVel(double dxVel) {
        this.xVel += dxVel;
    }

    public void changeYVel(double dyVel) {
        this.yVel += dyVel;

    }
    
}