package com.uqam.latece.harissa.models;

import soot.Local;
import soot.Value;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.JimpleBody;
import soot.jimple.internal.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class HarissaBody {

    private HarissaMethod declaringMethod;
    private JimpleBody body;
    private List<InvokeExpression> invokeExpressions;
    private List<AssignExpression> assignExpressions;


    public HarissaBody(JimpleBody jimpleBody)
    {
        this.body = jimpleBody;
        this.buildExpressions();
        //this.invokeExpressions = this.getInvokeExpressions();
    }

    public HarissaBody(JimpleBody jimpleBody, HarissaMethod declaringMethod)
    {
        this.body = jimpleBody;
        this.declaringMethod = declaringMethod;
        buildExpressions();
        // this.invokeExpressions = this.getInvokeExpressions();
    }


    public String getPlainBody()
    {
        return body.toString();
    }

    public HarissaMethod getDeclaringMethod()
    {
        return this.declaringMethod;
    }

    public void buildExpressions()
    {
        List<InvokeExpression> invokeExprs = new ArrayList<>();
        List<AssignExpression> assignExpressions = new ArrayList<>();

        body.getUnits().stream()
                       .filter(unit -> (unit instanceof JInvokeStmt || unit instanceof JAssignStmt))
                       .forEach(unit -> {
                           if(unit instanceof JInvokeStmt)
                           {
                             invokeExprs.add((new InvokeExpression(((JInvokeStmt) unit).getInvokeExpr())));
                           }
                           if(unit instanceof JAssignStmt)
                           {
                               assignExpressions.add(new AssignExpression(((JAssignStmt) unit)));
                               try {
                                  // if (((JAssignStmt) unit).getInvokeExpr() != null)
                                       invokeExprs.add((new InvokeExpression(((JAssignStmt) unit).getInvokeExpr())));
                               }
                               catch (RuntimeException e)
                               {
                                   //CATCH IT
                               }
                           }
                       });

        this.invokeExpressions = invokeExprs;
        this.assignExpressions = assignExpressions;
    }

    public List<InvokeExpression> getInvokeExpressions()
    {
        return this.invokeExpressions;
    }

    public List<AssignExpression> getAssignExpressions()
    {
        return this.assignExpressions;
    }

    public List<Local> getLocals()
    {

        return new ArrayList<>(this.body.getLocals());
    }

    public List<Local> getParameterLocals()
    {

        return new ArrayList<>(this.body.getParameterLocals());
    }

    public List<InvokeExpression> getInvokeExpressionOfMethodWithSignature(String methodSignature)
    {
        return getInvokeExpressions().stream()
                                     .filter(invokeExpr -> invokeExpr.getMethodSignature()
                                     .equals(methodSignature))
                                     .collect(Collectors.toList());
    }


    public class InvokeExpression
    {
        private InvokeExpr invokeExpr;
        private String methodSignature;
        private String fullExpression;
        private String  invoker;
        private HashMap<String, String> args;

        public InvokeExpression(InvokeExpr invokeExpr)
        {
            this.args = new HashMap<>();
            this.invokeExpr = invokeExpr;
            this.build();

        }

        public InvokeExpr getInvokeExpr() {
            return invokeExpr;
        }

        public HarissaMethod getMethod()
        {
            HarissaMethod method = new HarissaMethod(this.getInvokeExpr().getMethod());

            return method;
        }

        public String getMethodSignature()
        {
            return this.methodSignature;
        }

        public String getFullExpression() {
            return this.fullExpression;
        }

        public HashMap<String, String> getArgs() {
            return this.args;
        }

        public String getInvoker() {
            return this.invoker;
        }

        public HarissaMethod getInvokingMethod() {
            return new HarissaMethod(this.invokeExpr.getMethod(),
                    new HarissaClass(this.invokeExpr.getMethod().getDeclaringClass()));
        }

        private void build()
        {

            this.methodSignature = this.invokeExpr.getMethodRef().getSignature();
            this.fullExpression = this.invokeExpr.toString();

            this.invokeExpr.getArgs()
                           .forEach(arg -> this.args.put(arg.getType().toString(), arg.toString()));

            if(!this.invokeExpr.getUseBoxes().isEmpty()) this.invoker = this.invokeExpr.getUseBoxes()
                                                                                       .iterator()
                                                                                       .next()
                                                                                       .getValue()
                                                                                       .toString();
        }

        public boolean findInArgs(String type, String value)
        {
            return this.args.entrySet()
                            .stream()
                            .anyMatch(entry -> (entry.getKey().equals(type) &&
                                                entry.getValue().contains(value)));

        }

        public int getArgIndex(String type, String value)
        {
            int argIndex = 0;

            if(findInArgs(type, value))
            {

                int i = 1;
                while(i < invokeExpr.getArgs().size() + 1)
                {
                    if(invokeExpr.getArg(i - 1).getType().toString().equals(type) && invokeExpr.getArg(i - 1).toString().equals(value))
                            break;
                    else
                            i++;
                }

                argIndex = i;
            }

            return argIndex;
        }
    }

    public class AssignExpression
    {
        public JAssignStmt sootAssignStatement;

        private Value left;
        private Value right;

        public AssignExpression(JAssignStmt sootAssignStatement)
        {
            this.sootAssignStatement = sootAssignStatement;
            this.left  = sootAssignStatement.getLeftOp();
            this.right = sootAssignStatement.getRightOp();
        }

        public Value getLeft() {
            return left;
        }

        public Value getRight() {
            return right;
        }

        public String getRightStringType()
        {
            return this.right.getType().toString();
        }

        public boolean leftIsLocal()
        {
            return this.left instanceof Local;
        }

        public boolean isNewAssign()
        {
            return this.right instanceof JNewExpr;
        }


        public boolean rightIsInvokeExpression()
        {

            return this.right instanceof JVirtualInvokeExpr    ||
                   this.right instanceof JDynamicInvokeExpr    ||
                   this.right instanceof  JInterfaceInvokeExpr ||
                   this.right instanceof  JSpecialInvokeExpr   ||
                   this.right instanceof  JStaticInvokeExpr;
        }
    }
}
