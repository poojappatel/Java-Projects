package evaluator.arith;

import language.Operand;
import language.Operator;
import parser.IllegalPostfixExpressionException;
import parser.PostfixParser.Type;
import parser.Token;
import parser.arith.ArithPostfixParser;
import stack.LinkedStack;
import stack.StackInterface;


import evaluator.PostfixEvaluator;

/** An {@link ArithPostfixEvaluator} is a postfix evaluator over simple arithmetic expressions. */
public class ArithPostfixEvaluator implements PostfixEvaluator<Integer> {

  private final StackInterface<Operand<Integer>> stack;

  /** Constructs an {@link ArithPostfixEvaluator} */
  public ArithPostfixEvaluator() {
    // TODO Initialize to your LinkedStack
    stack = new LinkedStack<Operand<Integer>>();
  }

  /** {@inheritDoc} */
  @Override
  public Integer evaluate(String expr) throws IllegalPostfixExpressionException {
    ArithPostfixParser parser = new ArithPostfixParser(expr);
    boolean operatorExist = false; 
    for (Token<Integer> token : parser) {
      Type type = token.getType();
      switch (type) {
        case OPERAND:
          // TODO What do we do when we see an operand?
          stack.push(token.getOperand());
          break;
        case OPERATOR:
          operatorExist = true; 
          // TODO What do we do when we see an operator?
          Operator<Integer> getIntegers = token.getOperator();
          Operand<Integer> result;
            if (getIntegers.getNumberOfArguments() == 1) {
              getIntegers.setOperand(0, stack.pop());
            } else {
              getIntegers.setOperand(1, stack.pop());
              getIntegers.setOperand(0, stack.pop());
            }
          result = getIntegers.performOperation();
          stack.push(result);
          break;
        default:
          throw new IllegalStateException("Parser returned an invalid Type: " + type);
      }
    }
    if (operatorExist == false && stack.size() > 1)
        throw new IllegalPostfixExpressionException();
    return stack.pop().getValue();
  }
}
