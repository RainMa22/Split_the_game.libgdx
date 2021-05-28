package me.rainma22.gdx;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameObject2D {
	float x,y,z;
	TextureRegion texture;
	float width,height;
	int xmax,ymax;
	int xcounter,ycounter;
	private Rectangle rect;
	int fps;
	float scale;
	float rotation;
	public GameObject2D(float x,float y,TextureRegion t,float width,float height,int fps,float scale) {
		this.x=x;this.y=y;texture=t; this.width=width;this.height=height;
		xmax=(int)(t.getRegionWidth()/width);ymax=(int)(t.getRegionHeight()/height);
		xcounter=0;ycounter=0;rect=new Rectangle(x, y, width*scale, height*scale);
		this.fps=fps;
		this.scale=scale;
	}
	public GameObject2D(float x,float y,TextureRegion t,float width,float height,int fps,float scale,int beginCounter) {
		this.x=x;this.y=y;texture=t; this.width=width;this.height=height;
		xmax=(int)(t.getRegionWidth()/width);ymax=(int)(t.getRegionHeight()/height);
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
		TextureRegion tr=new TextureRegion(texture, (int)(width*xcounter), (int)(height*ycounter), (int)width, (int)height);
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
		TextureRegion tr=new TextureRegion(texture, (int)(width*xcounter), (int)(height*ycounter), (int)width, (int)height);
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
	public float getX() {
		return this.x;
	}
	public float getY() {
		return this.y;
	}
	public float getZ() {
		return this.z;
	}
	public float getScale(){
		return scale;
	}
	public Rectangle getRect() {
		return rect;
	}
}
