
package core;

/**
 * The Class Pair.
 *
 * @param <L> the generic type
 * @param <R> the generic type
 */
public class Pair<L,R>{
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** left attribute l. */
	private L l;
    
    /** right attribute r. */
    private R r;
    
    /**
     * Instantiates a new pair.
     *
     * @param L l : the left attribute of the new pair
     * @param R r : the right attribute of the new pair
     */
    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }
    
    /**
     * Gets l.
     *
     * @return Ll: the l attribute of the current pair
     */
    public L getL(){ return l; }
    
    /**
     * Gets r.
     *
     * @return R r : the r arribute of the current pair
     */
    public R getR(){ return r; }
    
    /**
     * Sets l.
     *
     * @param L l : the new l attribute
     */
    public void setL(L l){ this.l = l; }
    
    /**
     * Sets r.
     *
     * @param R r : the new r attribute
     */
    public void setR(R r){ this.r = r; }
}