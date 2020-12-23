package maps_elements.data_types;

public class Vector2d {
    public int x;
    public int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + Integer.toString(x) + "," + Integer.toString(y) + ")";
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return other.precedes(this);
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(java.lang.Math.max(x, other.x), java.lang.Math.max(y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(java.lang.Math.min(x, other.x), java.lang.Math.min(y, other.y));
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }

    public boolean equals(Object other) {
        if(this == other){
            return true;
        }
        if(this.getClass() != other.getClass()){
            return false;
        }
        Vector2d that = (Vector2d) other;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    public Vector2d opposite() {
        return new Vector2d(-x, -y);
    }
}