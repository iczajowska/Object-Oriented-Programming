package agh.cs.lab8;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "("+String.valueOf(this.x)+","+String.valueOf(this.y)+")";
    }
    public boolean precedes(Vector2d other){
        if(this.x<=other.x&&this.y<=other.y) return true;
        else return false;
    }
    public boolean follow(Vector2d other){
        if(this.x>=other.x&&this.y>=other.y) return true;
        else return false;
    }
    public Vector2d upperRight(Vector2d other){
        int vx;
        int vy;
        if(other.x>this.x) vx=other.x; else vx=this.x;
        if(other.y>this.y) vy=other.y; else vy=this.y;
        return new Vector2d(vx,vy);
    }
    public Vector2d lowerLeft(Vector2d other){
        int vx;
        int vy;
        if(other.x<this.x) vx=other.x; else vx=this.x;
        if(other.y<this.y) vy=other.y; else vy=this.y;
        return new Vector2d(vx, vy);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x+other.x,this.y+other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x-other.x,this.y-other.y);
    }
    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return (this.x==that.x&&this.y==that.y);
    }
    public Vector2d opposite(){
        return new Vector2d((-1)*this.x,(-1)*this.y);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }


}

