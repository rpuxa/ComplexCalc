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


import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class Problem1101 implements Runnable{

    private final static Random rnd = new Random();

    // SOLUTION!!!
    // HACK ME PLEASE IF YOU CAN!!!
    // PLEASE!!!
    // PLEASE!!!
    // PLEASE!!!

    private final static char MIN_REGISTER_NAME = 'A', MAX_REGISTER_NAME = 'Z';
    private final static int REGISTERS_COUNT = MAX_REGISTER_NAME - MIN_REGISTER_NAME + 1;
    private final static Variable[] REGISTERS = new Variable[REGISTERS_COUNT];

    private static void clearRegisters(){
        for (int index = 0; index < REGISTERS_COUNT; ++index) {
            REGISTERS[index] = new Variable(false);
        }
    }

    private void solve() {
        clearRegisters();
        final Expression expression = ExpressionBuilder.create(readLine()).buildExpression();

        final int maxAbsCoordinate = readInt();
        final int decidePointsCount = readInt();
        final int inversePointsCount = readInt();

        final Point[] decidePoints = readPointArray(decidePointsCount);

        final InversePoint[] inversePoints = new InversePoint[inversePointsCount];
        for (int index = 0; index < inversePointsCount; ++index) {
            Point position = readPoint();
            final char registerName = readString().charAt(0);
            inversePoints[index] = new InversePoint(position, registerName);
        }

        final List<Point> way = getWay(expression, maxAbsCoordinate, decidePoints, inversePoints);
        for (Point point : way) {
            out.println(point.x + " " + point.y);
        }
    }

    private final static int DECIDE = -1;

    private final static int[][] DIRECTIONS = {
            {1, 0}, {0, -1}, {-1, 0}, {0, 1}
    };

    private final static int TRUE_DELTA = 1, FALSE_DELTA = DIRECTIONS.length - TRUE_DELTA;

    private static List<Point> getWay(
            final Expression expression,
            final int maxAbsCoordinate,
            final Point[] decidePoints,
            final InversePoint[] inversePoints
    ) {
        final int fieldSize = maxAbsCoordinate + 1 + maxAbsCoordinate;
        final int[][] field = new int[fieldSize][fieldSize];

        for (Point decidePoint : decidePoints) {
            field[decidePoint.x + maxAbsCoordinate][decidePoint.y + maxAbsCoordinate] = DECIDE;
        }

        for (InversePoint inversePoint : inversePoints) {
            field[inversePoint.x + maxAbsCoordinate][inversePoint.y + maxAbsCoordinate] = inversePoint.registerName;
        }

        int curX = maxAbsCoordinate, curY = maxAbsCoordinate;
        int curDirectionIndex = 0;

        List<Point> way = new ArrayList<>();
        while (true) {
            if (!checkCell(curX, fieldSize, curY, fieldSize)) break;

            way.add(new Point(curX - maxAbsCoordinate, curY - maxAbsCoordinate));

            int cellState = field[curX][curY];
            if (DECIDE == cellState) {
                curDirectionIndex += (expression.calculate() ? TRUE_DELTA : FALSE_DELTA);
                curDirectionIndex %= DIRECTIONS.length;
            } else if (MIN_REGISTER_NAME <= cellState && cellState <= MAX_REGISTER_NAME) {
                REGISTERS[cellState - MIN_REGISTER_NAME].inverse();
            }

            int[] direction = DIRECTIONS[curDirectionIndex];

            curX += direction[0];
            curY += direction[1];
        }

        return way;
    }

    private static class InversePoint extends Point {

        char registerName;

        InversePoint(Point position, char registerName) {
            super(position);
            this.registerName = registerName;
        }
    }

    private static class ExpressionBuilder {

        private final static Variable FALSE_CONSTANT = new Variable(false), TRUE_CONSTANT = new Variable(true);

        private final static UnaryOperator<Boolean> NOT_OPERATOR = x -> !x;
        private final static BinaryOperator<Boolean> AND_OPERATOR = (x, y) -> (x && y);
        private final static BinaryOperator<Boolean> OR_OPERATOR = (x, y) -> (x || y);

        private final static char SPACE = ' ';
        private final static char OPEN_BRACKET = '(', CLOSE_BRACKET = ')';

        private final static char NOT_CHAR = '-', AND_CHAR = '&', OR_CHAR = '|';
        private final static char FALSE_CHAR = '0', TRUE_CHAR = '1';

        private final static String NOT_STRING = "NOT", AND_STRING = "AND", OR_STRING = "OR";
        private final static String FALSE_STRING = "FALSE", TRUE_STRING = "TRUE";

        private final static Map<String, Character> REPLACINGS;

        static {
            REPLACINGS = new HashMap<>();

            REPLACINGS.put(NOT_STRING, NOT_CHAR);
            REPLACINGS.put(AND_STRING, AND_CHAR);
            REPLACINGS.put(OR_STRING, OR_CHAR);

            REPLACINGS.put(FALSE_STRING, FALSE_CHAR);
            REPLACINGS.put(TRUE_STRING, TRUE_CHAR);
        }

        static int[] calculateCloseBracketIndexes(char[] expressionChars) {
            int[] closeBracketIndexes = new int[expressionChars.length];
            Arrays.fill(closeBracketIndexes, -1);

            Deque<Integer> stack = new ArrayDeque<>();
            for (int index = 0; index < expressionChars.length; ++index) {
                if (OPEN_BRACKET == expressionChars[index]) {
                    stack.push(index);
                } else if (CLOSE_BRACKET == expressionChars[index]) {
                    int openBracketIndex = stack.pop();
                    closeBracketIndexes[openBracketIndex] = index;
                }
            }

            return closeBracketIndexes;
        }

        static ExpressionBuilder create(String expressionString) {
            for (Map.Entry<String, Character> replacing : REPLACINGS.entrySet()) {
                expressionString = expressionString.replace(replacing.getKey(), "" + replacing.getValue());
            }

            expressionString = expressionString.replace("" + SPACE, "");

            char[] expressionChars = expressionString.toCharArray();

            int[] closeBracketIndexes = calculateCloseBracketIndexes(expressionChars);

            return new ExpressionBuilder(expressionChars, closeBracketIndexes);
        }

        private final char[] expressionChars;
        private final int[] closeBracketIndexes;

        private ExpressionBuilder(char[] expressionChars, int[] closeBracketIndexes) {
            this.expressionChars = expressionChars;
            this.closeBracketIndexes = closeBracketIndexes;
        }

        Expression buildExpression() {
            return buildExpression(0, expressionChars.length - 1);
        }

        private enum BuildMode {
            BRACKETS, OR, AND, NOT, VARIABLE
        }

        private Expression buildExpression(int left, int right) {
            return buildExpression(left, right, BuildMode.OR);
        }

        private Expression buildExpression(int left, int right, BuildMode mode) {
            while (closeBracketIndexes[left] == right) {
                ++left;
                --right;
                mode = BuildMode.BRACKETS;
            }

            switch (mode) {
                case BRACKETS: return buildExpression(left, right);
                case OR: return buildBinaryExpression(left, right, OR_CHAR, OR_OPERATOR, BuildMode.AND);
                case AND: return buildBinaryExpression(left, right, AND_CHAR, AND_OPERATOR, BuildMode.NOT);
                case NOT: return buildUnaryExpression(left, right, BuildMode.VARIABLE);
                case VARIABLE: return buildVariableExpression(left, right);
                default: return null;
            }
        }

        private Expression buildBinaryExpression(
                final int left, final int right,
                final char operatorChar,
                final BinaryOperator<Boolean> binaryOperator,
                final BuildMode nextMode
        ) {
            Expression binaryExpression = null;

            int start = left;
            for (int index = left; index <= right + 1; ++index) {
                char expressionChar = (right + 1 == index ? operatorChar : expressionChars[index]);
                if (operatorChar == expressionChar) {
                    int currentExpressionEnd = index - 1;
                    Expression currentExpression = buildExpression(start, currentExpressionEnd, nextMode);

                    if (null == binaryExpression) {
                        binaryExpression = currentExpression;
                    } else {
                        binaryExpression = new BinaryOperation(binaryExpression, currentExpression, binaryOperator);
                    }

                    start = index + 1;
                }

                if (OPEN_BRACKET == expressionChar) {
                    index = closeBracketIndexes[index];
                }
            }

            return binaryExpression;
        }

        private static UnaryOperator<Boolean> getUnaryOperator(char operatorChar) {
            switch (operatorChar) {
                case NOT_CHAR:
                    return NOT_OPERATOR;
                default:
                    return null;
            }
        }

        private Expression buildUnaryExpression(
                final int left, final int right,
                final BuildMode nextMode
        ) {
            Deque<UnaryOperator<Boolean>> unaryOperators = new ArrayDeque<>();
            while (unaryOperators.size() + left <= right) {
                final char expressionChar = expressionChars[left + unaryOperators.size()];
                final UnaryOperator<Boolean> unaryOperator = getUnaryOperator(expressionChar);
                if (null != unaryOperator) {
                    unaryOperators.push(unaryOperator);
                } else {
                    break;
                }
            }

            Expression expression = buildExpression(left + unaryOperators.size(), right, nextMode);
            while (unaryOperators.size() > 0) {
                UnaryOperator<Boolean> unaryOperator = unaryOperators.pop();
                expression = new UnaryOperation(expression, unaryOperator);
            }

            return expression;
        }

        private Expression buildVariableExpression(
                final int left, final int right
        ) {
            char expressionChar = expressionChars[left];

            switch (expressionChar) {
                case TRUE_CHAR: return TRUE_CONSTANT;
                case FALSE_CHAR: return FALSE_CONSTANT;
                default:
                    return REGISTERS[expressionChar - MIN_REGISTER_NAME];
            }
        }
    }



    private interface Expression {

        boolean calculate();
    }

    private static class Variable implements Expression {

        private boolean value;

        Variable(boolean value) {
            this.value = value;
        }

        @Override
        public boolean calculate() {
            return value;
        }

        void inverse() {
            this.value ^= true;
        }
    }

    private static class UnaryOperation implements Expression {

        private Expression innerExpression;
        private UnaryOperator<Boolean> operator;

        UnaryOperation(Expression innerExpression, UnaryOperator<Boolean> operator) {
            this.innerExpression = innerExpression;
            this.operator = operator;
        }

        @Override
        public boolean calculate() {
            return operator.apply(innerExpression.calculate());
        }
    }

    private static class BinaryOperation implements Expression {

        private Expression leftExpression, rightExpression;
        private BinaryOperator<Boolean> operator;

        BinaryOperation(Expression leftExpression, Expression rightExpression, BinaryOperator<Boolean> operator) {
            this.leftExpression = leftExpression;
            this.rightExpression = rightExpression;
            this.operator = operator;
        }

        @Override
        public boolean calculate() {
            return operator.apply(leftExpression.calculate(), rightExpression.calculate());
        }
    }

}


http://acm.timus.ru/problem.aspx?space=1&num=1101

