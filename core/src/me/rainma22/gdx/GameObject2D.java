package me.rainma22.gdx;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameObject2D {
	int x,y,z;
	TextureRegion texture;
	int width,height;
	int xmax,ymax;
	int xcounter,ycounter;
	private Rectangle rect;
	int fps;
	float scale;
	float rotation;
	public GameObject2D(int x,int y,TextureRegion t,int width,int height,int fps,float scale) {
		this.x=x;this.y=y;texture=t; this.width=width;this.height=height;
		xmax=t.getRegionWidth()/width;ymax=t.getRegionHeight()/height;
		xcounter=0;ycounter=0;rect=new Rectangle(x, y, width*scale, height*scale);
		this.fps=fps;
		this.scale=scale;
	}
	public GameObject2D(int x,int y,TextureRegion t,int width,int height,int fps,float scale,int beginCounter) {
		this.x=x;this.y=y;texture=t; this.width=width;this.height=height;
		xmax=t.getRegionWidth()/width;ymax=t.getRegionHeight()/height;
		xcounter=0;ycounter=0;rect=new Rectangle(x, y, width*scale, height*scale);
		this.fps=fps;
		this.scale=scale;
		xcounter=beginCounter%xmax;
		ycounter=(beginCounter/xmax)%ymax;
	}
	public GameObject2D(int x,int y,TextureRegion t,int width,int height,int fps,float scale,int beginCounter,float rotation) {
		this.x=x;this.y=y;texture=t; this.width=width;this.height=height;
		xmax=t.getRegionWidth()/width;ymax=t.getRegionHeight()/height;
		xcounter=0;ycounter=0;rect=new Rectangle(x, y, width*scale, height*scale);
		this.fps=fps;
		this.scale=scale;
		xcounter=beginCounter%xmax;
		ycounter=(beginCounter/xmax)%ymax;
		this.rotation=rotation;
	}
	public TextureRegion getNextTexture(long i) {
		TextureRegion tr=new TextureRegion(texture, (width*xcounter), (height*ycounter), width, height);
		if((double)i %(60d/fps)==0)
			xcounter++;
		if (xcounter>=xmax) {
			ycounter++;
			xcounter=0;
			ycounter%=ymax;
		}
		return(tr);
	}
	public TextureRegion getPreviousTexture(long i) {
		TextureRegion tr=new TextureRegion(texture, (width*xcounter), (height*ycounter), width, height);
		if((double)i %(60d/fps)==0)
			xcounter--;
		if (xcounter<=0) {
			ycounter--;
			xcounter=xmax-1;
			if(ycounter<0) ycounter=ymax-1;
//System.out.println(xcounter+":"+ycounter);
		}
		return(tr);
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getZ() {
		return this.z;
	}
	public float getScale(){
		return scale;
	}
	public Rectangle getRect() {
		return rect;
	}
}
