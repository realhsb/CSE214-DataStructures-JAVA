package hw214_1;

//Rational number
// Rat(10, 15) is 10/15 and is equivalent to Rat(2, 3)
//
public class Rat implements Field, Modulo, Ordered {
    int n, d;
    
    public Rat(int numerator, int denumerator) {
        //TODO: make numerator and denominator prime to
        //each other using Euclidean.gcd
    	Ring x = new Int(numerator);
    	Ring y = new Int(denumerator);
    	
    	Ring r = Euclidean.GCD((Int)x, (Int)y);
    	x = ((Int)x).quo(r);
    	y = ((Int)y).quo(r);
    	this.n = ((Int)x).getInt();
    	this.d = ((Int)y).getInt();
    	}
    
    //Modulo
    public Ring mod(Ring a) {
        Rat r = (Rat)a;
        if(r.n == 0)
            throw new ArithmeticException("Division by zero");
        return new Rat((n*r.d) % (d*r.n), d*r.d);
    }
    
    public Ring quo(Ring a) {
    	Rat r = (Rat)a;
    	if(r.n == 0)
    		throw new ArithmeticException("Division by zero");
    	return new Rat(n*r.d, d*r.n);
    }
    
    //Ordered
    public boolean ge(Ordered a) {
        Rat r = (Rat)a;
        return n*r.d >= d*r.n;
    }
    
    //Ring
    public Ring add(Ring a) {
    	Rat r = (Rat)a;
    	return new Rat((n*r.d + d*r.n), d*r.d);
    }
    
    public Ring addIdentity() {
    	return new Rat (0, d); 
    }
    
    public Ring addInverse() {
    	return new Rat(-n, d);
    }
    
    public Ring mul(Ring a) {
    	Rat r = (Rat)a;
    	return new Rat((n*r.n), d*r.d);
    }

    //Field
    public Ring mulIdentity() {
    	return new Rat(1, 1);
    }
    
    public Ring mulInverse() {
    	return new Rat(d, n);
    }
}
