import java.util.Scanner;

class Z {

    double re;
    double im;
    Z(double Re, double Im) {
        re=Re;
        im=Im;
    }
}

public class Run {

    private static final double pi=3.14159265358979323846;

    public static void main(String[] agrs) {

        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
    }

    private static Z sum(Z z1,Z z2){
        return new Z(z1.re+z2.re,z2.im+z1.im);
    }

    private static Z mult(Z z1,Z z2){
        return new Z(z1.re*z2.re-z1.im*z2.im,z1.im*z2.re+z1.re*z2.im);
    }

    private static Z div(Z z1,Z z2){
        return new Z((z1.re*z2.re+z1.im*z2.im)/(Math.pow(z2.re,2)+Math.pow(z2.im,2)),(z1.im*z2.re-z1.re*z2.im)/(Math.pow(z2.re,2)+Math.pow(z2.im,2)));
    }

    private static Z abs(Z z){
        return new Z(Math.sqrt(z.re*z.re+z.im*z.im),0);
    }

    private static double arg(Z z){
        if ((z.re>=0) && (z.im>=0))
            return Math.atan(z.im/z.re);
        if ((z.re<=0) && (z.im>=0))
            return pi/2-Math.atan(z.re/z.im);
        if ((z.re<=0) && (z.im<=0))
            return pi+Math.atan(z.im/z.re);
        if ((z.re>=0) && (z.im<=0))
            return 3*pi/2-Math.atan(z.im/z.re);
        return 0;
}
    private static Z cos(Z z){
        return  new Z(Math.cos(z.re)*Math.cosh(z.im),-Math.sin(z.re)*Math.sinh(z.im));
    }

    private static Z sin(Z z){
        return  new Z(Math.sin(z.re)*Math.cosh(z.im),Math.cos(z.re)*Math.sinh(z.im));
    }

    private static Z tan(Z z){
        return div(sin(z),cos(z));
    }

    private static Z cot(Z z){
        return div(cos(z),sin(z));
    }

    private static Z ln(Z z){
        return new Z(Math.log(abs(z).re),arg(z));
    }

    private static Z log(Z z1,Z z2){
        return div(ln(z1),ln(z2));
    }

    private static Z power(Z z1,Z z2){
        Z z3 = mult(z2,ln(z1));
        return new Z(Math.exp(z3.re)*Math.cos(z3.im),Math.exp(z3.re)*Math.sin(z3.im));
    }

    private static Z asin(Z z){
        return mult(new Z(0,-1),ln(sum(mult(new Z(0,1),z),power(sum(new Z(1,0),mult(power(z,new Z(2,0)),new Z(-1,0))),new Z(0.5,0)))));
    }

    private static Z acos(Z z){
        return sum(new Z(pi/2,0),mult(new Z(-1,0),asin(z)));
    }

    private static Z atan(Z z){
        return asin(div(z,power(sum(new Z(1,0),mult(z,z)),new Z(0.5,0))));
    }

    private static Z acot(Z z){
        return sum(new Z(pi/2,0),mult(new Z(-1,0),atan(z)));
    }

    private static Z ch(Z z) {
        return cos(mult(new Z(0, 1), z));
    }

    private static Z sh(Z z){
        return mult(new Z(0,-1),sin(mult(new Z(0,1),z)));
    }

    private static Z th(Z z){
        return mult(new Z(0,-1),tan(mult(new Z(0,1),z)));
    }

    private static Z cth(Z z){
        return div(new Z(1,0),th(z));
    }

    private static Z arsh(Z z){
        return ln(sum(z,power(sum(mult(z,z),new Z(1,0)),new Z(0.5,0))));
    }

    private static Z arch(Z z){
        return ln(sum(z,power(sum(mult(z,z),new Z(-1,0)),new Z(0.5,0))));
    }

    private static Z arth(Z z){
        return div(ln(div(sum(new Z(1,0),z),sum(new Z(1,0),mult(new Z(-1,0),z)))),new Z(2,0));
    }

    private static Z arcth(Z z){
        return div(ln(div(sum(new Z(1,0),z),sum(new Z(-1,0),z))),new Z(2,0));
    }

    private static Z root(Z z1,Z z2){
        return power(z1,div(new Z(1,0),z2));
    }

}